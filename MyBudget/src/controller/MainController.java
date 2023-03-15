package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;
import model.Benutzer;
import model.BenutzerFX;
import model.Datenbank;
import model.Dauereintrag;
import model.DauereintragFX;
import model.Eintrag;
import model.EintragFX;
import model.Intervall;
import model.Kategorie;
import model.KategorieFX;

public class MainController {	
	//Format Datum
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	//Format Währung
	NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
	//Format Text in Decimalzahl
	NumberStringConverter converter = new NumberStringConverter("#0.00");

	//INITIALIZE 
	@FXML public void initialize() {
		//Zeigt die ÜbersichtStack beim wechseln der Tabs Einnahmen/Ausgaben
		tpEinnahmenAusgabenStatistik.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> arg0, Tab arg1, Tab arg2) {
				if(arg2 == tabAusgaben) {
					showStackAusgabenUebersicht(null);
					btnAusgabenUebersicht.setDefaultButton(true);
					setFavoriteButtonAbleOrDisable();
				}
				if(arg2 == tabEinnahmen) {
					showStackEinnahmenUebersicht(null);	
					btnEinnahmenUebersicht.setDefaultButton(true);
					setFavoriteButtonAbleOrDisable();
				}
			}
		});
		//Benutzer auslesen und der ObserverList hinzufügen
		getObservableListBenutzer();
		cbBenutzer.setItems(olBenutzer);
		cbBenutzer.getSelectionModel().select(olBenutzer.stream().filter(b -> b.getName().equals("HAUSHALT")).findFirst().orElse(null));
		//Einnahmen Übersicht laden
		btnEinnahmenUebersicht.setDefaultButton(true);
		ladeUebersicht();
		ladeStatistik();
		addButtonToDauereintraegeTable();
		addButtonToUebersichtTable();
		//Favoriten Button auf able oder disable setzen
		setFavoriteButtonAbleOrDisable();
	}
	
	//ZEITRAUM------------------------------------------------------------------------------------------------------------------------------
	//Zeitraum Anzeigen
	LocalDate anfangZeitraum = LocalDate.now().withDayOfMonth(1);
	int letzterTagMonat = LocalDate.now().lengthOfMonth();
	int letzterTagJahr = LocalDate.now().lengthOfYear();
	LocalDate endeZeitraum = anfangZeitraum.withDayOfMonth(letzterTagMonat);
	String periodeZeitraum = "month";
		
	//ActionEvent btnBearbeitenTag
	@FXML public void setPeriodeTag(){
		//Button Monat default entfernen
		btnUebersichtMonat.setDefaultButton(false);
		btnFavoritenMonat.setDefaultButton(false);
		btnStatistikMonat.setDefaultButton(false);
		//Periden Daten zurückgeben
		periodeZeitraum = "'day'";
		anfangZeitraum = LocalDate.now();
		endeZeitraum = LocalDate.now();
		lblUebersichtZeitraum.setText(anfangZeitraum.format(formatter));
	}
		
	//ActionEvent btnBearbeitenWoche
	@FXML public void setPeriodeWoche(){
		//Button Monat default entfernen
		btnUebersichtMonat.setDefaultButton(false);
		btnFavoritenMonat.setDefaultButton(false);
		btnStatistikMonat.setDefaultButton(false);
		//Periden Daten zurückgeben
		periodeZeitraum = "'week'";
		anfangZeitraum = LocalDate.now().with(DayOfWeek.MONDAY);
		endeZeitraum = LocalDate.now().with(DayOfWeek.SUNDAY);
		//lblBearbeitenZeitraum neuen String hinterlegen
		getZeitraum();
	}
	
	//ActionEvent btnUebersichtMonat
	@FXML public void setPeriodeMonat(ActionEvent event){
		//Periden Daten zurückgeben
		periodeZeitraum = "month";
		anfangZeitraum = LocalDate.now().withDayOfMonth(1);
		endeZeitraum = anfangZeitraum.withDayOfMonth(letzterTagMonat);
		getZeitraum();
	}
	
	//ActionEvent btnUebersichtJahr
	@FXML public void setPeriodeJahr(ActionEvent event){
		//Button Monat default entfernen
		btnUebersichtMonat.setDefaultButton(false);
		btnFavoritenMonat.setDefaultButton(false);
		btnStatistikMonat.setDefaultButton(false);
		//Periden Daten zurückgeben
		periodeZeitraum = "year";
		anfangZeitraum = LocalDate.now().withDayOfYear(1);
		endeZeitraum = anfangZeitraum.withDayOfYear(letzterTagJahr);
		getZeitraum();
	}
	
	//ActionEvent btnUebersichtPfeilVorwaerts
	@FXML public void periodeZeitraumVor(ActionEvent event){
		if(periodeZeitraum.equalsIgnoreCase("'day'")) {
			anfangZeitraum = anfangZeitraum.plus(1, ChronoUnit.DAYS);
			endeZeitraum = endeZeitraum.plus(1, ChronoUnit.DAYS);
			//lblBearbeitenZeitraum neuen String hinterlegen
			lblUebersichtZeitraum.setText(anfangZeitraum.format(formatter));
		}
		else if(periodeZeitraum.equalsIgnoreCase("'week'")) {
			anfangZeitraum = anfangZeitraum.plus(1, ChronoUnit.WEEKS);
			endeZeitraum = endeZeitraum.plus(1, ChronoUnit.WEEKS);
			//lblBearbeitenZeitraum neuen String hinterlegen
			getZeitraum();
		}
		else if(periodeZeitraum.equalsIgnoreCase("month")) {
			anfangZeitraum = anfangZeitraum.plus(1, ChronoUnit.MONTHS);
			endeZeitraum = endeZeitraum.plus(1, ChronoUnit.MONTHS);
			getZeitraum();
		}
		else if(periodeZeitraum.equalsIgnoreCase("year")) {
			anfangZeitraum = anfangZeitraum.plus(1, ChronoUnit.YEARS);
			endeZeitraum = endeZeitraum.plus(1, ChronoUnit.YEARS);
			getZeitraum();
		}
	}
	
	//ActionEvent btnUebersichtPfeilZurueck
	@FXML public void periodeZeitraumZurueck(ActionEvent event){
		if(periodeZeitraum == "'day'") {
			anfangZeitraum = anfangZeitraum.minus(1, ChronoUnit.DAYS);
			endeZeitraum = endeZeitraum.minus(1, ChronoUnit.DAYS);
			//lblBearbeitenZeitraum neuen String hinterlegen
			lblUebersichtZeitraum.setText(anfangZeitraum.format(formatter));
		}
		else if(periodeZeitraum == "'week'") {
			anfangZeitraum = anfangZeitraum.minus(1, ChronoUnit.WEEKS);
			endeZeitraum = endeZeitraum.minus(1, ChronoUnit.WEEKS);
			//lblBearbeitenZeitraum neuen String hinterlegen
			getZeitraum();
		}
		else if(periodeZeitraum == "month") {
			anfangZeitraum = anfangZeitraum.minus(1, ChronoUnit.MONTHS);
			endeZeitraum = endeZeitraum.minus(1, ChronoUnit.MONTHS);
			getZeitraum();
		}
		else if(periodeZeitraum == "year") {
			anfangZeitraum = anfangZeitraum.minus(1, ChronoUnit.YEARS);
			endeZeitraum = endeZeitraum.minus(1, ChronoUnit.YEARS);
			getZeitraum();
		}	
	}
	
	//Retourniert einen String für lblUebersichtZeitraum
	public void getZeitraum() {
		lblUebersichtZeitraum.setText(anfangZeitraum.format(formatter) + " bis " + endeZeitraum.format(formatter));
		lblFavoritenZeitraum.setText(anfangZeitraum.format(formatter) + " bis " + endeZeitraum.format(formatter));
		lblStatistikZeitraum.setText(anfangZeitraum.format(formatter) + " bis " + endeZeitraum.format(formatter));
	}
	
	//BENUTZER------------------------------------------------------------------------------------------------------------------------------
	@FXML ObservableList<BenutzerFX> olBenutzer = FXCollections.observableArrayList();		
	
	@FXML HBox hbBenutzer;
	@FXML Button bBenutzerAnlegenEntfernen; //ActionEvent ist benutzerAnlegen()
	@FXML ComboBox<BenutzerFX> cbBenutzer; //ActionEvent ist datenAktualisieren()
	
	//TableViews neu laden
	@FXML public void datenAktualisieren(){	
		ladeUebersicht();
		ladeFavoriten();
		ladeDauereintraege();
	}
	
	//Benutzer aus Datenbank auslesen und ObservableList hinzufügen
	public void getObservableListBenutzer() {
		try {
			ArrayList<Benutzer> alBenutzer = Datenbank.readBenutzer();
			olBenutzer.clear();
			for(Benutzer einBenutzer : alBenutzer)
				olBenutzer.add(new BenutzerFX(einBenutzer));
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.toString());
		}	
	}
	
	//Öffnen des BenutzerDialog durch drücken des bBenutzerAnlegenEntfernen Buttons
	@FXML public void benutzerAnlegen(ActionEvent event) throws SQLException{
		try {
			FXMLLoader fxmlLoaderBenutzer = new FXMLLoader();
			fxmlLoaderBenutzer.setLocation(getClass().getResource("/view/BenutzerDialog.fxml"));
			DialogPane benutzerDialog = fxmlLoaderBenutzer.load();
			
			//Holen der BenutzerDialogController-Instanzen
			BenutzerDialogController bdc = fxmlLoaderBenutzer.getController();		
			
			Dialog<ButtonType> dialogBenutzer = new Dialog<>();
			dialogBenutzer.setDialogPane(benutzerDialog);
			dialogBenutzer.setTitle("Benutzer verwalten");
			
			Optional<ButtonType> clickedButton = dialogBenutzer.showAndWait();			
			if(clickedButton.get() == ButtonType.OK ) {		
				//olBenutzer updaten und anzeigen
				getObservableListBenutzer();
				cbBenutzer.setItems(olBenutzer);
				cbBenutzer.getSelectionModel().select(olBenutzer.stream().filter(b -> b.getName().equals("HAUSHALT")).findFirst().orElse(null));
				//neu laden der TableView Favoriten
				setTabsFavoriten();
				//neu laden der TableView Dauereinträge
				tableColumnsDauereintraege();
			}
			else 
				return;			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//TABPANES
	@FXML TabPane tpEinnahmenAusgabenStatistik;
	public TabPane getTabPane() {
		return tpEinnahmenAusgabenStatistik;
	}
		
	//TAB EINNAHMEN
	@FXML Tab tabEinnahmen;
	@FXML AnchorPane apEinnahmen;
	@FXML Button btnEinnahmenUebersicht; //ActionEvent ist showStackEinnahmenUebersicht()
	@FXML Button btnEinnahmenFavoriten; //ActionEvent ist showStackEinnahmenFavoriten()
	@FXML Button btnEinnahmenDauereintraege; //ActionEvent ist showStackEinnahmenDauereintraege()
	
	//Wechseln der Stacks in Einnahmen per klick auf Button(Übersicht, Favoriten, Dauereintraege)		
	@FXML public void showStackEinnahmenUebersicht(ActionEvent event) {
		apEinnahmen.getChildren().remove(spUebersicht);
		apEinnahmen.getChildren().add(spUebersicht);
		ladeUebersicht();
	}
	@FXML public void showStackEinnahmenFavoriten(ActionEvent event) throws SQLException {
		apEinnahmen.getChildren().remove(spFavoriten);
		apEinnahmen.getChildren().add(spFavoriten);
		ladeFavoriten();
	}
	@FXML public void showStackEinnahmenDauereintraege(ActionEvent event) {
		apEinnahmen.getChildren().remove(spDauereintraege);
		apEinnahmen.getChildren().add(spDauereintraege);
		ladeDauereintraege();
	}
	
	//TAB AUSGABEN
	@FXML Tab tabAusgaben;
	@FXML AnchorPane apAusgaben;
	@FXML Button btnAusgabenUebersicht; //ActionEvent ist showStackAusgabenUebersicht()
	@FXML Button btnAusgabenFavoriten; //ActionEvent ist showStackAusgabenFavoriten()	
	@FXML Button btnAusgabenDauereintraege; //ActionEvent ist showStackAusgabenDauereintraege()
	
	//Wechseln der Stacks in Ausgaben per klick auf Button(Übersicht, Favoriten, Dauereintraege)
	@FXML public void showStackAusgabenUebersicht(ActionEvent event) {
		apAusgaben.getChildren().remove(spUebersicht);
		apAusgaben.getChildren().add(spUebersicht);
		ladeUebersicht();
	}
	@FXML public void showStackAusgabenFavoriten(ActionEvent event) throws SQLException {
		apAusgaben.getChildren().remove(spFavoriten);
		apAusgaben.getChildren().add(spFavoriten);
		ladeFavoriten();
	}
	@FXML public void showStackAusgabenDauereintraege(ActionEvent event) {
		apAusgaben.getChildren().remove(spDauereintraege);
		apAusgaben.getChildren().add(spDauereintraege);
		ladeDauereintraege();
	}
	
	//ÜBERSICHT ----------------------------------------------------------------------------------------------------------------------------
	@FXML ObservableList<KategorieFX> olKategorie = FXCollections.observableArrayList();
	@FXML ObservableList<String> olSortierungUebersicht = FXCollections.observableArrayList("Kategorie A-Z", "Kategorie Z-A", "Betrag aufsteigend","Betrag absteigend");
	
	//StackPane Übersicht
	@FXML StackPane spUebersicht;
	
	@FXML HBox hbUebersichtButtonsZeitraum;
	@FXML Button btnUebersichtMonat; //ActionEvent ist setPeriodeMonat()
	@FXML Button btnUebersichtJahr; //ActionEvent ist setPeriodeJahr()
	
	@FXML HBox hbUebersichtZeitraum;
	@FXML Button btnUebersichtPfeilZurueck; //ActionEvent ist periodeZeitraumZurueck()
	@FXML Label lblUebersichtZeitraum;
	@FXML Button btnUebersichtPfeilVorwaerts; //ActionEvent ist periodeZeitraumVor()
	
	@FXML GridPane gpUebersichtPlus;
	@FXML Button btnPlus; //ActionEvent ist bearbeitenDialog()
	
	@FXML AnchorPane apUebersicht;
	
	//TableView Übersicht
	@FXML TableView<KategorieFX> tvUebersicht;
	@FXML TableColumn<KategorieFX, String> kategorieCol;
	@FXML TableColumn<KategorieFX, Double> summeCol;
	
	//Inhalt für Übersicht laden
	public void ladeUebersicht() {
		//Zeitraum laden
		btnUebersichtMonat.setDefaultButton(true);
		getZeitraum();
		//TableView laden
		tableColumnsUebersicht();
		tvUebersicht.setItems(olKategorie);
		tvUebersicht.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}
	
	//Öffnen des BearbeitenDialogControllers 
	@FXML public void bearbeitenDialog(ActionEvent event) throws SQLException {
		try {
			FXMLLoader fxmlLoaderBearbeiten = new FXMLLoader();
			fxmlLoaderBearbeiten.setLocation(getClass().getResource("/view/BearbeitenDialog.fxml"));
			DialogPane bearbeitenDialog = fxmlLoaderBearbeiten.load();
			
			//Holen der KategorieDialogController-Instanzen
			BearbeitenDialogController bdc = fxmlLoaderBearbeiten.getController();	
			bdc.setMainController(this);
			
			boolean isEinnahmenParameter = tpEinnahmenAusgabenStatistik.getSelectionModel().getSelectedItem().getText().equals("Einnahmen");	
			if(kategorieFX == null)
				bdc.setKategorieFX(new KategorieFX(new Kategorie(0, isEinnahmenParameter, "", false)));
			else {
				bdc.setKategorieFX(kategorieFX);
				bdc.setKategorieFXData();
			}
			
			Dialog<ButtonType> dialogBearbeiten = new Dialog<>();
			dialogBearbeiten.setDialogPane(bearbeitenDialog);
			dialogBearbeiten.setTitle("Bearbeiten");
			
			Optional<ButtonType> clickedButton = dialogBearbeiten.showAndWait();
			if(clickedButton.get() == ButtonType.OK ) {	
				ladeUebersicht();
				kategorieFX = new KategorieFX(new Kategorie(0, isEinnahmenParameter, "", false));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Kategorien aus Datenbank auslesen und ObservableList hinzufügen
	public void getObservableListKategorien() {
		try {
			ArrayList<Kategorie> alKategorien = Datenbank.readKategorie(tpEinnahmenAusgabenStatistik.getSelectionModel().getSelectedItem().getText());
			olKategorie.clear();
			for(Kategorie eineKategorie : alKategorien) {
				eineKategorie.setSummeEintraege(Datenbank.readKategorieSummeEintraege(eineKategorie.getName(), tpEinnahmenAusgabenStatistik.getSelectionModel().getSelectedItem().getText()));
				olKategorie.add(new KategorieFX(eineKategorie));									
			}
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.toString());
		}	
	}
	
	//TableColumns TableView tvUebersicht hinzufügen
	public void tableColumnsUebersicht() {
		getObservableListKategorien();
		kategorieCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		summeCol.setCellValueFactory(new PropertyValueFactory<>("summeEintraege"));	
		summeCol.setCellFactory(column -> new TableCell<KategorieFX, Double>() {
			 @Override
			    protected void updateItem(Double betrag, boolean empty) {
			        super.updateItem(betrag, empty);
			        if (empty) {
			            setText(null);
			        } else {
			            setText(currencyFormat.format(betrag));
			        }
			    }
		});
	}
	
	//TableColumn mit Buttons erstellen und TableView tvUebersicht hinzufügen
	KategorieFX kategorieFX = null;
	private void addButtonToUebersichtTable() {
        TableColumn<KategorieFX, Void> buttonCol = new TableColumn();
        Callback<TableColumn<KategorieFX, Void>, TableCell<KategorieFX, Void>> cellFactory = new Callback<TableColumn<KategorieFX, Void>, TableCell<KategorieFX, Void>>() {
            @Override
            public TableCell<KategorieFX, Void> call(final TableColumn<KategorieFX, Void> param) {
                final TableCell<KategorieFX, Void> cell = new TableCell<KategorieFX, Void>() {
                    private final Button btnFavorite = new Button("Favorite");
                    private final Button btnBearbeiten = new Button("Bearbeiten");
                    private final Button btnLöschen = new Button("Löschen");
                    HBox hbButtons = new HBox(10, btnFavorite, btnBearbeiten, btnLöschen);
                    {
                    	//ActionEvents für Favoriten Button
                    	btnFavorite.setOnAction((ActionEvent event) -> {
                    		kategorieFX  = getTableView().getItems().get(getIndex());
                    		try {
                    			if(kategorieFX.isFavorite() == false) {
                    				Datenbank.setKategorieFavorit(kategorieFX.getKategorieId(), true);
                    				btnFavorite.setStyle("-fx-background-color:  lightblue;");
                    			}
                    			else {
                    				Datenbank.setKategorieFavorit(kategorieFX.getKategorieId(), false);
                    				btnFavorite.setStyle("");
                    			}
                    			setFavoriteButtonAbleOrDisable();
                    			//Liste neu laden
                    			tableColumnsUebersicht();
                    			boolean isEinnahmenParameter = tpEinnahmenAusgabenStatistik.getSelectionModel().getSelectedItem().getText().equals("Einnahmen");	
                    			kategorieFX = new KategorieFX(new Kategorie(0, isEinnahmenParameter, "", false));
                    		} catch (SQLException e) {
                    			e.printStackTrace(); 
                    		}
                        });
                    	//ActionEvents für Bearbeiten Button
                    	btnBearbeiten.setOnAction((ActionEvent event) -> {
                    		try {
                    			kategorieFX  = getTableView().getItems().get(getIndex());
								bearbeitenDialog(event);
							} catch (SQLException e) {
								e.printStackTrace();
							}
                        });
                    	//ActionEvents für Löschen Button
                    	btnLöschen.setOnAction((ActionEvent event) -> {
                    		kategorieFX = getTableView().getItems().get(getIndex());
                    		Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
                    	    confirmationDialog.setTitle("Kategorie löschen");
                    	    confirmationDialog.setContentText("Soll die Kategorie wirklich gelöscht werden, mit der Kategorie werden auch alle: "
                    	    		+ "\n\nEinträge und Dauereinträge\n\ngelöscht. \nÄnderungen können nicht rückgängig gemacht werden!");
                    	    Optional<ButtonType> clickedButton = confirmationDialog.showAndWait();
                    	    if (clickedButton.isPresent() && clickedButton.get() == ButtonType.OK) {
                    	    	try {
                    	    		Datenbank.deleteKategorie(kategorieFX.getKategorieId());
                    	    		//Liste neu laden
                    	    		tableColumnsUebersicht();
                    	    	} catch (SQLException e) {
                    	    		e.printStackTrace();
                    	    	}
                    	    }
                    	    boolean isEinnahmenParameter = tpEinnahmenAusgabenStatistik.getSelectionModel().getSelectedItem().getText().equals("Einnahmen");	
                			kategorieFX = new KategorieFX(new Kategorie(0, isEinnahmenParameter, "", false));
                        });
                    }
                    //Button nur anzeigen wenn Text in der Zeile gezeigt wird
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                        	if(getTableView().getItems().get(getIndex()).isFavorite())
                        		btnFavorite.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
                            setGraphic(hbButtons);
                        }
                    }
                };
                return cell;
            }
        };
        buttonCol.setCellFactory(cellFactory);
        buttonCol.setPrefWidth(456);
        buttonCol.setStyle("-fx-alignment: CENTER;");
        tvUebersicht.getColumns().add(buttonCol);
    }

	//FAVORITEN ----------------------------------------------------------------------------------------------------------------------------
	@FXML ObservableList<KategorieFX> olFavoriten = FXCollections.observableArrayList();
	
	//StackPane Favoriten
	@FXML StackPane spFavoriten;		
	@FXML HBox hbFavoritenButtonsZeitraum;
	@FXML Button btnFavoritenTag; //ActionEvent ist setPeriodeTag()
	@FXML Button btnFavoritenWoche; //ActionEvent ist setPeriodeWoche()
	@FXML Button btnFavoritenMonat; //ActionEvent ist setPeriodeMonat()
	@FXML Button btnFavoritenJahr; //ActionEvent ist setPeriodeJahr()
	
	@FXML HBox hbFavoritenZeitraum;
	@FXML Button btnFavoritenPfeilZurueck; //ActionEvent ist periodeZeitraumZurueck()
	@FXML Label lblFavoritenZeitraum;
	@FXML Button btnFavoritenPfeilVorwaerts; //ActionEvent ist periodeZeitraumVor()
	
	@FXML HBox hbFavoritenEingabezeile;
	@FXML Label lblFavoritenKategorie;
	@FXML ComboBox<KategorieFX> cbFavoritenKategorie;
	@FXML Label lblFavoritenDatum;
	@FXML DatePicker dpFavoritenDatum;
	@FXML Label lblFavoritenTitel;
	@FXML TextField txtFavoritenTitel;
	@FXML Label lblFavoritenBetrag;
	@FXML TextField txtFavoritenBetrag;
	@FXML Label lblFavoritenBenutzer;
	@FXML ComboBox<BenutzerFX> cbFavoritenBenutzer;
	@FXML Label lblFavoritenDauereintrag;
	@FXML ComboBox<Intervall> cbFavoritenIntervall; //ActionEvent ist setDatePickerEndeDauereintragOnAbleFavoriten()
	@FXML Label lblFavoritenEndeDauereintrag;
	@FXML DatePicker dpFavoritenEndeDauereintrag;
	@FXML Button btnFavoritenSpeichern; //ActionEvent ist btnSpeichernUeberFavoriten()
	
	@FXML TabPane tpFavoriten;

	//Inhalt für Favoriten laden
	public void ladeFavoriten() {
		//Zeitraum laden
		btnFavoritenMonat.setDefaultButton(true);
		getZeitraum();
		//btnEinnahmenUebersicht als Default entfernen
		btnEinnahmenUebersicht.setDefaultButton(false);
		btnAusgabenUebersicht.setDefaultButton(false);
		//Defaults setzen und Listen laden für Eingabezeile
		dpFavoritenDatum.setValue(LocalDate.now());
		cbFavoritenKategorie.setItems(olKategorie);
		cbFavoritenKategorie.setPromptText("Kategorie wählen");
		txtFavoritenTitel.setPromptText("Titel eingeben");
		txtFavoritenBetrag.setPromptText("00.00");
		txtFavoritenBetrag.setTextFormatter(new TextFormatter<>(converter));
		cbFavoritenBenutzer.setItems(olBenutzer);
		cbFavoritenBenutzer.getSelectionModel().select(olBenutzer.stream().filter(b -> b.getName().equals("HAUSHALT")).findFirst().orElse(null));
		cbFavoritenIntervall.getItems().setAll(Intervall.values());
		cbFavoritenIntervall.setValue(Intervall.KEINE);
		dpFavoritenEndeDauereintrag.setDisable(true);
		//Tabs und TableView laden
		try {
			setTabsFavoriten();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Set Favoriten Button aktiv/inaktiv
	public void setFavoriteButtonAbleOrDisable() {
		getObservableListFavoritenKategorien();
		if(tpEinnahmenAusgabenStatistik.getSelectionModel().getSelectedItem().getText().equals("Einnahmen")) {
			if(olFavoriten.size() != 0)
				btnEinnahmenFavoriten.setDisable(false);
			else
				btnEinnahmenFavoriten.setDisable(true);
		}
		else if(tpEinnahmenAusgabenStatistik.getSelectionModel().getSelectedItem().getText().equals("Ausgaben")) {
			if(olFavoriten.size() != 0)
				btnAusgabenFavoriten.setDisable(false);
			else
				btnAusgabenFavoriten.setDisable(true);
		}
	}
	
	//DatePicker dpFavoritenEndeDauereintrag set on able oder disable
	@FXML public void setDatePickerEndeDauereintragOnAbleFavoriten(ActionEvent event){
		if(cbFavoritenIntervall.getSelectionModel().getSelectedItem() == Intervall.KEINE)
			dpFavoritenEndeDauereintrag.setDisable(true);
		else {
			dpFavoritenEndeDauereintrag.setDisable(false);
		}
	}
	
	//Einnahmen Einträge/Dauereinträge speichern über Favoriten											
	@FXML public void btnSpeichernUeberFavoriten(ActionEvent event) {
		if(cbFavoritenKategorie.getSelectionModel().getSelectedItem() == null) {
			new Alert(AlertType.ERROR, "Kategorie wählen").showAndWait();
			return;
		}
		if(txtFavoritenTitel.getText() == null || txtFavoritenTitel.getText().length() == 0) {
			new Alert(AlertType.ERROR, "Titel eingeben").showAndWait();
			return;
		}
		if(txtFavoritenBetrag.getText() == null || txtFavoritenBetrag.getText().length() == 0) {
			new Alert(AlertType.ERROR, "Betrag eingeben").showAndWait();
			return;
		}
		if(!cbFavoritenIntervall.getSelectionModel().getSelectedItem().equals("keine") && dpFavoritenEndeDauereintrag.getValue() == null) {
			new Alert(AlertType.ERROR, "Datum Ende-Dauereintrag wählen").showAndWait();
			return;
		}
		if(cbFavoritenIntervall.getValue().toString().equalsIgnoreCase("keine")) {
			try {
				//Eintrag hinzufügen
				Datenbank.insertEintrag(new Eintrag(
						dpFavoritenDatum.getValue(),
						txtFavoritenTitel.getText(),
						Double.parseDouble(txtFavoritenBetrag.getText()),
						cbFavoritenBenutzer.getSelectionModel().getSelectedItem().getModellBenutzer(),
						cbFavoritenKategorie.getSelectionModel().getSelectedItem().getModellKategorie(),
						cbFavoritenIntervall.getSelectionModel().getSelectedItem()
						));
				//TableView neu laden
				setTabsFavoriten();
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				Datenbank.insertDauereintrag(new Dauereintrag(
						dpFavoritenDatum.getValue(),
						txtFavoritenTitel.getText(),
						Double.parseDouble(txtFavoritenBetrag.getText()),
						cbFavoritenBenutzer.getSelectionModel().getSelectedItem().getModellBenutzer(),
						cbFavoritenIntervall.getSelectionModel().getSelectedItem(),
						dpFavoritenEndeDauereintrag.getValue(),
						cbFavoritenKategorie.getSelectionModel().getSelectedItem().getModellKategorie()
						));
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}
		}
	}
			
	//Favorisierte Kategorien aus Datenbank auslesen und ObservableList hinzufügen
	public void getObservableListFavoritenKategorien() {
		try {
			ArrayList<Kategorie> alFavoriten = Datenbank.readFavoritenKategorien(cbBenutzer.getSelectionModel().getSelectedItem().getBenutzerId(), tpEinnahmenAusgabenStatistik.getSelectionModel().getSelectedItem().getText());
			olFavoriten.clear();
			for(Kategorie eineKategorie : alFavoriten)
				olFavoriten.add(new KategorieFX(eineKategorie));
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.toString());
		}	
	}
	
	//Favoriten Tabs erstellen und tabAusgabenFavoriten hinzufügen
	ArrayList<Tab> alTabsFavoriten = new ArrayList<>();
	int eineFavoritenKategorieId = 0;
	public void setTabsFavoriten() throws SQLException {
		getObservableListFavoritenKategorien();
		alTabsFavoriten.clear();
		for(KategorieFX eineFavoritenKategorieFX : olFavoriten) {
			//Neuen Tab erstellen
			eineFavoritenKategorieId = eineFavoritenKategorieFX.getKategorieId();
			Tab einTabFavoritenKategorie = new Tab(eineFavoritenKategorieFX.getName());
			einTabFavoritenKategorie.setContent(getObservableListUndTableViewEintraegeNachKategorie());
			alTabsFavoriten.add(einTabFavoritenKategorie);
		}
		//Tabs der TabPane hinzufügen
		tpFavoriten.getTabs().clear();
		tpFavoriten.getTabs().addAll(alTabsFavoriten);
	}
	
	//Einträge nach Kategorie aus der Datenbank auslesen und in ObservableList eintragen und TableView erstellen
	public Node getObservableListUndTableViewEintraegeNachKategorie() {
		ObservableList<EintragFX> olEintraege = FXCollections.observableArrayList();
		TableView<EintragFX> tvFavoriten = new TableView<>(olEintraege);
		try {
			ArrayList<Eintrag> alEintraege = Datenbank.readEintraegeNachKategorie(cbBenutzer.getSelectionModel().getSelectedItem().getBenutzerId(), tpEinnahmenAusgabenStatistik.getSelectionModel().getSelectedItem().getText(), eineFavoritenKategorieId);
			for(Eintrag einEintrag : alEintraege)
				olEintraege.add(new EintragFX(einEintrag));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//TableView erstellen
		TableColumn<EintragFX, LocalDate> datumCol = new TableColumn<>("Datum");
		datumCol.setCellValueFactory(new PropertyValueFactory<>("datum"));
		datumCol.setCellFactory(column -> new TableCell<EintragFX, LocalDate>() {
			@Override
			protected void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				if (empty) {
					setText("");
				} else {
					setText(formatter.format(date));
				}
			}
		});
		datumCol.setPrefWidth(260);
		datumCol.setStyle("-fx-alignment: CENTER;");
		TableColumn<EintragFX, String> titelCol = new TableColumn<>("Titel");
		titelCol.setCellValueFactory(new PropertyValueFactory<>("titel"));
		titelCol.setPrefWidth(260);
		titelCol.setStyle("-fx-alignment: CENTER;");
		TableColumn<EintragFX, Double> betragCol = new TableColumn<>("Betrag");
		betragCol.setCellValueFactory(new PropertyValueFactory<>("betrag"));
		betragCol.setCellFactory(column -> new TableCell<EintragFX, Double>() {
			@Override
			protected void updateItem(Double betrag, boolean empty) {
				super.updateItem(betrag, empty);
				if (empty) {
					setText(null);
				} else {
					setText(currencyFormat.format(betrag));
				}
			}
		});
		betragCol.setPrefWidth(260);
		betragCol.setStyle("-fx-alignment: CENTER;");
		TableColumn<EintragFX, String> benutzerCol = new TableColumn<>("Benutzer");
		benutzerCol.setCellValueFactory(new PropertyValueFactory<>("benutzerName"));
		benutzerCol.setPrefWidth(260);
		benutzerCol.setStyle("-fx-alignment: CENTER;");
		TableColumn<EintragFX, String> intervallCol = new TableColumn<>("Dauereintrag");
		intervallCol.setCellValueFactory(new PropertyValueFactory<>("intervall"));
		intervallCol.setPrefWidth(260);
		intervallCol.setStyle("-fx-alignment: CENTER;");								
		tvFavoriten.getColumns().addAll(datumCol, titelCol, betragCol, benutzerCol, intervallCol, addButtonToEintraegeTable());
		tvFavoriten.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);	
		return tvFavoriten;
	}
	
	//TableColumn mit Buttons erstellen und TableView Favoriten zuordnen
	private TableColumn<EintragFX,Void> addButtonToEintraegeTable() {
		TableColumn<EintragFX, Void> buttonCol = new TableColumn();
		Callback<TableColumn<EintragFX, Void>, TableCell<EintragFX, Void>> cellFactory = new Callback<TableColumn<EintragFX, Void>, TableCell<EintragFX, Void>>() {
			@Override
			public TableCell<EintragFX, Void> call(final TableColumn<EintragFX, Void> param) {
				final TableCell<EintragFX, Void> cell = new TableCell<EintragFX, Void>() {
					private final Button btnLöschen = new Button("Löschen");
					HBox hbButtons = new HBox(10, btnLöschen);
					{
						//ActionEvent für Button
						btnLöschen.setOnAction((ActionEvent event) -> {
							EintragFX eintragFX = getTableView().getItems().get(getIndex());
							Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
							confirmationDialog.setTitle("Eintrag löschen");
							confirmationDialog.setContentText("Soll der Eintrag wirklich gelöscht werden, Änderungen können nicht rückgängig gemacht werden!");
							Optional<ButtonType> clickedButton = confirmationDialog.showAndWait();
							if (clickedButton.isPresent() && clickedButton.get() == ButtonType.OK) {
								try {
									Datenbank.deleteEintrag(eintragFX.getEintragId());
									getObservableListUndTableViewEintraegeNachKategorie();
								} catch (SQLException e) {
									e.printStackTrace();
								}
							}
						});
					}
					//Button nur anzeigen wenn Text in der Zeile gezeigt wird
					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(hbButtons);
						}
					}
				};
				return cell;
			}
		};
		buttonCol.setCellFactory(cellFactory);
		buttonCol.setPrefWidth(80);
		buttonCol.setStyle("-fx-alignment: CENTER;");
		return buttonCol;
	}
	
	//DAUEREINTRÄGE ------------------------------------------------------------------------------------------------------------------------
	@FXML ObservableList<DauereintragFX> olDauereintraege = FXCollections.observableArrayList();
	@FXML ObservableList<String> olSortierungDauereintraege = FXCollections.observableArrayList("Kategorie A-Z", "Kategorie Z-A", "Betrag aufsteigend","Betrag absteigend", "Datum aufsteigend", "Datum absteigend");
	
	//StackPane Dauereinträge 
	@FXML StackPane spDauereintraege;
	
	@FXML HBox hbDauereintraegeText;
	@FXML Label lblDauereintraegeText;
	@FXML Label lblDauereintraegeAktuellesDatum;
	
	@FXML HBox hbDauereintraege;
	@FXML Label lblDauereintraegeKategorie;
	@FXML ComboBox<KategorieFX> cbDauereintraegeKategorie;
	@FXML Label lblDauereintraegeDatum;
	@FXML DatePicker dpDauereintraegeDatum;
	@FXML Label lblDauereintraegeTitel;
	@FXML TextField txtDauereintraegeTitel;
	@FXML Label lblDauereintraegeBetrag;
	@FXML TextField txtDauereintraegeBetrag;
	@FXML Label lblDauereintraegeBenutzer;
	@FXML ComboBox<BenutzerFX> cbDauereintraegeBenutzer;
	@FXML Label lblDauereintraegeDauereintrag;
	@FXML ComboBox<Intervall> cbDauereintraegeIntervall;
	@FXML Label lblDauereintraegeEndeDauereintrag;
	@FXML DatePicker dpDauereintraegeEndeDauereintrag; //ActionEvent ist setDatePickerEndeDauereintragOnAble
	@FXML Button btnDauereintraegeSpeichern; //ActionEvent ist btnSpeichernUeberDauereintrag
	
	//TableView Dauereinträge
	@FXML TableView<DauereintragFX> tvDauereintraege;
	@FXML TableColumn<DauereintragFX, LocalDate> naechsteFaelligkeitCol;
	@FXML TableColumn<DauereintragFX, String> kategorieNameCol;
	@FXML TableColumn<DauereintragFX, String> titelCol;
	@FXML TableColumn<DauereintragFX, Double> betragCol;
	@FXML TableColumn<DauereintragFX, String> benutzerCol;
	@FXML TableColumn<DauereintragFX, Intervall> intervallCol;
	@FXML TableColumn<DauereintragFX, LocalDate> endeDauereintragCol;
	@FXML TableColumn<DauereintragFX, Node> buttonsDauereintragCol;
	
	//Inhalt für Dauereintrag laden
	public void ladeDauereintraege() {
		//btnEinnahmenUebersicht als Default entfernen
		btnEinnahmenUebersicht.setDefaultButton(false);
		btnAusgabenUebersicht.setDefaultButton(false);
		lblDauereintraegeAktuellesDatum.setText(LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
		//Default setzen und Listen laden für Eingabezeile
		dpDauereintraegeDatum.setValue(LocalDate.now());
		cbDauereintraegeKategorie.setItems(olKategorie);
		cbDauereintraegeKategorie.setPromptText("Bitte wählen");
		txtDauereintraegeTitel.setPromptText("Titel eingeben");
		txtDauereintraegeBetrag.setPromptText("00.00");
		txtDauereintraegeBetrag.setTextFormatter(new TextFormatter<>(converter));
		cbDauereintraegeBenutzer.setItems(olBenutzer);
		cbDauereintraegeBenutzer.getSelectionModel().select(olBenutzer.stream().filter(b -> b.getName().equals("HAUSHALT")).findFirst().orElse(null));
		cbDauereintraegeIntervall.getItems().setAll(Intervall.values());
		cbDauereintraegeIntervall.setValue(Intervall.KEINE);
		dpDauereintraegeEndeDauereintrag.setDisable(true);
		//TableView laden
		tvDauereintraege.setItems(olDauereintraege);
		tableColumnsDauereintraege();
		tvDauereintraege.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		}
	
	//DatePicker dpDauereintraegeEndeDauereintrag set on able oder disable
	@FXML public void setDatePickerEndeDauereintragOnAble(ActionEvent event) {
		if(cbDauereintraegeIntervall.getSelectionModel().getSelectedItem() == Intervall.KEINE)
			dpDauereintraegeEndeDauereintrag.setDisable(true);
		else {
			dpDauereintraegeEndeDauereintrag.setDisable(false);
		}
	}
	
	//Dauereinträge nach Benutzer und Einnahmen/Ausgaben und ObservableList hinzufügen
	public void getObservableListDauereintraege() {
		try {
			ArrayList<Dauereintrag> alDauereintraege = Datenbank.readDauereintraege(cbBenutzer.getSelectionModel().getSelectedItem().getBenutzerId(), tpEinnahmenAusgabenStatistik.getSelectionModel().getSelectedItem().getText());
			olDauereintraege.clear();
			for(Dauereintrag einDauereintrag : alDauereintraege)
				olDauereintraege.add(new DauereintragFX(einDauereintrag));	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Einnahmen Einträge/Dauereinträge speichern über Dauereintrag	
	@FXML public void btnSpeichernUeberDauereintrag(ActionEvent event) {
		if(txtDauereintraegeTitel.getText() == null || txtDauereintraegeTitel.getText().length() == 0) {
			new Alert(AlertType.ERROR, "Titel eingeben").showAndWait();
			return;
		}
		if(txtDauereintraegeBetrag.getText() == null || txtDauereintraegeBetrag.getText().length() == 0) {
			new Alert(AlertType.ERROR, "Betrag eingeben").showAndWait();
			return;
		}
		if(cbDauereintraegeIntervall.getSelectionModel().getSelectedItem().equals("keine")) {
			new Alert(AlertType.ERROR, "Bitte Intervall wählen").showAndWait();
			return;
		}
		if(dpDauereintraegeEndeDauereintrag.getValue() == null) {
			new Alert(AlertType.ERROR, "Datum Ende-Dauereintrag wählen").showAndWait();
			return;
		}
		if(cbDauereintraegeKategorie.getSelectionModel().getSelectedItem() == null) {
			new Alert(AlertType.ERROR, "Kategorie wählen").showAndWait();
			return;
		}
		try {
			Datenbank.insertDauereintrag(new Dauereintrag(
				dpDauereintraegeDatum.getValue(),
				txtDauereintraegeTitel.getText(),
				Double.parseDouble(txtDauereintraegeBetrag.getText()),
				cbDauereintraegeBenutzer.getSelectionModel().getSelectedItem().getModellBenutzer(),
				cbDauereintraegeIntervall.getSelectionModel().getSelectedItem(),
				dpDauereintraegeEndeDauereintrag.getValue(),
				cbDauereintraegeKategorie.getSelectionModel().getSelectedItem().getModellKategorie()
					));
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		}
		tableColumnsDauereintraege();
	}
			
	//TableColumn mit Buttons erstellen und TableView Dauereinträge zuordnen
	private void addButtonToDauereintraegeTable() {
		buttonsDauereintragCol.setCellFactory(column -> new TableCell<>() {
			private final Button btnLöschen = new Button("Löschen");
			HBox hbButtons = new HBox(10, btnLöschen);
			{
				//ActionEvent für Button
				btnLöschen.setOnAction((ActionEvent event) -> {
					DauereintragFX dauereintragFX = getTableView().getItems().get(getIndex());
					Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
					confirmationDialog.setTitle("Eintrag löschen");
					confirmationDialog.setContentText("Soll der Eintrag wirklich gelöscht werden, Änderungen können nicht rückgängig gemacht werden!");
					Optional<ButtonType> clickedButton = confirmationDialog.showAndWait();
					if (clickedButton.isPresent() && clickedButton.get() == ButtonType.OK) {
						try {
							Datenbank.deleteDauereintrag(dauereintragFX.getDauereintragId());
							tableColumnsDauereintraege();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				});
			}
			//Button nur anzeigen wenn Text in der Zeile gezeigt wird
			@Override
			public void updateItem(Node item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					setGraphic(hbButtons);
				}
			}
		});
	}
			
	//TableColumns zuordnen Dauereintraege
	public void tableColumnsDauereintraege() {
		getObservableListDauereintraege();
		naechsteFaelligkeitCol.setCellValueFactory(new PropertyValueFactory<>("naechsteFaelligkeit"));
		naechsteFaelligkeitCol.setCellFactory(column -> new TableCell<DauereintragFX, LocalDate>() {
			@Override
			protected void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				if (empty) {
					setText("");
				} else {
					setText(formatter.format(date));
				}
			}
		});
		kategorieNameCol.setCellValueFactory(new PropertyValueFactory<>("kategorieName"));
		titelCol.setCellValueFactory(new PropertyValueFactory<>("titel"));
		betragCol.setCellValueFactory(new PropertyValueFactory<>("betrag"));
		betragCol.setCellFactory(column -> new TableCell<DauereintragFX, Double>() {
			@Override
			protected void updateItem(Double betrag, boolean empty) {
				super.updateItem(betrag, empty);
				if (empty) {
					setText(null);
				} else {
					setText(currencyFormat.format(betrag));
				}
			}
		});
		benutzerCol.setCellValueFactory(new PropertyValueFactory<>("benutzerName"));
		intervallCol.setCellValueFactory(new PropertyValueFactory<>("intervall"));
		endeDauereintragCol.setCellValueFactory(new PropertyValueFactory<>("dauereintragEnde"));
		endeDauereintragCol.setCellFactory(column -> new TableCell<DauereintragFX, LocalDate>() {
			@Override
			protected void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				if (empty) {
					setText("");
				} else {
					setText(formatter.format(date));
				}
			}
		});
	}
	
	//Dauereintraege durchgehen und ausführen								
	public void dauereintraegeAusfuehren() throws SQLException {
		ArrayList<Dauereintrag> alDauereintraege = Datenbank.readDauereintraegeAlle();
		for(Dauereintrag einDauereintrag : alDauereintraege) {
			//überprüfen ob Dauereintrag dauereintragEnde nach dem aktuellen Datum und naechsteFaelligkeit vor dem aktuellen Datum hat
			while(einDauereintrag.getEnddatum().isAfter(LocalDate.now()) && einDauereintrag.getNaechsteFaelligkeit().isBefore(LocalDate.now())){
				Datenbank.insertEintrag(new Eintrag(
						einDauereintrag.getNaechsteFaelligkeit(),
						einDauereintrag.getDeTitel(),
						einDauereintrag.getDeBetrag(),
						einDauereintrag.getDeBenutzer(),
						einDauereintrag.getDeKategorie(),
						einDauereintrag.getIntervall()					
						));
				//das naechsteFaelligkeit Datum auf die nächste Fälligkeit setzen setzen
				if(einDauereintrag.getIntervall().equals(Intervall.TAEGLICH)) {
					Datenbank.updateDauereintragNaechsteFaelligkeit(einDauereintrag.getNaechsteFaelligkeit().plusDays(1), einDauereintrag.getDauereintragId());
					einDauereintrag.setNaechsteFaelligkeit(einDauereintrag.getNaechsteFaelligkeit().plusDays(1));
				}
				if(einDauereintrag.getIntervall().equals(Intervall.WOECHENTLICH)) {
					Datenbank.updateDauereintragNaechsteFaelligkeit(einDauereintrag.getNaechsteFaelligkeit().plusWeeks(1), einDauereintrag.getDauereintragId());
					einDauereintrag.setNaechsteFaelligkeit(einDauereintrag.getNaechsteFaelligkeit().plusWeeks(1));
				}
				if(einDauereintrag.getIntervall().equals(Intervall.MONATLICH)) {
					Datenbank.updateDauereintragNaechsteFaelligkeit(einDauereintrag.getNaechsteFaelligkeit().plusMonths(1), einDauereintrag.getDauereintragId());
					einDauereintrag.setNaechsteFaelligkeit(einDauereintrag.getNaechsteFaelligkeit().plusMonths(1));
				}
				if(einDauereintrag.getIntervall().equals(Intervall.QUARTALSWEISE)) {
					Datenbank.updateDauereintragNaechsteFaelligkeit(einDauereintrag.getNaechsteFaelligkeit().plusMonths(3), einDauereintrag.getDauereintragId());
					einDauereintrag.setNaechsteFaelligkeit(einDauereintrag.getNaechsteFaelligkeit().plusMonths(3));
				}
				if(einDauereintrag.getIntervall().equals(Intervall.JAEHRLICH)) {
					Datenbank.updateDauereintragNaechsteFaelligkeit(einDauereintrag.getNaechsteFaelligkeit().plusYears(1), einDauereintrag.getDauereintragId());
					einDauereintrag.setNaechsteFaelligkeit(einDauereintrag.getNaechsteFaelligkeit().plusYears(1));
				}
			}
		}
	}
	
	//TAB STATISTIK-------------------------------------------------------------------------------------------------------------------------
	@FXML Tab tabStatistik;
	
	@FXML HBox hbStatistik;
	@FXML VBox vbStatistikButtonsDiagramm;
	@FXML HBox hbBilderDiagramme;
	@FXML ImageView ivTortendiagramm;
	@FXML ImageView ivSaulendiagramm;
	@FXML HBox hbRadioButtonsDiagramme;
	@FXML RadioButton rbTortendiagramm; //ActionEvent ist showTortendiagramm()
	@FXML RadioButton rbSaeulendiagramm; //ActionEvent ist showSaulendiagramm()
	
	@FXML VBox vbStatistikZeitraum;
	@FXML HBox hbStatistikButtonsZeitraum;
	@FXML Button btnStatistikMonat; //ActionEvent ist setPeriodeMonat()
	@FXML Button btnStatistikJahr; //ActionEvent ist setPeriodeJahr()
	@FXML HBox hbStatistikZeitraum;
	@FXML Button btnStatistikPfeilZurueck; //ActionEvent ist periodeZeitraumZurueck()
	@FXML Label lblStatistikZeitraum;
	@FXML Button btnStatistikPfeilVorwaerts; //ActionEvent ist periodeZeitraumVor()
	
	@FXML VBox vbStatistikBenutzer;
	@FXML AnchorPane apDiagramme;
	@FXML StackPane spSauelendiagramm; //ActionEvent showSaulendiagramm
	@FXML StackPane spTortendiagramm; //ActionEvent showTortendiagramm
	
	//Inhalt für Statistik laden
	@FXML public void ladeStatistik() {
		//setze rbTortendiagramm als Default
		rbTortendiagramm.setSelected(true);
		showTortendiagramm(null);
		//Zeitraum laden
		btnStatistikMonat.setDefaultButton(true);
		getZeitraum();
		//RadioButtons Diagramme in ToggleGroup packen
		setToggleGroup();
		//RadioButtons für Benutzer laden
		setRbBenutzerStatistik();
	}
	
	//Set RadioButtons in ToggleGroup und setze den gewählten RadioButton auf selectedRadioButton 
	public void setToggleGroup() {
		ToggleGroup toggleGroup = new ToggleGroup();
		rbTortendiagramm.setToggleGroup(toggleGroup);
		rbSaeulendiagramm.setToggleGroup(toggleGroup);
	}
	
	//Statistik Methoden
	//Add CheckBoxes to vbStatistikBenutzer
	ArrayList<RadioButton> radioButtonsBenutzer = new ArrayList<>();
	ToggleGroup toggleGroupBenutzer = new ToggleGroup();
	public void setRbBenutzerStatistik(){
		try {
			//Erstelle neue CheckBox
			for(Benutzer einBenutzer : Datenbank.readBenutzer()) {
				RadioButton rb = new RadioButton(einBenutzer.getName());
				rb.setToggleGroup(toggleGroupBenutzer);
				radioButtonsBenutzer.add(rb);
				if(einBenutzer.getName().equals("HAUSHALT"))
					rb.setSelected(true);
			}
			//Füge CheckBoxen der VBox hinzu
			vbStatistikBenutzer.getChildren().addAll(radioButtonsBenutzer);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Set CheckBoxes on Action															
	public void handleRadioButtonActionBenutzerStatistik(RadioButton rb) {
		List<BenutzerFX> lBenutzerFX = olBenutzer.stream().collect(Collectors.toList());
		int index = radioButtonsBenutzer.indexOf(rb);
		BenutzerFX benutzerFX = lBenutzerFX.get(index);
		if (rb.isSelected()) {
//			    benutzerFX.doSomething();												// perform action when CheckBox is not selected - Methode hinterlegen
		} 
	}
	
	//Tortendiagramme erstellen und Pane pStatistk hinzufügen
	@FXML public void showTortendiagramm(ActionEvent event) {
																						//Benutzer(immer nur einer möglich) und Zeitraum hinzufügen
		//Tortendiagramm Einnahmen erstellen
		PieChart tortenDiagrammEinnahmen = new PieChart();
		HBox hb1 = new HBox(tortenDiagrammEinnahmen);
		tortenDiagrammEinnahmen.setTitle("Einnahmen");
		//Tortendiagramm Ausgaben erstellen
		PieChart tortenDiagrammAusgaben = new PieChart();
		HBox hb2 = new HBox(tortenDiagrammAusgaben);
		tortenDiagrammAusgaben.setTitle("Ausgaben");
		try {
			//Einnahmen Kategorien auslesen 
			ArrayList<Kategorie> alKategorienEinnahmen = Datenbank.readKategorie(tabEinnahmen.getText());
			//Kategorien durch iterieren Name und Summe dem Einnahmen-Diagramm hinzufügen
			for(Kategorie eineKategorie : alKategorienEinnahmen) {
				eineKategorie.setSummeEintraege(Datenbank.readKategorieSummeEintraege(eineKategorie.getName(), tabEinnahmen.getText()));
				tortenDiagrammEinnahmen.getData().add(new PieChart.Data(eineKategorie.getName(), eineKategorie.getSummeEintraege()));
			}
			//Ausgaben Kategorien auslesen 
			ArrayList<Kategorie> alKategorienAusgaben = Datenbank.readKategorie(tabAusgaben.getText());
			//Kategorien durch iterieren Name und Summe dem Ausgaben-Diagramm hinzufügen
			for(Kategorie eineKategorie : alKategorienAusgaben) {
				eineKategorie.setSummeEintraege(Datenbank.readKategorieSummeEintraege(eineKategorie.getName(), tabAusgaben.getText()));
				tortenDiagrammAusgaben.getData().add(new PieChart.Data(eineKategorie.getName(), eineKategorie.getSummeEintraege()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		HBox hbDiagramme = new HBox(hb1, hb2);
		spTortendiagramm.getChildren().add(hbDiagramme);
		apDiagramme.getChildren().remove(spTortendiagramm);
		apDiagramme.getChildren().add(spTortendiagramm);
	}
	
	@FXML public void showSaulendiagramm(ActionEvent event){
																						//Benutzer(immer nur einer möglich) und Zeitraum hinzufügen
		//Säulendiagramm Einnahmen erstellen
		CategoryAxis xAxeEinnahmen = new CategoryAxis();
		xAxeEinnahmen.setLabel("Kategorien");
		NumberAxis yAxeEinnahmen = new NumberAxis();	
		yAxeEinnahmen.setLabel("Betrag");
		BarChart<String, Number> sauelenDiagramm = new BarChart<>( xAxeEinnahmen, yAxeEinnahmen );
		sauelenDiagramm.setBarGap(10);
		sauelenDiagramm.setCategoryGap(50);
		XYChart.Series<String, Number> seriesEinnahmen = new XYChart.Series<>();
		seriesEinnahmen.setName("Einnahmen");
		XYChart.Series<String, Number> seriesAusgaben = new XYChart.Series<>();
		seriesAusgaben.setName("Ausgaben");
		try {
			//Einnahmen Kategorien auslesen 
			ArrayList<Kategorie> alKategorienEinnahmen;
			alKategorienEinnahmen = Datenbank.readKategorie(tabEinnahmen.getText());
			//Kategorien durch iterieren Name und Summe dem Einnahmen-Diagramm hinzufügen
			for(Kategorie eineKategorie : alKategorienEinnahmen) {
				eineKategorie.setSummeEintraege(Datenbank.readKategorieSummeEintraege(eineKategorie.getName(), tabEinnahmen.getText()));
				seriesEinnahmen.getData().add(new Data<String, Number>(eineKategorie.getName(), eineKategorie.getSummeEintraege()));
			}
			//Einnahmen Kategorien auslesen 
			ArrayList<Kategorie> alKategorienAusgaben;
			alKategorienAusgaben = Datenbank.readKategorie(tabAusgaben.getText());
			//Kategorien durch iterieren Name und Summe dem Einnahmen-Diagramm hinzufügen
			for(Kategorie eineKategorie : alKategorienAusgaben) {
				eineKategorie.setSummeEintraege(Datenbank.readKategorieSummeEintraege(eineKategorie.getName(), tabAusgaben.getText()));
				seriesAusgaben.getData().add(new Data<String, Number>(eineKategorie.getName(), eineKategorie.getSummeEintraege()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sauelenDiagramm.getData().addAll(seriesEinnahmen, seriesAusgaben);
		spSauelendiagramm.getChildren().add(sauelenDiagramm);
		apDiagramme.getChildren().remove(spSauelendiagramm);
		apDiagramme.getChildren().add(spSauelendiagramm);
		
	}
}

