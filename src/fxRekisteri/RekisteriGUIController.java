/**
 * Sample Skeleton for 'RekisteriGUIView.fxml' Controller Class
 */

package fxRekisteri;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


/**
 * @author Teemu Kupiainen, Pauli Koivuniemi
 * @version 17 Feb 2021
 *
 */
public class RekisteriGUIController {
    @FXML private TextField HakuField;
    @FXML private ComboBoxChooser<String> cbPaikka;
    @FXML private Label labelVirhe;
    
    private String rekisterinimi = "2020-2021";
    
    /**
     * @param url ?
     * @param bundle ?
     */
    public void initialize(URL url, ResourceBundle bundle) {
        //      
    }
    
    @FXML private void handleMenuTallenna() {
        tallenna();
    }
    
    
    @FXML private void handleMenuAvaa() {
        avaa();
    }
    
    @FXML private void handleMenuLopetus() {
        tallenna();
        Platform.exit();
    }
    
    
    @FXML private void handleMenuLisaa() {
        Dialogs.showMessageDialog("Ei toimi vielä");
    }
    
    
    @FXML private void handleMenuPoista() {
        Dialogs.showMessageDialog("Ei toimi vielä");
    }
    
    
    @FXML private void handleMenuApua() {
        Dialogs.showMessageDialog("Ei toimi vielä");
    }
    
    
    @FXML private void handleMenuTietoja() {        
        ModalController.showModal(RekisteriGUIController.class.getResource("TietoView.fxml"), "saarekisteri", null, "");
    }
    
    
    @FXML private void handleMuokkaaHuomio() {
        Dialogs.showMessageDialog("Ei toimi vielä");
    }
    
    @FXML private void handleUusiPaiva() {
        Dialogs.showMessageDialog("Ei toimi vielä");
    }
    
    
    
    @FXML private void handleHaku () {
        String haku = cbPaikka.getSelectedText();
        String hakuehto = HakuField.getText();
        if (hakuehto.isEmpty())
            virhe(null);
        else 
            virhe("Ei osata vielä hakea " + haku + ": " + hakuehto);
    }
    
    
    

    private void virhe(String virhe) {
        if (virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
    }
    
        
    public boolean avaa() {
        String nimi = RekisteriNimiController.kysyNimi(null, rekisterimi);
        if (nimi == null) return false;
        lueTiedosto(uusinimi);
        return true;
    }
    
}
