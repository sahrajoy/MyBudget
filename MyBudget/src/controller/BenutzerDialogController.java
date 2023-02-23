package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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

public class BenutzerDialogController extends Dialog<ButtonType> {
	ArrayList<Benutzer> alBenutzer = new ArrayList<>();
	ObservableList<BenutzerFX> olBenutzer = FXCollections.observableArrayList();
	
	@FXML DialogPane benutzerDialog;
	@FXML VBox vbBenutzer;
	
	@FXML HBox hbNeuerBenutzer;
	@FXML Label lblNeuerBenutzer;
	@FXML TextField tfNeuerBenutzer;
	
	@FXML VBox vbBestehendeBenutzer;
	@FXML Label lblBestehendeBenutzer;
	@FXML TableView<BenutzerFX> tvBenutzer;
	@FXML TableColumn<BenutzerFX, String> benutzerCol;
	
	@FXML Button bBenutzerLoeschen;
	
	//Methoden
	@FXML
	public void initialize() {
		benutzerCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		tvBenutzer.setItems(olBenutzer);
		showBenutzer();
	}
	
	@FXML
	public void insertBenutzerToList() {	
		boolean exists = false;
	    for (BenutzerFX einBenutzerFX : olBenutzer) {
	        if (einBenutzerFX.getName().equals(tfNeuerBenutzer.getText())) {	
	            new Alert(AlertType.ERROR, "Benutzer existiert bereits!").showAndWait();
	            exists = true;
	            break;
	        }
	    }
	    if (!exists) {
	        olBenutzer.add(new BenutzerFX(new Benutzer(tfNeuerBenutzer.getText())));	
	    }
	}
	
	@FXML
	public void loescheBenutzer(){							
		int i = tvBenutzer.getSelectionModel().getSelectedIndex();
		olBenutzer.remove(i);
	}
	
	@FXML
	public void showBenutzer() {
		try {
			alBenutzer = Datenbank.readBenutzer();
			olBenutzer.clear();
			for(Benutzer einBenutzer : alBenutzer)
				olBenutzer.add(new BenutzerFX(einBenutzer));
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.toString());
		}	
	}

	public ObservableList<BenutzerFX> getUpdatetBenutzerList() {
		return olBenutzer;
	}
	
	
}
