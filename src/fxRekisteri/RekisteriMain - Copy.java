package fxRekisteri;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


/**
 * @author Teemu Kupiainen, Pauli Koivuniemi
 * @version 21 Jan 2021
 *
 */
public class RekisteriMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("RekisteriGUIView.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("rekisteri.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		launch(args);
	}
}

