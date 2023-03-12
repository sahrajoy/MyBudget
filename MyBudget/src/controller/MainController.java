package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
					//Set Favoriten Button aktiv/inaktiv
					btnAusgabenFavoriten.setDisable(true);
					getObservableListFavoritenKategorien();
					if(olFavoriten.size() != 0)
						btnAusgabenFavoriten.setDisable(false);
				}
				if(arg2 == tabEinnahmen) {
					showStackEinnahmenUebersicht(null);	
					btnEinnahmenUebersicht.setDefaultButton(true);
					//Set Favoriten Button aktiv/inaktiv
					btnEinnahmenFavoriten.setDisable(true);
					getObservableListFavoritenKategorien();
					if(olFavoriten.size() != 0)
						btnEinnahmenFavoriten.setDisable(false);
				}
			}
		});
		//Benutzer auslesen und der ObserverList hinzufügen
		getObservableListBenutzer();
		cbBenutzer.setItems(olBenutzer);
		cbBenutzer.getSelectionModel().select(olBenutzer.stream().filter(b -> b.getName().equals("HAUSHALT")).findFirst().orElse(null));
		//Einnahmen Übersicht
		btnEinnahmenUebersicht.setDefaultButton(true);
		setSummeKategorien();
		ladeUebersicht();
		ladeStatistik();
		addButtonToDauereintraegeTable();
		addButtonToUebersichtTable();
		setFavoriteButtonAbleOrDisable();
	}
	
	//Zeitraum Anzeigen
	LocalDate anfangZeitraum = LocalDate.now().withDayOfMonth(1);
	int letzterTagMonat = LocalDate.now().lengthOfMonth();
	int letzterTagJahr = LocalDate.now().lengthOfYear();
	LocalDate endeZeitraum = anfangZeitraum.withDayOfMonth(letzterTagMonat);
	String periodeZeitraum = null;
		
	//BENUTZER------------------------------------------------------------------------------------------------------------------------------
	@FXML ObservableList<BenutzerFX> olBenutzer = FXCollections.observableArrayList();		
	
	@FXML HBox hbBenutzer;
	@FXML Button bBenutzerAnlegenEntfernen; 			
	@FXML ComboBox<BenutzerFX> cbBenutzer;
	//ActionEvent cbBenutzer
	@FXML public void datenAktualisieren(){	
		if(spUebersicht.isVisible()) 
			ladeUebersicht();
		else if(spFavoriten.isVisible())
			ladeFavoriten();
		else if(spDauereintraege.isVisible()) 
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
	
	//TABPANES
	@FXML TabPane tpEinnahmenAusgabenStatistik;
	public TabPane getTabPane() {
		return tpEinnahmenAusgabenStatistik;
	}
		
	//TAB EINNAHMEN
	@FXML Tab tabEinnahmen;
	@FXML AnchorPane apEinnahmen;
	@FXML Button btnEinnahmenUebersicht;	
	@FXML Button btnEinnahmenFavoriten;			//Set on disable bis Favoriten hinzugefügt wurden, wenn Favoriten vorhanden und Button geklickt dann setTabsFavoritenAusgaben();
	@FXML Button btnEinnahmenDauereintraege;
	
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
	@FXML Button btnAusgabenUebersicht;
	@FXML Button btnAusgabenFavoriten;							//Set on disable bis Favoriten hinzugefügt wurden, wenn Favoriten vorhanden und Button geklickt dann setTabsFavoritenAusgaben();
	@FXML Button btnAusgabenDauereintraege;
	
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
				getObservableListBenutzer();
				cbBenutzer.setItems(olBenutzer);
				cbBenutzer.getSelectionModel().select(olBenutzer.stream().filter(b -> b.getName().equals("HAUSHALT")).findFirst().orElse(null));
				}
			else 
				return;			
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//ÜBERSICHT ----------------------------------------------------------------------------------------------------------------------------
	@FXML ObservableList<KategorieFX> olKategorie = FXCollections.observableArrayList();
	@FXML ObservableList<String> olSortierungUebersicht = FXCollections.observableArrayList("Kategorie A-Z", "Kategorie Z-A", "Betrag aufsteigend","Betrag absteigend");
	
	//StackPane Übersicht
	@FXML StackPane spUebersicht;
	
	@FXML HBox hbUebersichtButtonsZeitraum;
	@FXML Button btnUebersichtMonat;
	@FXML Button btnUebersichtJahr;
	
	@FXML HBox hbUebersichtZeitraum;
	@FXML Button btnUebersichtPfeilZurueck;
	@FXML Label lblUebersichtZeitraum;
	@FXML Button btnUebersichtPfeilVorwaerts;
	
	@FXML GridPane gpUebersichtPlusUndSortieren;
	@FXML Button btnPlus;
	@FXML Label lblUebersichtSortierung;
	@FXML ComboBox<String> cbUebersichtSortierung;
	
	@FXML AnchorPane apUebersicht;
	
	//TableView Übersicht
	@FXML TableView<KategorieFX> tvUebersicht;
	@FXML TableColumn<KategorieFX, String> kategorieCol;
	@FXML TableColumn<KategorieFX, Double> summeCol;
	
	//Inhalt für Übersicht laden
	public void ladeUebersicht() {
		setPeriodeMonat();
		btnUebersichtMonat.setDefaultButton(true);
		getZeitraum();
		setSummeKategorien();
		tableColumnsUebersicht();
		cbUebersichtSortierung.getSelectionModel().select("Kategorie A-Z");
		cbUebersichtSortierung.setItems(olSortierungUebersicht);
		tvUebersicht.setItems(olKategorie);
		tvUebersicht.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}
	
	//Kategorien aus Datenbank auslesen und ObservableList hinzufügen
	public void getObservableListKategorien() {
		try {
			ArrayList<Kategorie> alKategorien = Datenbank.readKategorie(tpEinnahmenAusgabenStatistik.getSelectionModel().getSelectedItem().getText());
			olKategorie.clear();
			for(Kategorie eineKategorie : alKategorien) {
				eineKategorie.setSummeEintraege(Datenbank.readKategorieSummeEintraege(eineKategorie.getName()));
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
                    		kategorieFX  = getTableView().getItems().get(getIndex());
                    		Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
                    	    confirmationDialog.setTitle("Kategorie löschen");
                    	    confirmationDialog.setContentText("Soll die Kategorie wirklich gelöscht werden, Änderungen können nicht rückgängig gemacht werden!");
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
		
		//ActionEvent btnUebersichtMonat
	@FXML public void setPeriodeMonat(){
		periodeZeitraum = "Monat";
		anfangZeitraum = LocalDate.now().withDayOfMonth(1);
		endeZeitraum = anfangZeitraum.withDayOfMonth(letzterTagMonat);
		getZeitraum();
	}
	
	//ActionEvent btnUebersichtJahr
	@FXML public void setPeriodeJahr(){
		periodeZeitraum = "Jahr";
		btnUebersichtMonat.setDefaultButton(false);
		anfangZeitraum = LocalDate.now().withDayOfYear(1);
		endeZeitraum = anfangZeitraum.withDayOfYear(letzterTagJahr);
		getZeitraum();
	}
	
	//ActionEvent btnUebersichtPfeilVorwaerts
	@FXML public void periodeZeitraumVor(){
		if(periodeZeitraum.equalsIgnoreCase("Monat")) {
			anfangZeitraum = anfangZeitraum.plus(1, ChronoUnit.MONTHS);
			endeZeitraum = endeZeitraum.plus(1, ChronoUnit.MONTHS);
			getZeitraum();
		}
		else if(periodeZeitraum.equalsIgnoreCase("Jahr")) {
			anfangZeitraum = anfangZeitraum.plus(1, ChronoUnit.YEARS);
			endeZeitraum = endeZeitraum.plus(1, ChronoUnit.YEARS);
			getZeitraum();
		}
	}
	
	//ActionEvent btnUebersichtPfeilZurueck
	@FXML public void periodeZeitraumZurueck(){
		if(periodeZeitraum == "Monat") {
			anfangZeitraum = anfangZeitraum.minus(1, ChronoUnit.MONTHS);
			endeZeitraum = endeZeitraum.minus(1, ChronoUnit.MONTHS);
			getZeitraum();
		}
		if(periodeZeitraum == "Jahr") {
			anfangZeitraum = anfangZeitraum.minus(1, ChronoUnit.YEARS);
			endeZeitraum = endeZeitraum.minus(1, ChronoUnit.YEARS);
			getZeitraum();
		}	
	}
	
	//Retourniert einen String für lblUebersichtZeitraum
	public void getZeitraum() {
		lblUebersichtZeitraum.setText(anfangZeitraum.format(formatter) + " bis " + endeZeitraum.format(formatter));
	}
	
	//Einträge nach Kategorie auslesen und summieren
	public void setSummeKategorien() {
		double setSummeEintraege = 0;
		try {
			ArrayList<Kategorie> alKategorien = Datenbank.readKategorie(tpEinnahmenAusgabenStatistik.getSelectionModel().getSelectedItem().getText());
			for(Kategorie eineKategorie : alKategorien) {
				setSummeEintraege = Datenbank.readKategorieSummeEintraege(eineKategorie.getName());
				eineKategorie.setSummeEintraege(setSummeEintraege);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}				
	}
	
	//Sortierung der Kategorien in der Übersicht
	@FXML public void sortierungUebersicht(ActionEvent event) {
		if(cbUebersichtSortierung.getSelectionModel().getSelectedItem().equalsIgnoreCase("Kategorie A-Z"))
			olKategorie.sort(Comparator.comparing(KategorieFX::getName));
		else if(cbUebersichtSortierung.getSelectionModel().getSelectedItem().equalsIgnoreCase("Kategorie Z-A"))
			olKategorie.sort(Comparator.comparing(KategorieFX::getName).reversed());
		else if(cbUebersichtSortierung.getSelectionModel().getSelectedItem().equalsIgnoreCase("Betrag aufsteigend"))
			olKategorie.sort(Comparator.comparing(KategorieFX::getSummeEintraege));
		else if(cbUebersichtSortierung.getSelectionModel().getSelectedItem().equalsIgnoreCase("Betrag absteigend"))
			olKategorie.sort(Comparator.comparing(KategorieFX::getSummeEintraege).reversed());
	}
	
	//FAVORITEN ----------------------------------------------------------------------------------------------------------------------------
	@FXML ObservableList<KategorieFX> olFavoriten = FXCollections.observableArrayList();
	
	//StackPane Favoriten
	@FXML StackPane spFavoriten;		
	@FXML HBox hbFavoritenButtonsZeitraum;
	@FXML Button btnFavoritenTag;
	@FXML Button btnFavoritenWoche;
	@FXML Button btnFavoritenMonat;
	@FXML Button btnFavoritenJahr;
	
	@FXML HBox hbFavoritenZeitraum;
	@FXML Button btnFavoritenPfeilZurueck;
	@FXML Label lblFavoritenZeitraum;
	@FXML Button btnFavoritenPfeilVorwaerts;
	
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
	@FXML ComboBox<Intervall> cbFavoritenIntervall;
	@FXML Label lblFavoritenEndeDauereintrag;
	@FXML DatePicker dpFavoritenEndeDauereintrag;
	@FXML Button btnFavoritenSpeichern;					//Prüfungen auf vollständigkeit der eingegebenen Daten hinterlegen und speichern in DB
	
	@FXML TabPane tpFavoriten;

	//Inhalt für Favoriten laden
	public void ladeFavoriten() {
		//Defaults setzen und Listen laden für Eingabezeile
		btnEinnahmenUebersicht.setDefaultButton(false);
		btnAusgabenUebersicht.setDefaultButton(false);
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
	
	//Einnahmen Einträge/Dauereinträge speichern über Favoriten											
	@FXML public void btnSpeichernUeberFavoriten(ActionEvent event) {
		if(cbFavoritenIntervall.getValue().toString().equalsIgnoreCase("keine")) {
			try {
				Datenbank.insertEintrag(new Eintrag(
						dpFavoritenDatum.getValue(),
						txtFavoritenTitel.getText(),
						Double.parseDouble(txtFavoritenBetrag.getText()),
						cbFavoritenBenutzer.getSelectionModel().getSelectedItem().getModellBenutzer(),
						cbFavoritenKategorie.getSelectionModel().getSelectedItem().getModellKategorie(),
						cbFavoritenIntervall.getSelectionModel().getSelectedItem().getIName()
						));
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
	@FXML public Node getObservableListUndTableViewEintraegeNachKategorie() {
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
		datumCol.setPrefWidth(187.34);
		datumCol.setStyle("-fx-alignment: CENTER;");
		TableColumn<EintragFX, String> titelCol = new TableColumn<>("Titel");
		titelCol.setCellValueFactory(new PropertyValueFactory<>("titel"));
		titelCol.setPrefWidth(231.37);
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
		betragCol.setPrefWidth(209.33);
		betragCol.setStyle("-fx-alignment: CENTER;");
		TableColumn<EintragFX, String> benutzerCol = new TableColumn<>("Benutzer");
		benutzerCol.setCellValueFactory(new PropertyValueFactory<>("benutzerName"));
		benutzerCol.setPrefWidth(212.66);
		benutzerCol.setStyle("-fx-alignment: CENTER;");
		TableColumn<EintragFX, String> dauereintragCol = new TableColumn<>("Dauereintrag");
		dauereintragCol.setCellValueFactory(new PropertyValueFactory<>("dauereintrag"));
		dauereintragCol.setPrefWidth(212.66);
		dauereintragCol.setStyle("-fx-alignment: CENTER;");								
		tvFavoriten.getColumns().addAll(datumCol, titelCol, betragCol, benutzerCol, dauereintragCol, addButtonToEintraegeTable());
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
					private final Button btnBearbeiten = new Button("Bearbeiten");
					private final Button btnLöschen = new Button("Löschen");
					HBox hbButtons = new HBox(10, btnBearbeiten, btnLöschen);
					{
						//ActionEvents für Buttons//                    	
						btnBearbeiten.setOnAction((ActionEvent event) -> {
							EintragFX eintragFX  = getTableView().getItems().get(getIndex());
//		                            System.out.println("selectedData: " + data);
						});
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
		buttonCol.setPrefWidth(190);
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
	@FXML DatePicker dpDauereintraegeEndeDauereintrag;
	@FXML Button btnDauereintraegeSpeichern;	
	
	@FXML HBox hbDauereintraegeSortierung;
	@FXML Label lblDauereintraegeSortierung;
	@FXML ComboBox<String> cbDauereintraegeSortierung;
	@FXML AnchorPane apDauereintraege;
	
	//TableView Dauereinträge
	@FXML TableView<DauereintragFX> tvDauereintraege;
	@FXML TableColumn<DauereintragFX, LocalDate> naechsteFaelligkeitCol;
	@FXML TableColumn<DauereintragFX, String> kategorieNameCol;
	@FXML TableColumn<DauereintragFX, String> titelCol;
	@FXML TableColumn<DauereintragFX, Double> betragCol;
	@FXML TableColumn<DauereintragFX, String> benutzerCol;
	@FXML TableColumn<DauereintragFX, Intervall> intervallCol;
	@FXML TableColumn<DauereintragFX, LocalDate> endeDauereintragCol;
	
	//Inhalt für Dauereintrag laden
	@FXML public void ladeDauereintraege() {
		btnEinnahmenUebersicht.setDefaultButton(false);
		btnAusgabenUebersicht.setDefaultButton(false);
		lblDauereintraegeAktuellesDatum.setText(LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
		//Default setzen und Listen laden für Eingabezeile
		dpDauereintraegeDatum.setValue(LocalDate.now());
		cbDauereintraegeKategorie.setItems(olKategorie);
		cbDauereintraegeKategorie.setPromptText("Kategorie wählen");
		txtDauereintraegeTitel.setPromptText("Titel eingeben");
		txtDauereintraegeBetrag.setPromptText("00.00");
		txtDauereintraegeBetrag.setTextFormatter(new TextFormatter<>(converter));
		cbDauereintraegeBenutzer.setItems(olBenutzer);
		cbDauereintraegeBenutzer.getSelectionModel().select(olBenutzer.stream().filter(b -> b.getName().equals("HAUSHALT")).findFirst().orElse(null));
		cbDauereintraegeIntervall.getItems().setAll(Intervall.values());
		cbDauereintraegeIntervall.setValue(Intervall.KEINE);
		dpDauereintraegeEndeDauereintrag.setDisable(true);
		//Sortierung
		cbDauereintraegeSortierung.getSelectionModel().select("Datum aufsteigend");
		cbDauereintraegeSortierung.setItems(olSortierungDauereintraege);
		//TableView
		tvDauereintraege.setItems(olDauereintraege);
		tableColumnsDauereintraege();
		tvDauereintraege.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		}
	
	//Set DatePicker on able
	@FXML public void setDatePickerEndeDauereintragOnAble() {
		if(cbFavoritenIntervall.getSelectionModel().getSelectedItem() == Intervall.KEINE)
			dpFavoritenEndeDauereintrag.setDisable(true);
		else if(cbDauereintraegeIntervall.getSelectionModel().getSelectedItem() == Intervall.KEINE)
			dpDauereintraegeEndeDauereintrag.setDisable(true);
		else
			dpFavoritenEndeDauereintrag.setDisable(false);
		dpDauereintraegeEndeDauereintrag.setDisable(false);
	}
	
	//Dauereinträge nach Benutzer und Einnahmen/Ausgaben und ObservableList hinzufügen
	@FXML public void getObservableListDauereintraege() {
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
		if(cbDauereintraegeIntervall.getValue().toString().equalsIgnoreCase("keine")) {
//			try {
//				Datenbank.insertEintrag(new Eintrag(
//					dpDauereintraegeDatum.getValue(),
//					txtDauereintraegeTitel.getText(),
//					Double.parseDouble(txtDauereintraegeBetrag.getText()),
//					cbDauereintraegeBenutzer.getSelectionModel().getSelectedItem().getModellBenutzer(),
//					cbDauereintraegeKategorie.getSelectionModel().getSelectedItem().getModellKategorie()
//						));
//			} catch (NumberFormatException | SQLException e) {
//				e.printStackTrace();
//			}
		}
		else {
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
		}
		tableColumnsDauereintraege();
	}
			
	//TableColumn mit Buttons erstellen und TableView Dauereinträge zuordnen
	private void addButtonToDauereintraegeTable() {
		TableColumn<DauereintragFX, Void> buttonCol = new TableColumn();
		Callback<TableColumn<DauereintragFX, Void>, TableCell<DauereintragFX, Void>> cellFactory = new Callback<TableColumn<DauereintragFX, Void>, TableCell<DauereintragFX, Void>>() {
			@Override
			public TableCell<DauereintragFX, Void> call(final TableColumn<DauereintragFX, Void> param) {
				final TableCell<DauereintragFX, Void> cell = new TableCell<DauereintragFX, Void>() {
					private final Button btnBearbeiten = new Button("Bearbeiten");
					private final Button btnLöschen = new Button("Löschen");
					HBox hbButtons = new HBox(10, btnBearbeiten, btnLöschen);
					{
						//ActionEvents für Buttons//                    	
						btnBearbeiten.setOnAction((ActionEvent event) -> {
							//
						});
						btnLöschen.setOnAction((ActionEvent event) -> {
							DauereintragFX dauereintragFX = getTableView().getItems().get(getIndex());
							Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
							confirmationDialog.setTitle("Dauereintrag löschen");
							confirmationDialog.setContentText("Soll der Dauereintrag wirklich gelöscht werden, Änderungen können nicht rückgängig gemacht werden!");
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
		buttonCol.setPrefWidth(190);
		buttonCol.setStyle("-fx-alignment: CENTER;");
		tvDauereintraege.getColumns().add(buttonCol);
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
		
	//Sortierung der Dauereinträge
	@FXML public void sortierungDauerintraege(ActionEvent event) {
		if(cbDauereintraegeSortierung.getSelectionModel().getSelectedItem().equalsIgnoreCase("Kategorie A-Z"))
			olDauereintraege.sort(Comparator.comparing(DauereintragFX::getKategorieName));
		else if(cbDauereintraegeSortierung.getSelectionModel().getSelectedItem().equalsIgnoreCase("Kategorie Z-A"))
			olDauereintraege.sort(Comparator.comparing(DauereintragFX::getKategorieName).reversed());
		else if(cbDauereintraegeSortierung.getSelectionModel().getSelectedItem().equalsIgnoreCase("Betrag aufsteigend"))
			olDauereintraege.sort(Comparator.comparing(DauereintragFX::getBetrag));
		else if(cbDauereintraegeSortierung.getSelectionModel().getSelectedItem().equalsIgnoreCase("Betrag absteigend"))
			olDauereintraege.sort(Comparator.comparing(DauereintragFX::getBetrag).reversed());
		else if(cbDauereintraegeSortierung.getSelectionModel().getSelectedItem().equalsIgnoreCase("Datum aufsteigend"))
			olDauereintraege.sort(Comparator.comparing(DauereintragFX::getNaechsteFaelligkeit));
		else if(cbDauereintraegeSortierung.getSelectionModel().getSelectedItem().equalsIgnoreCase("Datum absteigend"))
			olDauereintraege.sort(Comparator.comparing(DauereintragFX::getNaechsteFaelligkeit).reversed());
	}
	
	//TAB STATISTIK-------------------------------------------------------------------------------------------------------------------------
	@FXML Tab tabStatistik;
	@FXML HBox hbStatistik;
	@FXML VBox vbStatistikButtonsDiagramm;
	@FXML HBox hbBilderDiagramme;
	@FXML ImageView ivTortendiagramm;
	@FXML ImageView ivSaulendiagramm;
	@FXML HBox hbRadioButtonsDiagramme;
	@FXML RadioButton rbTortendiagramm; 
	@FXML RadioButton rbSaeulendiagramm;
	@FXML VBox vbStatistikZeitraum;
	@FXML HBox hbStatistikButtonsZeitraum;
	@FXML Button bStatistikMonat;
	@FXML Button bStatistikJahr;
	@FXML HBox hbStatistikZeitraum;
	@FXML Button bStatistikPfeilZurueck;
	@FXML Label lblStatistikZeitraum;
	@FXML Button bStatistikPfeilVorwaerts;
	@FXML VBox vbStatistikBenutzer;
	@FXML Pane pStatistik;
	
	//Inhalt für Statistik laden
	@FXML public void ladeStatistik() {
		setCbBenutzerStatistik();
	}
	
	//Statistik Methoden
	//Add CheckBoxes to vbStatistikBenutzer
	ArrayList<CheckBox> checkBoxesBenutzer = new ArrayList<>();
	public void setCbBenutzerStatistik(){
		try {
			//Erstelle neue CheckBox
			for(Benutzer einBenutzer : Datenbank.readBenutzer()) {
				CheckBox checkBox = new CheckBox(einBenutzer.getName());
				checkBoxesBenutzer.add(checkBox);
				if(einBenutzer.getName().equals("HAUSHALT"))
					checkBox.setSelected(true);
			}
			//Füge CheckBoxen der VBox hinzu
			vbStatistikBenutzer.getChildren().addAll(checkBoxesBenutzer);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Set CheckBoxes on Action															
	public void handleCheckBoxActionBenutzerStatistik(CheckBox checkBox) {
		List<BenutzerFX> lBenutzerFX = olBenutzer.stream().collect(Collectors.toList());
		int index = checkBoxesBenutzer.indexOf(checkBox);
		BenutzerFX benutzerFX = lBenutzerFX.get(index);
		if (checkBox.isSelected()) {
//			    benutzerFX.doSomething();												// perform action when CheckBox is not selected - Methode hinterlegen
		} 
	}
		
}


