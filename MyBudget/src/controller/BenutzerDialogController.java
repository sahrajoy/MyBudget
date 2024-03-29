package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Benutzer;
import model.BenutzerFX;
import model.Datenbank;

public class BenutzerDialogController extends Dialog<ButtonType> {
	@FXML ArrayList<Benutzer> alBenutzer = new ArrayList<>();
	@FXML ObservableList<BenutzerFX> olBenutzer = FXCollections.observableArrayList();
	
	@FXML DialogPane benutzerDialog;
	@FXML VBox vbBenutzer;
	
	@FXML HBox hbNeuerBenutzer;
	@FXML Label lblNeuerBenutzer;
	@FXML TextField tfNeuerBenutzer; //ActionEvent ist insertBenutzerToList()
	
	@FXML VBox vbBestehendeBenutzer;
	@FXML Label lblBestehendeBenutzer;
	@FXML TableView<BenutzerFX> tvBenutzer;
	@FXML TableColumn<BenutzerFX, String> benutzerCol;
	
	@FXML Button btnBenutzerLoeschen; //ActionEvent ist loescheBenutzer()
	@FXML Button btnBenutzerBearbeiten;	//ActionEvent ist bearbeiteBenutzer()
	
	//Methoden
	@FXML public void initialize() {
		benutzerCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		tvBenutzer.setItems(olBenutzer);
		showBenutzer();
	}
	
	//Benutzer bearbeiten über Button btnBenutzerBearbeiten
	@FXML public void bearbeiteBenutzer(){
		TextInputDialog bearbeiteNameBenutzer = new TextInputDialog(tvBenutzer.getSelectionModel().getSelectedItem().getModellBenutzer().getName());
		bearbeiteNameBenutzer.setContentText("Neuen Benutzernamen eingeben");
        Optional<String> result = bearbeiteNameBenutzer.showAndWait();
        result.ifPresent(name -> 
        	{
				try {
					Datenbank.updateBenutzer(tvBenutzer.getSelectionModel().getSelectedItem().getModellBenutzer(), bearbeiteNameBenutzer.getEditor().getText());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
        );
        showBenutzer();      
	}
	
	//Neuen Benutzer anlegen über TextField tfNeuerBenutzer
	@FXML public void insertBenutzerToList() {	
		boolean exists = false;
	    for (BenutzerFX einBenutzerFX : olBenutzer) {
	        if (einBenutzerFX.getName().equalsIgnoreCase(tfNeuerBenutzer.getText())) {	
	            new Alert(AlertType.ERROR, "Benutzer existiert bereits!").showAndWait();
	            exists = true;
	            break;
	        }
	    }
	    if (!exists) {
	        olBenutzer.add(new BenutzerFX(new Benutzer(tfNeuerBenutzer.getText())));
	        try {
				Datenbank.insertBenutzer(new Benutzer(tfNeuerBenutzer.getText()));
				showBenutzer();
	        } catch (SQLException e) {
				e.printStackTrace();
			}
	    }
	}
	
	//Benutzer löschen über Button btnBenutzerLoeschen
	@FXML public void loescheBenutzer(){
	    Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
	    confirmationDialog.setTitle("Benutzer löschen");
	    confirmationDialog.setContentText("Soll der Benutzer wirklich gelöscht werden, Änderungen können nicht rückgängig gemacht werden!");
	    Optional<ButtonType> clickedButton = confirmationDialog.showAndWait();

	    if (clickedButton.isPresent() && clickedButton.get() == ButtonType.OK) {
	        BenutzerFX selectedBenutzer = tvBenutzer.getSelectionModel().getSelectedItem();
	        if (selectedBenutzer != null) {
	            try {
	                Datenbank.deleteBenutzer(selectedBenutzer.getBenutzerId());
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	            olBenutzer.remove(selectedBenutzer);
	        }
	    }
	}
	
	//Benutzer auslesen und der ObserverList hinzufügen
	public void showBenutzer() {
		try {
			alBenutzer = Datenbank.readBenutzer();
			olBenutzer.clear();
			for(Benutzer einBenutzer : alBenutzer)
				if(!einBenutzer.getName().equals("HAUSHALT"))
					olBenutzer.add(new BenutzerFX(einBenutzer));
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.toString());
		}	
	}
	
}
