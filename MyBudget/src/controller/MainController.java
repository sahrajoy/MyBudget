package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
	@FXML ObservableList<DauereintragFX> olDauereintraege = FXCollections.observableArrayList();
	@FXML ObservableList<EintragFX> olEintraege = FXCollections.observableArrayList();
	@FXML ObservableList<KategorieFX> olFavoriten = FXCollections.observableArrayList();
	@FXML ObservableList<KategorieFX> olKategorie = FXCollections.observableArrayList();
	@FXML ObservableList<BenutzerFX> olBenutzer = FXCollections.observableArrayList();		//Liste Benutzernamen hinterlegen
	@FXML ObservableList<String> olSortierung = FXCollections.observableArrayList("Kategorie A-Z", "Kategorie Z-A", "Betrag aufsteigend","Betrag absteigend", "Datum aufsteigend", "Datum absteigend");
	
	//Format Datum
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	//Format Währung
	NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
	//Format Text zeigen in Decimalzahl
	NumberStringConverter converter = new NumberStringConverter("#0.00");
	
	//TabPanes
	@FXML TabPane tpEinnahmenAusgabenStatistik;
	public TabPane getTabPane() {
		return tpEinnahmenAusgabenStatistik;
	}
	
	//Benutzer
	@FXML HBox hbBenutzer;
	@FXML Button bBenutzerAnlegenEntfernen; 			
	@FXML ComboBox<BenutzerFX> cbBenutzer;
	//ActionEvent cbBenutzer
	@FXML public void datenAktualisieren(){	
		if(spUebersicht.isVisible()) {
			getObservableListKategorien();
			tableColumnsUebersicht();
		}
		else if(spFavoriten.isVisible())
			try {
				setTabsFavoriten();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		else if(spDauereintraege.isVisible()) {
			getObservableListDauereintraege();
			tableColumnsDauereintraege();
		}
	}
	
	//TAB AUSGABEN
	@FXML Tab tabAusgaben;
	@FXML AnchorPane apAusgaben;
	@FXML Button btnAusgabenUebersicht;
	@FXML Button btnAusgabenFavoriten;							//Set on disable bis Favoriten hinzugefügt wurden, wenn Favoriten vorhanden und Button geklickt dann setTabsFavoritenAusgaben();
	@FXML Button btnAusgabenDauereintraege;
	
	//TAB EINNAHMEN
	@FXML Tab tabEinnahmen;
	@FXML AnchorPane apEinnahmen;
	@FXML Button btnEinnahmenUebersicht;	
	@FXML Button btnEinnahmenFavoriten;			//Set on disable bis Favoriten hinzugefügt wurden, wenn Favoriten vorhanden und Button geklickt dann setTabsFavoritenAusgaben();
	@FXML Button btnEinnahmenDauereintraege;
	
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
//	@FXML public void setDatePickerDauereintragEndeDauereintragOnAble() {
//		if(cbDauereintraegeIntervall.getSelectionModel().getSelectedItem() == Intervall.KEINE)
//			dpEinnahmenDauereintraegeEndeDauereintrag.setDisable(true);
//		else
//			dpEinnahmenDauereintraegeEndeDauereintrag.setDisable(false);
//	}
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
	@FXML TableColumn<DauereintragFX, LocalDate> dauereintragEndeCol;
	
	//Methoden
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
		setSummeKategorien();
		getObservableListKategorien();
		tableColumnsUebersicht();
		addButtonToUebersichtTable();
		cbUebersichtSortierung.getSelectionModel().select("Kategorie A-Z");
		cbUebersichtSortierung.setItems(olSortierung);
		tvUebersicht.setItems(olKategorie);
		tvUebersicht.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		addButtonToDauereintraegeTable();
		addButtonToUebersichtTable();
	}
	//Inhalt für Übersicht laden
	public void ladeUebersicht() {
		setSummeKategorien();
		tableColumnsUebersicht();
		cbUebersichtSortierung.getSelectionModel().select("Kategorie A-Z");
		cbUebersichtSortierung.setItems(olSortierung);
		tvUebersicht.setItems(olKategorie);
		tvUebersicht.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}
	//Inhalt für Favoriten laden
	public void ladeFavoriten() {
		//Default setzen und Listen laden für Eingabezeile
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
	
	//Inhalt für Dauereintrag laden
	public void ladeDauereintraege() {
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
		cbDauereintraegeSortierung.setItems(olSortierung);
		//TableView
		tvDauereintraege.setItems(olDauereintraege);
		tableColumnsDauereintraege();
		tvDauereintraege.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}
	
	//Inhalt für Statistik laden
	public void ladeStatistik() {
		setCbBenutzerStatistik();
//		bUebersicht.setDefaultButton(true);
	}
	
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
	
	//Einnahmen Einträge/Dauereinträge speichern												
	@FXML public void btnEintragSpeichern(ActionEvent event) {
		if(cbFavoritenIntervall.getValue().toString().equals("keine")) {
			try {
				Datenbank.insertEintrag(new Eintrag(
					dpFavoritenDatum.getValue(),
					txtFavoritenTitel.getText(),
					Double.parseDouble(txtFavoritenBetrag.getText()),
					cbFavoritenBenutzer.getSelectionModel().getSelectedItem().getModellBenutzer(),
					cbFavoritenKategorie.getSelectionModel().getSelectedItem().getModellKategorie()
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
			for(Kategorie eineKategorie : alKategorien) {
				eineKategorie.setSummeEintraege(Datenbank.readKategorieSummeEintraege(eineKategorie.getName()));
				olKategorie.add(new KategorieFX(eineKategorie));									
			}
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.toString());
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
	
	//Öffnen des KategorieDialog durch drücken des bAusgabenPlus Buttons
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
	
	//Öffnen des BearbeitenDialogControllers 
		
	
	//Favoriten Tabs erstellen und tabAusgabenFavoriten hinzufügen
	ArrayList<Tab> alTabsFavoriten = new ArrayList<>();
	int eineFavoritenKategorieId = 0;
	public void setTabsFavoriten() throws SQLException {
		getObservableListFavoritenKategorien();
		for(KategorieFX eineFavoritenKategorieFX : olFavoriten) {
			//Neuen Tab erstellen
			Tab einTabFavoritenKategorie = new Tab(eineFavoritenKategorieFX.getName());
			einTabFavoritenKategorie.setContent(getObservableListEintraegeNachKategorie());
			alTabsFavoriten.add(einTabFavoritenKategorie);
			eineFavoritenKategorieId = eineFavoritenKategorieFX.getKategorieId();
		}
		//Tabs der TabPane hinzufügen
		tpFavoriten.getTabs().addAll(alTabsFavoriten);
	}
	
	//Einträge nach Kategorie aus der Datenbank auslesen und in ObservableList eintragen und TableView erstellen
	TableView<EintragFX> tvFavoriten= new TableView<>(olEintraege);
	@FXML public Node getObservableListEintraegeNachKategorie() {
		try {
			ArrayList<Eintrag> alEintraege = Datenbank.readEintraegeNachKategorie(cbBenutzer.getSelectionModel().getSelectedItem().getBenutzerId(), tpEinnahmenAusgabenStatistik.getSelectionModel().getSelectedItem().getText(), eineFavoritenKategorieId);
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
		dauereintragEndeCol.setCellValueFactory(new PropertyValueFactory<>("dauereintragEnde"));
		dauereintragEndeCol.setCellFactory(column -> new TableCell<DauereintragFX, LocalDate>() {
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
	
	//TableColumns zuordnen TableView Übersicht
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
	
	//TableColumn mit Buttons erstellen und TableView Übersicht zuordnen
	private void addButtonToUebersichtTable() {
        TableColumn<KategorieFX, Void> buttonCol = new TableColumn();
        Callback<TableColumn<KategorieFX, Void>, TableCell<KategorieFX, Void>> cellFactory = new Callback<TableColumn<KategorieFX, Void>, TableCell<KategorieFX, Void>>() {
            @Override
            public TableCell<KategorieFX, Void> call(final TableColumn<KategorieFX, Void> param) {
                final TableCell<KategorieFX, Void> cell = new TableCell<KategorieFX, Void>() {
                    private final Button bFavorite = new Button("Favorite");
                    private final Button bBearbeiten = new Button("Bearbeiten");
                    private final Button bLöschen = new Button("Löschen");
                    HBox hbButtons = new HBox(10, bFavorite, bBearbeiten, bLöschen);
                    {
                    	//ActionEvents für Buttons
                    	bFavorite.setOnAction((ActionEvent event) -> {
                    		KategorieFX kategorieFX  = getTableView().getItems().get(getIndex());
                    		try {
                    			if(kategorieFX.isFavorite())
                    				Datenbank.setKategorieFavorit(kategorieFX.getKategorieId(), true);
                    			else
                    				Datenbank.setKategorieFavorit(kategorieFX.getKategorieId(), false);
                    		} catch (SQLException e) {
                    			e.printStackTrace();
                    		}
                        });
                    	
                    	bBearbeiten.setOnAction((ActionEvent event) -> {
//                            Data data = getTableView().getItems().get(getIndex());
//                            System.out.println("selectedData: " + data);
                        });
                    	bLöschen.setOnAction((ActionEvent event) -> {
//                            Data data = getTableView().getItems().get(getIndex());
//                            System.out.println("selectedData: " + data);
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
        buttonCol.setPrefWidth(456);
        buttonCol.setStyle("-fx-alignment: CENTER;");
        tvUebersicht.getColumns().add(buttonCol);
    }
	
	//TableColumn mit Buttons erstellen und TableView Übersicht zuordnen
	private void addButtonToDauereintraegeTable() {
        TableColumn<DauereintragFX, Void> buttonCol = new TableColumn();
        Callback<TableColumn<DauereintragFX, Void>, TableCell<DauereintragFX, Void>> cellFactory = new Callback<TableColumn<DauereintragFX, Void>, TableCell<DauereintragFX, Void>>() {
            @Override
            public TableCell<DauereintragFX, Void> call(final TableColumn<DauereintragFX, Void> param) {
                final TableCell<DauereintragFX, Void> cell = new TableCell<DauereintragFX, Void>() {
                    private final Button bBearbeiten = new Button("Bearbeiten");
                    private final Button bLöschen = new Button("Löschen");
                    HBox hbButtons = new HBox(10, bBearbeiten, bLöschen);
                    {
                    	//ActionEvents für Buttons//                    	
                    	bBearbeiten.setOnAction((ActionEvent event) -> {
//                            Data data = getTableView().getItems().get(getIndex());
//                            System.out.println("selectedData: " + data);
                        });
                    	bLöschen.setOnAction((ActionEvent event) -> {
//                            Data data = getTableView().getItems().get(getIndex());
//                            System.out.println("selectedData: " + data);
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
	
	//Einträge nach Kategorie auslesen und summieren
	public void setSummeKategorien() {
		double setSummeEintraege = 0;
		try {
			ArrayList<Kategorie> alKategorien = Datenbank.readKategorie();
			for(Kategorie eineKategorie : alKategorien) {
				setSummeEintraege = Datenbank.readKategorieSummeEintraege(eineKategorie.getName());
				eineKategorie.setSummeEintraege(setSummeEintraege);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}				
	}
	
	//Sortierung der Kategorien in der Übersicht
	@FXML public void sortierungEinnahmenUebersicht() {
		
	}
}
