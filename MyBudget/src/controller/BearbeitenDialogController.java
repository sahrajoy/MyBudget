package controller;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.util.converter.NumberStringConverter;
import model.Benutzer;
import model.BenutzerFX;
import model.Datenbank;
import model.DauereintragFX;
import model.EintragFX;
import model.Intervall;

public class BearbeitenDialogController extends Dialog<ButtonType> {
	@FXML ObservableList<BenutzerFX> olBenutzer = FXCollections.observableArrayList();		
		
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
	
	@FXML HBox hbBearbeitenKategorieName;
	@FXML TextField txtBearbeitenKategorieName;
	@FXML Button btnBearbeitenKategorieName;
	
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
		dpBearbeitenDatum.setValue(LocalDate.now());
		txtBearbeitenTitel.setPromptText("Titel eingeben");
		txtBearbeitenBetrag.setPromptText("00.00");
		txtBearbeitenBetrag.setTextFormatter(new TextFormatter<>(converter));
		cbBearbeitenBenutzer.setItems(olBenutzer);
		cbBearbeitenBenutzer.getSelectionModel().select(olBenutzer.stream().filter(b -> b.getName().equals("HAUSHALT")).findFirst().orElse(null));
		cbBearbeitenIntervall.getItems().setAll(Intervall.values());
		cbBearbeitenIntervall.setValue(Intervall.KEINE);
		dpBearbeitenEndeDauereintrag.setDisable(true);

	}
	
	//MainController initialisieren
	private MainController mainController;
	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}
	
	@FXML public void setDatePickerEndeDauereintragOnAble() {
		if(cbBearbeitenIntervall.getSelectionModel().getSelectedItem() == Intervall.KEINE)
			dpBearbeitenEndeDauereintrag.setDisable(true);
		else
			dpBearbeitenEndeDauereintrag.setDisable(false);
	}
	
	@FXML public void btnEintragSpeichern(){
		
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
}
