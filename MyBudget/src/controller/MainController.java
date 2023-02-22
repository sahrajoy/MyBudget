package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Benutzer;
import model.BenutzerFX;
import model.Datenbank;
import model.Dauereintrag;
import model.Eintrag;
import model.Intervall;
import model.Kategorie;

public class MainController {
	
	ObservableList<Benutzer> olBenutzer = FXCollections.observableArrayList();		//Liste Benutzernamen hinterlegen
	ObservableList<String> olSortierung = FXCollections.observableArrayList("Kategorie A-Z", "Kategorie Z-A", "Betrag aufsteigend","Betrag absteigend", "Datum aufsteigend", "Datum absteigend");
	ObservableList<Eintrag> olEintraege = FXCollections.observableArrayList();
	
	@FXML HBox hbBenutzer;
	@FXML Button bBenutzerAnlegen; 		
	@FXML 
	public void benutzerAnlegen(ActionEvent event) throws SQLException{
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/view/BenutzerDialog.fxml"));
			DialogPane benutzerDialog = fxmlLoader.load();
			
			Dialog<ButtonType> dialog = new Dialog<>();
			dialog.setDialogPane(benutzerDialog);
			dialog.setTitle("Benutzer verwalten");
			
			((Button) dialog.getDialogPane().lookupButton(ButtonType.OK)).setText("Speichern");
			((Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Abbrechen");

			Optional<ButtonType> clickedButton = dialog.showAndWait();
			if(clickedButton.get() == ButtonType.OK && !Datenbank.benutzerExist("Testperson2")) {		//tfNeuerBenutzer.getText
				Datenbank.insertBenutzer(new Benutzer("Testperson2"));
				showBenutzer();
			}
			else if(clickedButton.get() == ButtonType.OK && Datenbank.benutzerExist("Testperson2")) {			//tfNeuerBenutzer.getText
				new Alert(AlertType.ERROR, "Benutzer existiert bereits").showAndWait();
			}
			else if(clickedButton.get() == ButtonType.CANCEL) {
				return;
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	@FXML ComboBox<Benutzer> cbBenutzer; 
	public void showBenutzer() {
		try {
			ArrayList<Benutzer> alBenutzer = Datenbank.readBenutzer();
			olBenutzer.clear();
			for(Benutzer einBenutzer : alBenutzer)
				olBenutzer.add(einBenutzer);
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.toString());
		}	
	}
	
	//Einahmen
	@FXML Tab tabEinnahmen;
	@FXML AnchorPane apEinnahmen;
	@FXML Button bEinnahmenUebersicht;	
	@FXML
	public void showKategorieEinnahmen() {
//		tvEinnahmenUebersichtKategorien
		
	}
	@FXML Button bEinnahmenFavoriten;
	@FXML
	public void showEintraegeEinnahmen() {
//		tvEinnahmenFavoriten
		
	}
	@FXML Button bEinnahmenDauereintraege;
	@FXML
	public void showDauereintraegeEinnahmen() {
//		tvEinnahmenDauereintraege
		
	}
	
	//StackPane Einnahmen Übersicht
	@FXML StackPane spEinnahmenUebersicht;
	@FXML HBox hbEinnahmenUebersichtButtonsZeitraum;
	@FXML Button bEinnahmenUebersichtMonat;
	@FXML Button bEinnahmenUebersichtJahr;
	@FXML HBox hbEinnahmenUebersichtZeitraum;
	@FXML Button bEinnahmenUebersichtPfeilZurueck;
	@FXML Label lblEinnahmenUebersichtZeitraum;
	@FXML Button bEinnahmenUebersichtPfeilVorwaerts;
	@FXML GridPane gpEinnahmenUebersichtPlusUndSortieren;
	@FXML Button bEinnahmenPlus;
	@FXML Label lblEinnahmenUebersichtSortierung;
	@FXML ComboBox<String> cbEinnahmenUebersichtSortierung;
	@FXML TableView<Kategorie> tvEinnahmenUebersichtKategorien;	
	@FXML TableColumn<Kategorie, String> einnahmenKategorieCol;
	@FXML TableColumn<Kategorie, Double> einnahmenSummeCol;
	@FXML TableColumn<Kategorie, String> einnahmenUebersichtButtonsCol;		//Variable für Buttons?
	
	//StackPane Einnahmen Favoriten
	@FXML StackPane spEinnahmenFavoriten;		
	@FXML HBox hbEinnahmenFavoritenButtonsZeitraum;
	@FXML Button bEinnahmenFavoritenTag;
	@FXML Button bEinnahmenFavoritenWoche;
	@FXML Button bEinnahmenFavoritenMonat;
	@FXML Button bEinnahmenFavoritenJahr;
	@FXML HBox hbEinnahmenFavoritenZeitraum;
	@FXML Button bEinnahmenFavoritenPfeilZurueck;
	@FXML Label lblEinnahmenFavoritenZeitraum;
	@FXML Button bEinnahmenFavoritenPfeilVorwaerts;
	@FXML TabPane tpEinnahmenFavoriten;
	@FXML Tab tabEinnahmenFavoriten;		//Favoriten hinterlegen 
	
	@FXML HBox hbEinnahmenFavoritenEingabezeile;
	@FXML Label lblEinnahmenFavoritenDatum;
	@FXML DatePicker dpEinnahmenFavoritenDatum;
	@FXML Label lblEinnahmenFavoritenTitel;
	@FXML TextField txtEinnahmenFavoritenTitel;
	@FXML Label lblEinnahmenFavoritenBetrag;
	@FXML TextField txtEinnahmenFavoritenBetrag;
	@FXML Label lblEinnahmenFavoritenBenutzer;
	@FXML ComboBox<Benutzer> cbEinnahmenFavoritenBenutzer;
	@FXML Label lblEinnahmenFavoritenDauereintrag;
	@FXML ComboBox<Intervall> cbEinnahmenFavoritenIntervall;
	@FXML Label lblEinnahmenFavoritenEndeDauereintrag;
	@FXML DatePicker dpEinnahmenFavoritenEndeDauereintrag;
	@FXML Button bEinnahmenFavoritenSpeichern;			//Prüfungen auf vollständigkeit der eingegebenen Daten hinterlegen
	
	@FXML TableView<Eintrag> tvEinnahmenFavoriten;
	@FXML TableColumn<Eintrag, LocalDate> einnahmenDatumCol;
	@FXML TableColumn<Eintrag, String> einnahmenTitelCol;
	@FXML TableColumn<Eintrag, Double> einnahmenBetragCol;
	@FXML TableColumn<Eintrag, Benutzer> einnahmenBenutzerCol;
	@FXML TableColumn<Eintrag, String> einnahmenDauereintragCol;
	@FXML TableColumn<Eintrag, LocalDate> einnahmenDauereintragEndedatumCol;
	@FXML TableColumn<Eintrag, String> einnahmenFavoritenButtonsCol; 		//Variable für Buttons?

	//StackPane Einnahmen Dauereinträge
	@FXML StackPane spEinnahmenDauereintraege;
	@FXML HBox hbEinnahmenDauereintraegeText;
	@FXML Label lblEinnahmenDauereintraegeText;
	@FXML Label lblEinnahmenDauereintraegeDatum;
	@FXML HBox hbEinnahmenDauereintraegeSortierung;
	@FXML Label lblEinnahmenDauereintraegeSortierung;
	@FXML ComboBox<String> cbEinnahmenDauereintraegeSortierung;
	@FXML TableView<Dauereintrag> tvEinnahmenDauereintraege;
	@FXML TableColumn<Dauereintrag, LocalDate> einnahmenDauereintraegeDatumCol;
	@FXML TableColumn<Dauereintrag, String> einnahmenDauereintraegeTitelCol;
	@FXML TableColumn<Dauereintrag, Double> einnahmenDauereintraegeBetragCol;
	@FXML TableColumn<Dauereintrag, Benutzer> einnahmenDauereintraegeBenutzerCol;
	@FXML TableColumn<Dauereintrag, Intervall> einnahmenDauereintraegeIntervallCol;
	@FXML TableColumn<Dauereintrag, LocalDate> einnahmenDauereintraegeEndeCol;
	@FXML TableColumn<Dauereintrag, String> einnahmenDauereintraegeButtonsCol;		//Variable für Buttons?
		
	@FXML 
	public void showStackEinnahmenUebersicht(ActionEvent event) {
		apEinnahmen.getChildren().remove(spEinnahmenUebersicht);
		apEinnahmen.getChildren().add(spEinnahmenUebersicht);
	}
	@FXML 
	public void showStackEinnahmenFavoriten(ActionEvent event) {
		apEinnahmen.getChildren().remove(spEinnahmenFavoriten);
		apEinnahmen.getChildren().add(spEinnahmenFavoriten);
	}
	@FXML 
	public void showStackEinnahmenDauereintraege(ActionEvent event) {
		apEinnahmen.getChildren().remove(spEinnahmenDauereintraege);
		apEinnahmen.getChildren().add(spEinnahmenDauereintraege);
	}
	
	//Ausgaben
	@FXML Tab tabAusgaben;
	@FXML AnchorPane apAusgaben;
	@FXML Button bAusgabenUebersicht;
	@FXML
	public void showKategorieAusgaben() {
//		tvAusgabenUebersichtKategorien
		
	}
	@FXML Button bAusgabenFavoriten;
	@FXML
	public void showEintraegeAusgaben() {
//		tvAusgabenFavoriten
		
	}
	@FXML Button bAusgabenDauereintraege;
	@FXML
	public void showDauereintraegeAusgaben() {
//		tvAusgabenDauereintraege
		
	}

	//StackPane Ausgaben Übersicht
	@FXML StackPane spAusgabenUebersicht;
	@FXML HBox hbAusgabenUebersichtButtonsZeitraum;
	@FXML Button bAusgabenUebersichtMonat;
	@FXML Button bAusgabenUebersichtJahr;
	@FXML HBox hbAusgabenUebersichtZeitraum;
	@FXML Button bAusgabenUebersichtPfeilZurueck;
	@FXML Label lblAusgabenUebersichtZeitraum;
	@FXML Button bAusgabenUebersichtPfeilVorwaerts;
	@FXML GridPane gpAusgabenUebersichtPlusUndSortieren;
	@FXML Button bAusgabenPlus;
	@FXML Label lblAusgabenUebersichtSortierung;
	@FXML ComboBox<String> cbAusgabenUebersichtSortierung;
	@FXML TableView<Kategorie> tvAusgabenUebersichtKategorien;
	@FXML TableColumn<Kategorie, String> ausgabenKategorieCol;
	@FXML TableColumn<Kategorie, Double> ausgabenSummeCol;
	@FXML TableColumn<Kategorie, String> ausgabenUebersichtButtonsCol;		//Variable für Buttons?
	
	//StackPane Ausgaben Favoriten
	@FXML StackPane spAusgabenFavoriten;
	@FXML HBox hbAusgabenFavoritenButtonsZeitraum;
	@FXML Button bAusgabenFavoritenTag;
	@FXML Button bAusgabenFavoritenWoche;
	@FXML Button bAusgabenFavoritenMonat;
	@FXML Button bAusgabenFavoritenJahr;
	@FXML HBox hbAusgabenFavoritenZeitraum;
	@FXML Button bAusgabenFavoritenPfeilZurueck;
	@FXML Label lblAusgabenFavoritenZeitraum;
	@FXML Button bAusgabenFavoritenPfeilVorwaerts;
	@FXML TabPane tpAusgabenFavoriten;
	@FXML Tab tabAusgabenFavoriten;		//Favoriten hinterlegen
	
	@FXML HBox hbAusgabenFavoritenEingabezeile;
	@FXML Label lblAusgabenFavoritenDatum;
	@FXML DatePicker dpAusgabenFavoritenDatum;
	@FXML Label lblAusgabenFavoritenTitel;
	@FXML TextField txtAusgabenFavoritenTitel;
	@FXML Label lblAusgabenFavoritenBetrag;
	@FXML TextField txtAusgabenFavoritenBetrag;
	@FXML Label lblAusgabenFavoritenBenutzer;
	@FXML ComboBox<Benutzer> cbAusgabenFavoritenBenutzer;
	@FXML Label lblAusgabenFavoritenDauereintrag;
	@FXML ComboBox<Intervall> cbAusgabenFavoritenIntervall;
	@FXML Label lblAusgabenFavoritenEndeDauereintrag;
	@FXML DatePicker dpAusgabenFavoritenEndeDauereintrag;
	@FXML Button bAusgabenFavoritenSpeichern;			//Prüfungen auf vollständigkeit der eingegebenen Daten hinterlegen
	
	@FXML TableView<Eintrag> tvAusgabenFavoriten;
	@FXML TableColumn<Eintrag, LocalDate> ausgabenDatumCol;
	@FXML TableColumn<Eintrag, String> ausgabenTitelCol;
	@FXML TableColumn<Eintrag, Double> ausgabenBetragCol;
	@FXML TableColumn<Eintrag, Benutzer> ausgabenBenutzerCol;
	@FXML TableColumn<Eintrag, String> ausgabenDauereintragCol;
	@FXML TableColumn<Eintrag, LocalDate> ausgabenDauereintragEndedatumCol;
	@FXML TableColumn<Eintrag, String> ausgabenFavoritenButtonsCol; 		//Variable für Buttons?
	
	//StackPane Ausgaben Dauereintraege
	@FXML StackPane spAusgabenDauereintraege;
	@FXML HBox hbAusgabenDauereintraegeDatum;
	@FXML Label lblAusgabenDauereintraegeText;
	@FXML Label lblAusgabenDauereintraegeDatum;
	@FXML HBox hbAusgabenDauereintraegeSortierung;
	@FXML Label lblAusgabenDauereintraegeSortierung;
	@FXML ComboBox<String> cbAusgabenDauereintraegeSortierung;
	@FXML TableView<Dauereintrag> tvAusgabenDauereintraege;
	@FXML TableColumn<Dauereintrag, LocalDate> ausgabenDauereintraegeDatumCol;
	@FXML TableColumn<Dauereintrag, String> ausgabenDauereintraegeTitelCol;
	@FXML TableColumn<Dauereintrag, Double> ausgabenDauereintraegeBetragCol;
	@FXML TableColumn<Dauereintrag, Benutzer> ausgabenDauereintraegeBenutzerCol;
	@FXML TableColumn<Dauereintrag, Intervall> ausgabenDauereintraegeIntervallCol;
	@FXML TableColumn<Dauereintrag, LocalDate> ausgabenDauereintraegeEndeCol;
	@FXML TableColumn<Dauereintrag, String> ausgabenDauereintraegeButtonsCol;		//Variable für Buttons?
	
	@FXML 
	public void showStackAusgabenUebersicht(ActionEvent event) {
		apAusgaben.getChildren().remove(spAusgabenUebersicht);
		apAusgaben.getChildren().add(spAusgabenUebersicht);
	}
	@FXML 
	public void showStackAusgabenFavoriten(ActionEvent event) {
		apAusgaben.getChildren().remove(spAusgabenFavoriten);
		apAusgaben.getChildren().add(spAusgabenFavoriten);
	}
	@FXML 
	public void showStackAusgabenDauereintraege(ActionEvent event) {
		apAusgaben.getChildren().remove(spAusgabenDauereintraege);
		apAusgaben.getChildren().add(spAusgabenDauereintraege);
	}
	
	//Statistik
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
	@FXML CheckBox cbStatistikBenutzer;	
	@FXML Pane pStatistik;
	
	//Methoden
	@FXML
	public void initialize() {
		
		// Benutzer laden und der Observer List hinzufügen
		 showBenutzer();
		
		cbBenutzer.setItems(olBenutzer);
//		cbBenutzer.getSelectionModel().select("Haushalt");			//Haushalt Objekt hinterlegen
		//Einnahmen
		cbEinnahmenUebersichtSortierung.getSelectionModel().select("Kategorie A-Z");
		cbEinnahmenUebersichtSortierung.setItems(olSortierung);
		
		dpEinnahmenFavoritenDatum.setValue(LocalDate.now());
		cbEinnahmenFavoritenBenutzer.setItems(olBenutzer);
		cbEinnahmenFavoritenIntervall.getItems().setAll(Intervall.values());
		dpEinnahmenFavoritenEndeDauereintrag.setValue(LocalDate.now());
		
		lblEinnahmenDauereintraegeDatum.setText(LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
		cbEinnahmenDauereintraegeSortierung.getSelectionModel().select("Datum aufsteigend");
		cbEinnahmenDauereintraegeSortierung.setItems(olSortierung);
		//Ausgaben
		cbAusgabenUebersichtSortierung.getSelectionModel().select("Kategorie A-Z");
		cbAusgabenUebersichtSortierung.setItems(olSortierung);
		
		dpAusgabenFavoritenDatum.setValue(LocalDate.now());
		cbAusgabenFavoritenBenutzer.setItems(olBenutzer);
		cbAusgabenFavoritenIntervall.getItems().setAll(Intervall.values());
		dpAusgabenFavoritenEndeDauereintrag.setValue(LocalDate.now());
		
		lblAusgabenDauereintraegeDatum.setText(LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
		cbAusgabenDauereintraegeSortierung.getSelectionModel().select("Datum aufsteigend");
		cbAusgabenDauereintraegeSortierung.setItems(olSortierung);
		//Statistik
//		cbHaushalt.setDefaultButton(true)
//		bUebersicht.setDefaultButton(true);
	}
	
	


}
