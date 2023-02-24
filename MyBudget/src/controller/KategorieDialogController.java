package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import model.Datenbank;
import model.Kategorie;
import model.KategorieFX;

public class KategorieDialogController extends Dialog<ButtonType> {
	ArrayList<Kategorie> alKategorie = new ArrayList<>();
	ObservableList<KategorieFX> olKategorie = FXCollections.observableArrayList();
	
	@FXML DialogPane kategorieDialog;
	@FXML VBox vbKategorie;
	
	@FXML HBox hbNeueKategorie;
	@FXML Label lblNeueKategorie;
	@FXML TextField tfNeueKategorie;
	
	@FXML VBox vbBestehendeKategorien;
	@FXML Label lblBestehendeKategorien;
	@FXML TableView<KategorieFX> tvKategorie;
	@FXML TableColumn<KategorieFX, String> kategorieCol;
	
	//Methoden
	@FXML public void initialize() {
		kategorieCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		tvKategorie.setItems(olKategorie);
		showKategorie();
	}
	
	@FXML public void insertKategorieToList() {	
		boolean exists = false;
	    for (KategorieFX einKategorieFX : olKategorie) {
	        if (einKategorieFX.getName().equalsIgnoreCase(tfNeueKategorie.getText())) {	
	            new Alert(AlertType.ERROR, "Kategorie existiert bereits!").showAndWait();
	            exists = true;
	            break;
	        }
	    }
	    if (!exists) {
	        olKategorie.add(new KategorieFX(new Kategorie(tfNeueKategorie.getText(), false)));	
	        try {
				Datenbank.insertKategorie(new Kategorie(tfNeueKategorie.getText(), false));
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
	}
	
	@FXML public void showKategorie() {
		try {
		alKategorie = Datenbank.readKategorie();
		olKategorie.clear();
		for(Kategorie einKategorie : alKategorie)
			olKategorie.add(new KategorieFX(einKategorie));	
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.toString());
		}
	}

	public ObservableList<KategorieFX> getUpdatetKategorieList() {
		return olKategorie;
	}

}
