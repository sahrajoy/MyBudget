package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
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
import model.DauereintragFX;
import model.Eintrag;
import model.EintragFX;
import model.Intervall;
import model.Kategorie;
import model.KategorieFX;

public class MainController {
	@FXML ObservableList<DauereintragFX> olDauereintraege = FXCollections.observableArrayList();
	@FXML ObservableList<EintragFX> olEintraege = FXCollections.observableArrayList();
	@FXML ObservableList<KategorieFX> olFavoriten = FXCollections.observableArrayList();
	@FXML ObservableList<KategorieFX> olKategorie = FXCollections.observableArrayList();
	@FXML ObservableList<BenutzerFX> olBenutzer = FXCollections.observableArrayList();		//Liste Benutzernamen hinterlegen
	@FXML ObservableList<String> olSortierung = FXCollections.observableArrayList("Kategorie A-Z", "Kategorie Z-A", "Betrag aufsteigend","Betrag absteigend", "Datum aufsteigend", "Datum absteigend");
	
	//TabPanes
	@FXML TabPane tpEinnahmenAusgabenStatistik;
	public TabPane getTabPane() {
		return tpEinnahmenAusgabenStatistik;
	}
	
	//Benutzer
	@FXML HBox hbBenutzer;
	@FXML Button bBenutzerAnlegenEntfernen; 			
	@FXML ComboBox<BenutzerFX> cbBenutzer;
	@FXML public void datenAktualisieren(){	
		if(spEinnahmenUebersicht.isVisible()) {
			getObservableListKategorien();
			tableColumnsUebersicht();
		}
		else if(spEinnahmenFavoriten.isVisible())
			try {
				setTabsFavoritenAusgaben();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		else if(spEinnahmenDauereintraege.isVisible()) {
			getObservableListDauereintraege();
			tableColumnsDauereintraege();
		}
	}
	
	//TAB EINNAHMEN
	@FXML Tab tabEinnahmen;
	@FXML AnchorPane apEinnahmen;
	@FXML Button bEinnahmenUebersicht;	
	@FXML Button bEinnahmenFavoriten;			//Set on disable bis Favoriten hinzugefügt wurden, wenn Favoriten vorhanden und Button geklickt dann setTabsFavoritenAusgaben();
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
	@FXML AnchorPane apEinnahmenUebersicht;
	Button bFavoritEinnahmen;									//funktion hinterlegen - wenn gedrückt kategorie alFavoritenKategorie hinzufügen 

	//TableView Einnahmen Übersicht
	@FXML TableView<KategorieFX> tvEinnahmenUebersicht;
	@FXML TableColumn<KategorieFX, String> kategorieCol;
	@FXML TableColumn<KategorieFX, Double> summeCol;
	
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
	
	@FXML HBox hbEinnahmenFavoritenEingabezeile;
	@FXML Label lblEinnahmenFavoritenKategorie;
	@FXML ComboBox<KategorieFX> cbEinnahmenFavoritenKategorie;
	@FXML Label lblEinnahmenFavoritenDatum;
	@FXML DatePicker dpEinnahmenFavoritenDatum;
	@FXML Label lblEinnahmenFavoritenTitel;
	@FXML TextField txtEinnahmenFavoritenTitel;
	@FXML Label lblEinnahmenFavoritenBetrag;
	@FXML TextField txtEinnahmenFavoritenBetrag;
	@FXML Label lblEinnahmenFavoritenBenutzer;
	@FXML ComboBox<BenutzerFX> cbEinnahmenFavoritenBenutzer;
	@FXML Label lblEinnahmenFavoritenDauereintrag;
	@FXML ComboBox<Intervall> cbEinnahmenFavoritenIntervall;
	@FXML Label lblEinnahmenFavoritenEndeDauereintrag;
	@FXML DatePicker dpEinnahmenFavoritenEndeDauereintrag;
	@FXML public void setDatePickerEndeDauereintragOnAble() {
		if(cbEinnahmenFavoritenIntervall.getSelectionModel().getSelectedItem() == Intervall.KEINE)
			dpEinnahmenFavoritenEndeDauereintrag.setDisable(true);
		else
			dpEinnahmenFavoritenEndeDauereintrag.setDisable(false);
	}
	@FXML Button bEinnahmenFavoritenSpeichern;					//Prüfungen auf vollständigkeit der eingegebenen Daten hinterlegen und speichern in DB

	//StackPane Einnahmen Dauereinträge
	@FXML StackPane spEinnahmenDauereintraege;
	@FXML HBox hbEinnahmenDauereintraegeText;
	@FXML Label lblEinnahmenDauereintraegeText;
	@FXML Label lblEinnahmenDauereintraegeDatum;
	@FXML HBox hbEinnahmenDauereintraegeSortierung;
	@FXML Label lblEinnahmenDauereintraegeSortierung;
	@FXML ComboBox<String> cbEinnahmenDauereintraegeSortierung;
	@FXML AnchorPane apEinnahmenDauereintraege;
	
	//TableView Einnahmen Dauereinträge
	@FXML TableView<DauereintragFX> tvEinnahmenDauereintraege;
	@FXML TableColumn<DauereintragFX, LocalDate> naechsteFaelligkeitCol;
	@FXML TableColumn<DauereintragFX, String> titelCol;
	@FXML TableColumn<DauereintragFX, Double> betragCol;
	@FXML TableColumn<DauereintragFX, Benutzer> benutzerCol;
	@FXML TableColumn<DauereintragFX, Intervall> intervallCol;
	@FXML TableColumn<DauereintragFX, LocalDate> dauereintragEndeCol;
	
	//TAB AUSGABEN
	@FXML Tab tabAusgaben;
	@FXML AnchorPane apAusgaben;
	@FXML Button bAusgabenUebersicht;
	@FXML
	public void showKategorieAusgaben() {
		
	}
	@FXML Button bAusgabenFavoriten;							//Set on disable bis Favoriten hinzugefügt wurden, wenn Favoriten vorhanden und Button geklickt dann setTabsFavoritenAusgaben();
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
	@FXML AnchorPane apAusgabenUebersicht;
	Button bFavoritAusgaben;										//funktion hinterlegen - wenn gedrückt kategorie alFavoritenKategorie hinzufügen 
	
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
	
	@FXML HBox hbAusgabenFavoritenEingabezeile;
	@FXML Label lblAusgabenFavoritenDatum;
	@FXML DatePicker dpAusgabenFavoritenDatum;
	@FXML Label lblAusgabenFavoritenTitel;
	@FXML TextField txtAusgabenFavoritenTitel;
	@FXML Label lblAusgabenFavoritenBetrag;
	@FXML TextField txtAusgabenFavoritenBetrag;
	@FXML Label lblAusgabenFavoritenBenutzer;
	@FXML ComboBox<BenutzerFX> cbAusgabenFavoritenBenutzer;
	@FXML Label lblAusgabenFavoritenDauereintrag;
	@FXML ComboBox<Intervall> cbAusgabenFavoritenIntervall;
	@FXML Label lblAusgabenFavoritenEndeDauereintrag;
	@FXML DatePicker dpAusgabenFavoritenEndeDauereintrag;
	@FXML Button bAusgabenFavoritenSpeichern;						//Prüfungen auf vollständigkeit der eingegebenen Daten hinterlegen

	//StackPane Ausgaben Dauereintraege
	@FXML StackPane spAusgabenDauereintraege;
	@FXML HBox hbAusgabenDauereintraegeDatum;
	@FXML Label lblAusgabenDauereintraegeText;
	@FXML Label lblAusgabenDauereintraegeDatum;
	@FXML HBox hbAusgabenDauereintraegeSortierung;
	@FXML Label lblAusgabenDauereintraegeSortierung;
	@FXML ComboBox<String> cbAusgabenDauereintraegeSortierung;
	@FXML AnchorPane apAusgabenDauereintraege;
	
	//TAB STATISTIK
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
//		        benutzerFX.doSomething();												// perform action when CheckBox is not selected - Methode hinterlegen
		    } 
		}
	
	//METHODEN ALLGEMEIN

	@FXML
	public void initialize() {
		//Benutzer auslesen und der ObserverList hinzufügen
		getObservableListBenutzer();
		cbBenutzer.setItems(olBenutzer);
		cbBenutzer.getSelectionModel().select(olBenutzer.stream().filter(b -> b.getName().equals("HAUSHALT")).findFirst().orElse(null));
		//Einnahmen Übersicht
		getObservableListKategorien();
		tableColumnsUebersicht();
		cbEinnahmenUebersichtSortierung.getSelectionModel().select("Kategorie A-Z");
		cbEinnahmenUebersichtSortierung.setItems(olSortierung);
		getObservableListKategorien();
		tvEinnahmenUebersicht.setItems(olKategorie);
		tvEinnahmenUebersicht.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		//Einnahmen Favoriten
		dpEinnahmenFavoritenDatum.setValue(LocalDate.now());
		cbEinnahmenFavoritenKategorie.setItems(olKategorie);
		cbEinnahmenFavoritenKategorie.setPromptText("Kategorie wählen");
		cbEinnahmenFavoritenBenutzer.setItems(olBenutzer);
		cbEinnahmenFavoritenBenutzer.getSelectionModel().select(olBenutzer.stream().filter(b -> b.getName().equals("HAUSHALT")).findFirst().orElse(null));
		cbEinnahmenFavoritenIntervall.getItems().setAll(Intervall.values());
		cbEinnahmenFavoritenIntervall.setValue(Intervall.KEINE);
		dpEinnahmenFavoritenEndeDauereintrag.setDisable(true);
		//Einnahmen Dauereintrag
		lblEinnahmenDauereintraegeDatum.setText(LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
		cbEinnahmenDauereintraegeSortierung.getSelectionModel().select("Datum aufsteigend");
		cbEinnahmenDauereintraegeSortierung.setItems(olSortierung);
		getObservableListDauereintraege();
		tvEinnahmenDauereintraege.setItems(olDauereintraege);
		tvEinnahmenDauereintraege.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
//		set Favoriten on disable wenn keine Favoriten vorhanden
		//Ausgaben Übersicht
		cbAusgabenUebersichtSortierung.getSelectionModel().select("Kategorie A-Z");
		cbAusgabenUebersichtSortierung.setItems(olSortierung);
		//Ausgaben Favoriten
		dpAusgabenFavoritenDatum.setValue(LocalDate.now());
		cbAusgabenFavoritenBenutzer.setItems(olBenutzer);
		cbAusgabenFavoritenIntervall.getItems().setAll(Intervall.values());
		dpAusgabenFavoritenEndeDauereintrag.setValue(LocalDate.now());
		//Ausgaben Dauereintrag
		lblAusgabenDauereintraegeDatum.setText(LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
		cbAusgabenDauereintraegeSortierung.getSelectionModel().select("Datum aufsteigend");
		cbAusgabenDauereintraegeSortierung.setItems(olSortierung);
//		set Favoriten on disable wenn keine Favoriten vorhanden
		//Statistik
		setCbBenutzerStatistik();
//		cbHaushalt.setDefaultButton(true)
//		bUebersicht.setDefaultButton(true);
		
	}
	
	//Einnahmen Einträge/Dauereinträge speichern												
	@FXML public void bEinnahmenEintragSpeichern(ActionEvent event) {
		if(cbEinnahmenFavoritenIntervall.getValue().toString().equals("keine")) {
			try {
				Datenbank.insertEintrag(new Eintrag(
					dpEinnahmenFavoritenDatum.getValue(),
					txtEinnahmenFavoritenTitel.getText(),
					Double.parseDouble(txtEinnahmenFavoritenBetrag.getText()),
					cbEinnahmenFavoritenBenutzer.getSelectionModel().getSelectedItem().getModellBenutzer(),
					cbEinnahmenFavoritenKategorie.getSelectionModel().getSelectedItem().getModellKategorie()
						));
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				Datenbank.insertDauereintrag(new Dauereintrag(
					dpEinnahmenFavoritenDatum.getValue(),
					txtEinnahmenFavoritenTitel.getText(),
					Double.parseDouble(txtEinnahmenFavoritenBetrag.getText()),
					cbEinnahmenFavoritenBenutzer.getSelectionModel().getSelectedItem().getModellBenutzer(),
					cbEinnahmenFavoritenIntervall.getSelectionModel().getSelectedItem(),
					dpEinnahmenFavoritenEndeDauereintrag.getValue(),
					cbEinnahmenFavoritenKategorie.getSelectionModel().getSelectedItem().getModellKategorie()
						));
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}
		}
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
	
	//Kategorien aus Datenbank auslesen und ObservableList hinzufügen
	public void getObservableListKategorien() {
		try {
			ArrayList<Kategorie> alKategorien = Datenbank.readKategorie();
			olKategorie.clear();
			for(Kategorie eineKategorie : alKategorien)
				olKategorie.add(new KategorieFX(eineKategorie));					
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.toString());
		}	
	}
	
	//Kategorien aus Datenbank auslesen und ObservableList hinzufügen
	public void getObservableListFavoritenKategorien() {
		try {
			ArrayList<Kategorie> alKategorien = Datenbank.readKategorie();
			olFavoriten.clear();
			for(Kategorie eineKategorie : alKategorien)
				if(eineKategorie.isFavorite())
					olFavoriten.add(new KategorieFX(eineKategorie));					
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.toString());
		}	
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
	
	//Einträge aus der Datenbank auslesen und in ObservableList eintragen
	@FXML public void getObservableListEintraege() {
		try {
			ArrayList<Eintrag> alEintraege = Datenbank.readEintraege(cbBenutzer.getSelectionModel().getSelectedItem().getBenutzerId(), tpEinnahmenAusgabenStatistik.getSelectionModel().getSelectedItem().getText());
			olEintraege.clear();
			for(Eintrag einEintrag : alEintraege)
				olEintraege.add(new EintragFX(einEintrag));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Wechseln der Stacks in Einnahmen per klick auf Button(Übersicht, Favoriten, Dauereintraege)		//Ev. nutzbar für Einnahemn und Ausgaben?
	@FXML public void showStackEinnahmenUebersicht(ActionEvent event) {
		apEinnahmen.getChildren().remove(spEinnahmenUebersicht);
		apEinnahmen.getChildren().add(spEinnahmenUebersicht);
		getObservableListKategorien();
		tableColumnsUebersicht();
		
	}
	@FXML public void showStackEinnahmenFavoriten(ActionEvent event) throws SQLException {
		apEinnahmen.getChildren().remove(spEinnahmenFavoriten);
		apEinnahmen.getChildren().add(spEinnahmenFavoriten);
		setTabsFavoritenAusgaben();
	}
	@FXML public void showStackEinnahmenDauereintraege(ActionEvent event) {
		apEinnahmen.getChildren().remove(spEinnahmenDauereintraege);
		apEinnahmen.getChildren().add(spEinnahmenDauereintraege);
		getObservableListDauereintraege();
		tableColumnsDauereintraege();
	}
	
	//Wechseln der Stacks in Ausgaben per klick auf Button(Übersicht, Favoriten, Dauereintraege)
	@FXML public void showStackAusgabenUebersicht(ActionEvent event) {
		apAusgaben.getChildren().remove(spAusgabenUebersicht);
		apAusgaben.getChildren().add(spAusgabenUebersicht);
		getObservableListEintraege();
	}
	@FXML public void showStackAusgabenFavoriten(ActionEvent event) throws SQLException {
		apAusgaben.getChildren().remove(spAusgabenFavoriten);
		apAusgaben.getChildren().add(spAusgabenFavoriten);
		setTabsFavoritenAusgaben();
	}
	@FXML public void showStackAusgabenDauereintraege(ActionEvent event) {
		apAusgaben.getChildren().remove(spAusgabenDauereintraege);
		apAusgaben.getChildren().add(spAusgabenDauereintraege);
		getObservableListDauereintraege();
	}
	
	//Öffnen des BenutzerDialog
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
				}
			else 
				return;			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Öffnen des KategorieDialog
	@FXML public void kategorieAnlegen(ActionEvent event) throws SQLException {
		try {
			FXMLLoader fxmlLoaderKategorie = new FXMLLoader();
			fxmlLoaderKategorie.setLocation(getClass().getResource("/view/KategorieDialog.fxml"));
			DialogPane kategorieDialog = fxmlLoaderKategorie.load();
			
			//Holen der KategorieDialogController-Instanzen
			KategorieDialogController bdc = fxmlLoaderKategorie.getController();	
			bdc.setMainController(this);
			bdc.showKategorie();
						
			Dialog<ButtonType> dialogKategorie = new Dialog<>();
			dialogKategorie.setDialogPane(kategorieDialog);
			dialogKategorie.setTitle("Kategorien verwalten");

			Optional<ButtonType> clickedButton = dialogKategorie.showAndWait();					
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Favoriten Tabs erstellen und tabAusgabenFavoriten hinzufügen
	ArrayList<Tab> alTabsFavoriten = new ArrayList<>();
	String eineFavoritenKategorieName = null;
	public void setTabsFavoritenAusgaben() throws SQLException {
		ArrayList<Kategorie> alFavoritenKategorie = Datenbank.readFavoriten(cbBenutzer.getSelectionModel().getSelectedItem().getBenutzerId(), tpEinnahmenAusgabenStatistik.getSelectionModel().getSelectedItem().getText());
		for(Kategorie eineFavoritenKategorie : alFavoritenKategorie) {
			//Neuen Tab erstellen
			Tab einTabFavoritenKategorie = new Tab(eineFavoritenKategorie.getName());
			einTabFavoritenKategorie.setContent(getObservableListEintraegeNachKategorie());
			alTabsFavoriten.add(einTabFavoritenKategorie);
			eineFavoritenKategorieName = eineFavoritenKategorie.getName();
			
		}
		//Tabs der TabPane hinzufügen
		if(tpEinnahmenAusgabenStatistik.getSelectionModel().getSelectedItem().getText() == "Einnahmen")
			tpEinnahmenFavoriten.getTabs().addAll(alTabsFavoriten);
		else 
			tpAusgabenFavoriten.getTabs().addAll(alTabsFavoriten);
	}
	
	//Einträge nach Kategorie aus der Datenbank auslesen und in ObservableList eintragen und TableView erstellen
	TableView<EintragFX> tvFavoriten= new TableView<>(olEintraege);
	@FXML public Node getObservableListEintraegeNachKategorie() {
		try {
			ArrayList<Eintrag> alEintraege = Datenbank.readEintraegeNachKategorie(cbBenutzer.getSelectionModel().getSelectedItem().getBenutzerId(), tpEinnahmenAusgabenStatistik.getSelectionModel().getSelectedItem().getText(), eineFavoritenKategorieName);
			olEintraege.clear();
			for(Eintrag einEintrag : alEintraege)
				olEintraege.add(new EintragFX(einEintrag));
		} catch (SQLException e) {
			e.printStackTrace();
		//TableView erstellen
		TableColumn<EintragFX, LocalDate> datumCol = new TableColumn<>();
		datumCol.setCellValueFactory(new PropertyValueFactory<>("datum"));
		datumCol.setPrefWidth(187.34);
		TableColumn<EintragFX, String> titelCol = new TableColumn<>();
		titelCol.setCellValueFactory(new PropertyValueFactory<>("titel"));
		titelCol.setPrefWidth(231.37);
		TableColumn<EintragFX, Double> betragCol = new TableColumn<>();
		betragCol.setCellValueFactory(new PropertyValueFactory<>("betrag"));
		betragCol.setPrefWidth(209.33);
		TableColumn<EintragFX, Benutzer> benutzerCol = new TableColumn<>();
		benutzerCol.setCellValueFactory(new PropertyValueFactory<>("benutzer"));
		benutzerCol.setPrefWidth(212.66);
																									//Buttons hinzufügen
		tvFavoriten.getColumns().addAll(datumCol, titelCol, betragCol, benutzerCol);
		tvFavoriten.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);	
		}
		return tvFavoriten;
	}
	
	//TableColumns zuordnen Dauereintraege
	public void tableColumnsDauereintraege() {
		naechsteFaelligkeitCol.setCellValueFactory(new PropertyValueFactory<>("naechsteFaelligkeit"));
		titelCol.setCellValueFactory(new PropertyValueFactory<>("titel"));
		betragCol.setCellValueFactory(new PropertyValueFactory<>("betrag"));
		benutzerCol.setCellValueFactory(new PropertyValueFactory<>("benutzer"));
		intervallCol.setCellValueFactory(new PropertyValueFactory<>("intervall"));
		dauereintragEndeCol.setCellValueFactory(new PropertyValueFactory<>("dauereintragEnde"));
																								//Butttons hinzufügen
	}
	//TableColumns zuordnen Übersicht
	public void tableColumnsUebersicht() {
		kategorieCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		summeCol.setCellValueFactory(new PropertyValueFactory<>("summeEintraege"));				
																								//Butttons hinzufügen
	}
	
}
