package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import model.Benutzer;
import model.Kategorie;

public class MainController {

	@FXML ComboBox<Benutzer> cbBenutzer;
	@FXML ComboBox<String> cbSortierung;
	@FXML Pane hbAnsicht;			//Border hinzufügen
	@FXML Button bUebersicht;		//zu anderer 
	@FXML Button bFavoriten;
	@FXML Button bDauereintraege;
	@FXML Button bMonat;
	@FXML Button bJahr;
	@FXML Button bPfeilZurueck;
	@FXML Label lblZeitraum;
	@FXML Button bPfeilVorwaerts;
	@FXML Button bPlus;
	
	@FXML TableView<Kategorie> tvKategorien;		
	@FXML TableColumn<Kategorie, String> kategorieCol;
	@FXML TableColumn<Kategorie, String> summeCol;
	@FXML TableColumn<Kategorie, String> buttonsCol;
	
	
	@FXML
	public void initializeBenutzer() {
//		cbBenutzer.getItems().removeAll(cbBenutzer.getItems());
//		cbBenutzer.getItems().addAll(FXCollections.observableArrayList("Haushalt", "Benutzer1", "Benutzer2"));
//		cbBenutzer.getSelectionModel().select("Haushalt");
	}
	
	@FXML
	public void initializeSortierung() {
		cbSortierung.getItems().removeAll(cbSortierung.getItems());
		cbSortierung.getItems().addAll(FXCollections.observableArrayList("Kategorie A-Z", "Kategorie Z-A", "Betrag aufsteigend","Betrag absteigend"));
		cbSortierung.getSelectionModel().select("Kategorie A-Z");
	}

}
