package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BenutzerDialogController extends Dialog<ButtonType> {
	
	@FXML DialogPane benutzerDialog;
	@FXML VBox vbBenutzer;
	@FXML HBox hbNeuerBenutzer;
	@FXML Label lblNeuerBenutzer;
	@FXML TextField tfNeuerBenutzer;
	

}
