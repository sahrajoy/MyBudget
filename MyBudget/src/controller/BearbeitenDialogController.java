package controller;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
	@FXML TableColumn<EintragFX, LocalDate> dauereintragCol;
		
	@FXML Tab tabBearbeitenDauereintraege;
	
	@FXML TableView<DauereintragFX> tvBearbeitenDauereintraege;
	@FXML TableColumn<DauereintragFX, LocalDate> naechsteFaelligkeitCol;
	@FXML TableColumn<DauereintragFX, String> titelDauereintragCol;
	@FXML TableColumn<DauereintragFX, Double> betragDauereintragCol;
	@FXML TableColumn<DauereintragFX, String> benutzerDauereintragCol;
	@FXML TableColumn<DauereintragFX, Intervall> intervallCol;
	@FXML TableColumn<DauereintragFX, LocalDate> endeDauereintragCol;
	
	//Methoden
	@FXML public void initialize() {
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
	
	//Methode wird im MainController aufgerufen um die Daten zu übergeben
	public void setKategorieFXData() {
		txtBearbeitenKategorieName.setText(kategorieFX.getName());
		getObservableListBenutzer();
		getObservableListDauereintraege();
		getObservableListEintraege();
	}

	//DatePicker auf Able setzen
	@FXML public void setDatePickerEndeDauereintragOnAble() {
		if(cbBearbeitenIntervall.getSelectionModel().getSelectedItem() == Intervall.KEINE)
			dpBearbeitenEndeDauereintrag.setDisable(true);
		else
			dpBearbeitenEndeDauereintrag.setDisable(false);
	}
	
	//Kategorie speichern über btnBearbeitenKategorieName
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
	    	if(mainController.getTabPane().getSelectionModel().getSelectedItem() == mainController.tabAusgaben) {	
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
	    }
	}
	
	//Eintrag/Dauereintrag speichern
	@FXML public void btnEintragSpeichern(){
		if(cbBearbeitenIntervall.getValue().toString().equals("keine")) {
			try {
				Datenbank.insertEintrag(new Eintrag(
					dpBearbeitenDatum.getValue(),
					txtBearbeitenTitel.getText(),
					Double.parseDouble(txtBearbeitenBetrag.getText()),
					cbBearbeitenBenutzer.getSelectionModel().getSelectedItem().getModellBenutzer(),
					kategorieFX.getModellKategorie()
						));
				getObservableListEintraege();
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
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}
		}
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
	}

	//TableColumns zuordnen Dauereintraege
	public void tableColumnsEintraege() {
		getObservableListEintraege();
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
		dauereintragCol.setCellValueFactory(new PropertyValueFactory<>("dauereintrag"));
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
			ArrayList<Eintrag> alEintraege = Datenbank.readEintraege(mainController.cbBenutzer.getSelectionModel().getSelectedItem().getBenutzerId(), mainController.tpEinnahmenAusgabenStatistik.getSelectionModel().getSelectedItem().getText());
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
			ArrayList<Dauereintrag> alDauereintraege = Datenbank.readDauereintraege(mainController.cbBenutzer.getSelectionModel().getSelectedItem().getBenutzerId(), mainController.tpEinnahmenAusgabenStatistik.getSelectionModel().getSelectedItem().getText());
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
}
