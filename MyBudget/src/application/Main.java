package application;
	
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.Datenbank;
import model.Dauereintrag;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		try {
			Datenbank.createBenutzerTable();
			Datenbank.createKategorieTable();
			Datenbank.createEintragTable();
			Datenbank.createDauereintragTable();
			Datenbank.dauereintraegeAusfuehren();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Parent root = FXMLLoader.load(getClass().getResource("/view/MyBudget.fxml"));
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("MyBudget");
		primaryStage.show();
	}
	

	public static void main(String[] args) {
		launch(args);
	}
}
