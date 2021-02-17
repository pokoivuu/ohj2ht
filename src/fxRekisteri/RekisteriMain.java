package fxRekisteri;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author teemu
 * @version 17.2.2021
 *
 */
public class RekisteriMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("RekisteriGUIView.fxml"));
            final Pane root = ldr.load();
            final RekisteriGUIController kerhoCtrl = (RekisteriGUIController)ldr.getController();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("rekisteri.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("saarekisteri");
            primaryStage.show();
            if ( !kerhoCtrl.avaa() ) Platform.exit();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        launch(args);
    }
}