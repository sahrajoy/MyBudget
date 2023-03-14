package controller;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
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

public class BearbeitenDialogController extends Dialog<ButtonType> {
	@FXML ObservableList<BenutzerFX> olBenutzer = FXCollections.observableArrayList();		
	@FXML ObservableList<KategorieFX> olKategorien = FXCollections.observableArrayList();		
	@FXML ObservableList<DauereintragFX> olDauereintraege = FXCollections.observableArrayList();	
	@FXML ObservableList<EintragFX> olEintraege = FXCollections.observableArrayList();	
	
	//Format Datum
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	//Format Währung
	NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
	//Format Text zeigen in Decimalzahl
	NumberStringConverter converter = new NumberStringConverter("#0.00");
		
	@FXML HBox hbBearbeitenButtonsZeitraum;
	@FXML Button btnBearbeitenTag;
	@FXML Button btnBearbeitenWoche;
	@FXML Button btnBearbeitenMonat;
	@FXML Button btnBearbeitenJahr;
	
	@FXML HBox hbBearbeitenZeitraum;
	@FXML Button btnBearbeitenPfeilZurueck;
	@FXML Label lblBearbeitenZeitraum;
	@FXML Button btnBearbeitenPfeilVorwaerts;
	
	@FXML HBox hbBearbeitenKategorieName;
	@FXML TextField txtBearbeitenKategorieName;
	@FXML Button btnBearbeitenKategorieName;
	
	@FXML HBox hbBearbeitenEingabezeile;
	@FXML Label lblBearbeitenDatum;
	@FXML DatePicker dpBearbeitenDatum;
	@FXML Label lblBearbeitenTitel;
	@FXML TextField txtBearbeitenTitel;
	@FXML Label lblBearbeitenBetrag;
	@FXML TextField txtBearbeitenBetrag;
	@FXML Label lblBearbeitenBenutzer;
	@FXML ComboBox<BenutzerFX> cbBearbeitenBenutzer;
	@FXML Label lblBearbeitenDauereintrag;
	@FXML ComboBox<Intervall> cbBearbeitenIntervall;
	@FXML Label lblBearbeitenEndeDauereintrag;
	@FXML DatePicker dpBearbeitenEndeDauereintrag;
	@FXML Button btnBearbeitenSpeichern;
	
	@FXML TabPane tpBearbeiten;
	
	@FXML Tab tabBearbeitenEintraege;
	
	@FXML TableView<EintragFX> tvBearbeitenEintraege;
	@FXML TableColumn<EintragFX, LocalDate> datumCol;
	@FXML TableColumn<EintragFX, String> titelCol;
	@FXML TableColumn<EintragFX, Double> betragCol;
	@FXML TableColumn<EintragFX, String> benutzerCol;
	@FXML TableColumn<EintragFX, Intervall> intervallDauereintragCol;
	@FXML TableColumn<EintragFX, Node> bearbeitenEintragCol;
		
	@FXML Tab tabBearbeitenDauereintraege;
	
	@FXML TableView<DauereintragFX> tvBearbeitenDauereintraege;
	@FXML TableColumn<DauereintragFX, LocalDate> naechsteFaelligkeitCol;
	@FXML TableColumn<DauereintragFX, String> titelDauereintragCol;
	@FXML TableColumn<DauereintragFX, Double> betragDauereintragCol;
	@FXML TableColumn<DauereintragFX, String> benutzerDauereintragCol;
	@FXML TableColumn<DauereintragFX, Intervall> intervallCol;
	@FXML TableColumn<DauereintragFX, LocalDate> endeDauereintragCol;
	@FXML TableColumn<DauereintragFX, Node> bearbeitenDauereintragCol;
	
