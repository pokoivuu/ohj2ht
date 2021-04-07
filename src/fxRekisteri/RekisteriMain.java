package fxRekisteri;

import javafx.application.Application;
import javafx.stage.Stage;
import rekisteri.Rekisteri;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author Teemu Kupiainen ja Pauli Koivuniemi
 * @version 17.2.2021
 *
 * Pääohjelma Säärekisteri-ohjelman käynnistämiseksi
 *
 */
public class RekisteriMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("RekisteriGUIView.fxml"));
            final Pane root = (Pane)ldr.load();
            final RekisteriGUIController rekisteriCtrl = (RekisteriGUIController)ldr.getController();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("rekisteri.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("saarekisteri");
            
            primaryStage.setOnCloseRequest((event) ->{
                if (!rekisteriCtrl.saakoSulkea() ) event.consume();
              });
            
            Rekisteri rekisteri = new Rekisteri();
            rekisteriCtrl.setRekisteri(rekisteri);
            
            primaryStage.show();
            Application.Parameters params = getParameters(); 
            if ( params.getRaw().size() > 0 ) 
                rekisteriCtrl.lueTiedosto(params.getRaw().get(0));  
            else
                if ( !rekisteriCtrl.avaa() ) Platform.exit();



        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Käynnistetään käyttöliittymä
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        launch(args);
    }
}