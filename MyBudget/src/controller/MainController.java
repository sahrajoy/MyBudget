package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import model.Benutzer;
import model.Dauereintrag;
import model.Eintrag;
import model.Kategorie;

public class MainController {
	
	ObservableList<String> olBenutzer = FXCollections.observableArrayList("Haushalt", "Benutzer1", "Benutzer2");		//Liste Benutzernamen hinterlegen
	ObservableList<String> olSortierung = FXCollections.observableArrayList("Kategorie A-Z", "Kategorie Z-A", "Betrag aufsteigend","Betrag absteigend", "Datum aufsteigend", "Datum absteigend");
	
	@FXML HBox hbBenutzer;
	@FXML Button bBenutzerAnlegen; 		//Funktion Benutzer anlegen
	@FXML ComboBox<String> cbBenutzer; //Benutzer hinterlegen
	
	@FXML Tab tabEinnahmen;
	@FXML Button bEinnahmenUebersicht;	
	@FXML Button bEinnahmenFavoriten;
	@FXML Button bEinnahmenDauereintraege;
	
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
	@FXML ComboBox<Dauereintrag> cbEinnahmenFavoritenDauereintrag;
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
	
	//StackPane Einnahmen Dauereintraege
	@FXML StackPane spEinnahmenDauereintraege;
	@FXML Label hbEinnahmenDauereintraegeText;
	@FXML Label lblEinnahmenDauereintraegeDatum;			
	@FXML HBox hbEinnahmenDauereintraegeSortierung;
	@FXML Label lblEinnahmenDauereintraegeSortierung;
	@FXML ComboBox<String> cbEinnahmenDauereintraegeSortierung;
	
	
	@FXML Tab tabAusgaben;
	
	@FXML Tab tabStatistik;
	
	//Methoden
	@FXML
	public void initialize() {
		cbBenutzer.getSelectionModel().select("Haushalt");
		cbBenutzer.setItems(olBenutzer);
		cbEinnahmenUebersichtSortierung.getSelectionModel().select("Kategorie A-Z");
		cbEinnahmenUebersichtSortierung.setItems(olSortierung);
		cbEinnahmenDauereintraegeSortierung.getSelectionModel().select("Datum aufsteigend");
		cbEinnahmenDauereintraegeSortierung.setItems(olSortierung);
		lblEinnahmenDauereintraegeDatum.setText(LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
		dpEinnahmenFavoritenEndeDauereintrag.setValue(LocalDate.now());
//		bUebersicht.setDefaultButton(true);
	}
	


}