	//Methoden
	@FXML public void initialize() {
		getObservableListBenutzer();
		btnBearbeitenMonat.setDefaultButton(true);
		tvBearbeitenDauereintraege.setItems(olDauereintraege);
		tvBearbeitenEintraege.setItems(olEintraege);
		dpBearbeitenDatum.setValue(LocalDate.now());
		txtBearbeitenTitel.setPromptText("Titel eingeben");
		txtBearbeitenBetrag.setPromptText("00.00");
		txtBearbeitenBetrag.setTextFormatter(new TextFormatter<>(converter));
		cbBearbeitenBenutzer.setItems(olBenutzer);
		cbBearbeitenBenutzer.getSelectionModel().select(olBenutzer.stream().filter(b -> b.getName().equals("HAUSHALT")).findFirst().orElse(null));
		cbBearbeitenIntervall.getItems().setAll(Intervall.values());
		cbBearbeitenIntervall.setValue(Intervall.KEINE);
		dpBearbeitenEndeDauereintrag.setDisable(true);
		txtBearbeitenKategorieName.setPromptText("Kategoriename eingeben");
	}
	
	//MainController initialisieren
	private MainController mainController;
	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}
	//KategorieFX initialisieren
	private KategorieFX kategorieFX;
	public void setKategorieFX(KategorieFX kategorieFX) {
		this.kategorieFX = kategorieFX;
	}
	
	//Methode wird im MainController aufgerufen um die Daten der KategorieFX zu übergeben
	public void setKategorieFXData() {
		txtBearbeitenKategorieName.setText(kategorieFX.getName());
		getObservableListEintraege();
		getObservableListDauereintraege();
		tableColumnsEintraege();
		tableColumnsDauereintraege();
	}

	//DatePicker dpBearbeitenEndeDauereintrag auf Able setzen
	@FXML public void setDatePickerEndeDauereintragOnAble() {
		if(cbBearbeitenIntervall.getSelectionModel().getSelectedItem() == Intervall.KEINE)
			dpBearbeitenEndeDauereintrag.setDisable(true);
		else
			dpBearbeitenEndeDauereintrag.setDisable(false);
	}
	
	//Kategorie speichern über Button btnBearbeitenKategorieName
	@FXML public void kategorieSpeichern(ActionEvent event) {	
		boolean exists = false;
	    for (KategorieFX einKategorieFX : olKategorien) {
	        if (einKategorieFX.getName().equalsIgnoreCase(txtBearbeitenKategorieName.getText())) {	
	            new Alert(AlertType.ERROR, "Kategorie existiert bereits!").showAndWait();
	            exists = true;
	            break;
	        }
	    }
	    if (!exists) {
	    	if(mainController.getTabPane().getSelectionModel().getSelectedItem() == mainController.tabEinnahmen) {	
	    		if(kategorieFX.getName() == "") {
	    			Kategorie k = new Kategorie(true, txtBearbeitenKategorieName.getText(), false);
	    			try {
	    				Datenbank.insertKategorie(k);	
	    				KategorieFX kFX = new KategorieFX(k);
	    				olKategorien.add(kFX);	
	    				kategorieFX = kFX;
	    			} catch (SQLException e) {
	    				e.printStackTrace();
	    			}
	    		}
	    		else {
	    			try {
						Datenbank.updateKategorieName(kategorieFX.getModellKategorie(), txtBearbeitenKategorieName.getText());
					} catch (SQLException e) {
						e.printStackTrace();
					}
	    		}
	    	}
	    	if(mainController.getTabPane().getSelectionModel().getSelectedItem() == mainController.tabAusgaben) {	
	    		if(kategorieFX.getName() == "") {
	    			Kategorie k1 = new Kategorie(false, txtBearbeitenKategorieName.getText(), false);
	    			try {
	    				Datenbank.insertKategorie(k1);		
	    				KategorieFX kFX1 = new KategorieFX(k1);
	    				olKategorien.add(kFX1);	
	    				kategorieFX = kFX1;
	    			} catch (SQLException e) {
	    				e.printStackTrace();
	    			}
	    		}
		    	else {
	    			try {
						Datenbank.updateKategorieName(kategorieFX.getModellKategorie(), txtBearbeitenKategorieName.getText());
					} catch (SQLException e) {
						e.printStackTrace();
					}
		    	}
	    	}
	    }
	}
	
	//Eintrag/Dauereintrag speichern über Button btnBearbeitenSpeichern
	@FXML public void btnEintragSpeichern(){
		if(cbBearbeitenIntervall.getValue().toString().equals("keine")) {
			try {
				Datenbank.insertEintrag(new Eintrag(
					dpBearbeitenDatum.getValue(),
					txtBearbeitenTitel.getText(),
					Double.parseDouble(txtBearbeitenBetrag.getText()),
					cbBearbeitenBenutzer.getSelectionModel().getSelectedItem().getModellBenutzer(),
					kategorieFX.getModellKategorie(),
					cbBearbeitenIntervall.getSelectionModel().getSelectedItem()
						));
				getObservableListEintraege();
				tableColumnsEintraege();
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				Datenbank.insertDauereintrag(new Dauereintrag(
					dpBearbeitenDatum.getValue(),
					txtBearbeitenTitel.getText(),
					Double.parseDouble(txtBearbeitenBetrag.getText()),
					cbBearbeitenBenutzer.getSelectionModel().getSelectedItem().getModellBenutzer(),
					cbBearbeitenIntervall.getSelectionModel().getSelectedItem(),
					dpBearbeitenEndeDauereintrag.getValue(),
					kategorieFX.getModellKategorie()
						));
				getObservableListDauereintraege();
				tableColumnsDauereintraege();
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//TableColumns zuordnen Eintraege tvBearbeitenEintraege
	public void tableColumnsEintraege() {
//		getObservableListEintraege();
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
		titelCol.setCellValueFactory(new PropertyValueFactory<>("titel"));
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
		benutzerCol.setCellValueFactory(new PropertyValueFactory<>("benutzerName"));
		intervallDauereintragCol.setCellValueFactory(new PropertyValueFactory<>("intervall"));
		addButtonToEintraegeTable();
	}
	
	//TableColumn mit Buttons erstellen und TableView tvBearbeitenEintraege hinzufügen 
	EintragFX e = null;
	private void addButtonToEintraegeTable() {
		bearbeitenEintragCol.setCellFactory(column -> new TableCell<>() {
			private final Button btnBearbeiten = new Button("Update");
			private final Button btnLöschen = new Button("Löschen");
			HBox hbButtons = new HBox(10, btnBearbeiten, btnLöschen);
			{
				//ActionEvents für Buttons//                    	
				btnBearbeiten.setOnAction((ActionEvent event) -> {
					titelCol.setEditable(true);
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
							getObservableListEintraege();
							tableColumnsEintraege();
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
	
	//TableColumns zuordnen Dauereintraege tvBearbeitenDauereintraege
	public void tableColumnsDauereintraege() {
//		getObservableListDauereintraege();
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
		naechsteFaelligkeitCol.setEditable(true);
		titelDauereintragCol.setCellValueFactory(new PropertyValueFactory<>("titel"));
		betragDauereintragCol.setCellValueFactory(new PropertyValueFactory<>("betrag"));
		betragDauereintragCol.setCellFactory(column -> new TableCell<DauereintragFX, Double>() {
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
		benutzerDauereintragCol.setCellValueFactory(new PropertyValueFactory<>("benutzerName"));
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
		addButtonToDauereintraegeTable(); 
	}
	
	//TableColumn mit Buttons erstellen und TableView tvBearbeitenDauereintraege hinzufügen 
	private void addButtonToDauereintraegeTable() {
		bearbeitenDauereintragCol.setCellFactory(column -> new TableCell<>() {
			private final Button btnBearbeiten = new Button("Update");
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
					confirmationDialog.setTitle("Eintrag löschen");
					confirmationDialog.setContentText("Soll der Eintrag wirklich gelöscht werden, Änderungen können nicht rückgängig gemacht werden!");
					Optional<ButtonType> clickedButton = confirmationDialog.showAndWait();
					if (clickedButton.isPresent() && clickedButton.get() == ButtonType.OK) {
						try {
							Datenbank.deleteDauereintrag(dauereintragFX.getDauereintragId());
							getObservableListDauereintraege();
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
		
	//Benutzer auslesen und der ObserverList hinzufügen
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
	//Einträge auslesen und der ObserverList hinzufügen
	public void getObservableListEintraege(){
		try {
			ArrayList<Eintrag> alEintraege = Datenbank.readEintraegeNachKategorie(mainController.cbBenutzer.getSelectionModel().getSelectedItem().getBenutzerId(), mainController.tpEinnahmenAusgabenStatistik.getSelectionModel().getSelectedItem().getText(), kategorieFX.getKategorieId());
			olEintraege.clear();
			for(Eintrag einEintrag : alEintraege)
				olEintraege.add(new EintragFX(einEintrag));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//Dauereinträge auslesen und der ObserverList hinzufügen
	public void getObservableListDauereintraege(){
		try {
			ArrayList<Dauereintrag> alDauereintraege = Datenbank.readDauereintraegeNachKategorie(mainController.cbBenutzer.getSelectionModel().getSelectedItem().getBenutzerId(), mainController.tpEinnahmenAusgabenStatistik.getSelectionModel().getSelectedItem().getText(), kategorieFX.getKategorieId());
			olDauereintraege.clear();
			for(Dauereintrag einDauereintrag : alDauereintraege)
				olDauereintraege.add(new DauereintragFX(einDauereintrag));	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Kategorien auslesen und der ObserverList hinzufügen
	@FXML public void showKategorie() {
		try {
			ArrayList<Kategorie> alKategorien =  Datenbank.readKategorie(mainController.getTabPane().getSelectionModel().getSelectedItem().getText());
			olKategorien.clear();
			for(Kategorie einKategorie : alKategorien)
				olKategorien.add(new KategorieFX(einKategorie));	
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.toString());
		}
	}	
	
	//Zeitraum Anzeigen
	int letzterTagMonat = LocalDate.now().lengthOfMonth();
	int letzterTagJahr = LocalDate.now().lengthOfYear();
	LocalDate anfangZeitraum = LocalDate.now().withDayOfMonth(1);
	LocalDate endeZeitraum = anfangZeitraum.withDayOfMonth(letzterTagMonat);
	String periodeZeitraum = null;
	
	//ActionEvent btnBearbeitenTag
	@FXML public void setPeriodeTag(){
		//Button btnBearbeitenMonat wieder auf default false setzen
		btnBearbeitenMonat.setDefaultButton(false);
		//Periden Daten zurückgeben
		periodeZeitraum = "'day'";
		anfangZeitraum = LocalDate.now();
		endeZeitraum = LocalDate.now();
		lblBearbeitenZeitraum.setText(anfangZeitraum.format(formatter));
	}
		
	//ActionEvent btnBearbeitenWoche
	@FXML public void setPeriodeWoche(){
		//Button btnBearbeitenMonat wieder auf default false setzen
		btnBearbeitenMonat.setDefaultButton(false);
		//Periden Daten zurückgeben
		periodeZeitraum = "'week'";
		anfangZeitraum = LocalDate.now().with(DayOfWeek.MONDAY);
		endeZeitraum = LocalDate.now().with(DayOfWeek.SUNDAY);
		//lblBearbeitenZeitraum neuen String hinterlegen
		getZeitraum();
	}
		
	//ActionEvent btnBearbeitenMonat
	@FXML public void setPeriodeMonat(){
		//Periden Daten zurückgeben
		periodeZeitraum = "'month'";
		anfangZeitraum = LocalDate.now().withDayOfMonth(1);
		endeZeitraum = anfangZeitraum.withDayOfMonth(letzterTagMonat);
		//lblBearbeitenZeitraum neuen String hinterlegen
		getZeitraum();
	}
		
	//ActionEvent btnBearbeitenJahr
	@FXML public void setPeriodeJahr(){
		//Button btnBearbeitenMonat wieder auf default false setzen
		btnBearbeitenMonat.setDefaultButton(false);
		//Periden Daten zurückgeben
		periodeZeitraum = "'year'";
		anfangZeitraum = LocalDate.now().withDayOfYear(1);
		endeZeitraum = anfangZeitraum.withDayOfYear(letzterTagJahr);
		//lblBearbeitenZeitraum neuen String hinterlegen
		getZeitraum();
	}
		
	//ActionEvent btnUebersichtPfeilVorwaerts
	@FXML public void periodeZeitraumVor(){
		if(periodeZeitraum.equalsIgnoreCase("'day'")) {
			anfangZeitraum = anfangZeitraum.plus(1, ChronoUnit.DAYS);
			endeZeitraum = endeZeitraum.plus(1, ChronoUnit.DAYS);
			//lblBearbeitenZeitraum neuen String hinterlegen
			lblBearbeitenZeitraum.setText(anfangZeitraum.format(formatter));
		}
		else if(periodeZeitraum.equalsIgnoreCase("'week'")) {
			anfangZeitraum = anfangZeitraum.plus(1, ChronoUnit.WEEKS);
			endeZeitraum = endeZeitraum.plus(1, ChronoUnit.WEEKS);
			//lblBearbeitenZeitraum neuen String hinterlegen
			getZeitraum();
		}
		else if(periodeZeitraum.equalsIgnoreCase("'month'")) {
			anfangZeitraum = anfangZeitraum.plus(1, ChronoUnit.MONTHS);
			endeZeitraum = endeZeitraum.plus(1, ChronoUnit.MONTHS);
			//lblBearbeitenZeitraum neuen String hinterlegen
			getZeitraum();
		}
		else if(periodeZeitraum.equalsIgnoreCase("'year'")) {
			anfangZeitraum = anfangZeitraum.plus(1, ChronoUnit.YEARS);
			endeZeitraum = endeZeitraum.plus(1, ChronoUnit.YEARS);
			//lblBearbeitenZeitraum neuen String hinterlegen
			getZeitraum();
		}
	}
		
	//ActionEvent btnUebersichtPfeilZurueck
	@FXML public void periodeZeitraumZurueck(){
		if(periodeZeitraum == "'day'") {
			anfangZeitraum = anfangZeitraum.minus(1, ChronoUnit.DAYS);
			endeZeitraum = endeZeitraum.minus(1, ChronoUnit.DAYS);
			//lblBearbeitenZeitraum neuen String hinterlegen
			lblBearbeitenZeitraum.setText(anfangZeitraum.format(formatter));
		}
		else if(periodeZeitraum == "'week'") {
			anfangZeitraum = anfangZeitraum.minus(1, ChronoUnit.WEEKS);
			endeZeitraum = endeZeitraum.minus(1, ChronoUnit.WEEKS);
			//lblBearbeitenZeitraum neuen String hinterlegen
			getZeitraum();
		}
		else if(periodeZeitraum == "'month'") {
			anfangZeitraum = anfangZeitraum.minus(1, ChronoUnit.MONTHS);
			endeZeitraum = endeZeitraum.minus(1, ChronoUnit.MONTHS);
			//lblBearbeitenZeitraum neuen String hinterlegen
			getZeitraum();
		}
		else if(periodeZeitraum == "'year'") {
			anfangZeitraum = anfangZeitraum.minus(1, ChronoUnit.YEARS);
			endeZeitraum = endeZeitraum.minus(1, ChronoUnit.YEARS);
			//lblBearbeitenZeitraum neuen String hinterlegen
			getZeitraum();
		}	
	}
		
	//Retourniert einen String für lblUebersichtZeitraum
	public void getZeitraum() {
		lblBearbeitenZeitraum.setText(anfangZeitraum.format(formatter) + " bis " + endeZeitraum.format(formatter));
	}
			
}
