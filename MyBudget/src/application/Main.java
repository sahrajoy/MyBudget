package application;
	
import java.io.IOException;
import java.sql.SQLException;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.Datenbank;
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		FXMLLoader fxmlLoaderMainController = new FXMLLoader();
		fxmlLoaderMainController.setLocation(getClass().getResource("/view/MyBudget.fxml"));
		Parent root = fxmlLoaderMainController.load();
		
		MainController mc = (MainController)fxmlLoaderMainController.getController();
		
		try {
			mc.dauereintraegeAusfuehren();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("MyBudget");
		primaryStage.show();
	}
	

	public static void main(String[] args) {
		launch(args);
	}
}
