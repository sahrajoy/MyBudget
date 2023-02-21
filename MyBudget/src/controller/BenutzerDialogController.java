package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Benutzer;
import model.BenutzerFX;
import model.Datenbank;
import model.Kategorie;

public class BenutzerDialogController extends Dialog<ButtonType> {
	ObservableList<String> olBenutzer = FXCollections.observableArrayList();
	
	@FXML DialogPane benutzerDialog;
	@FXML VBox vbBenutzer;
	
	@FXML HBox hbNeuerBenutzer;
	@FXML Label lblNeuerBenutzer;
	@FXML TextField tfNeuerBenutzer;
	
	@FXML VBox vbBestehendeBenutzer;
	@FXML Label lblBestehendeBenutzer;
	@FXML TableView<Benutzer> tvBenutzer;
	@FXML TableColumn<Benutzer, String> benutzerCol;
	
	@FXML
	public void initialize() {
		benutzerCol.setCellValueFactory(new PropertyValueFactory<>(""));
		showBenutzer();
	}
	
	public void showBenutzer() {
		try {
			ArrayList<Benutzer> alBenutzer = Datenbank.readBenutzer();
			olBenutzer.clear();
			for(Benutzer einBenutzer : alBenutzer)
				olBenutzer.add(einBenutzer.toString());
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.toString());
		}	
	}
}
