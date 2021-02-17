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
    
    private String rekisterinimi = "2020-2021";
    
    /**
     * @param url ?
     * @param bundle ?
     */
    public void initialize(URL url, ResourceBundle bundle) {
        //      
    }
    

    @FXML private void handleHaku () {
        String haku = cbPaikka.getSelectedText();
        
    }
    
    
    @FXML private void handleMenuTietoja() {        
        ModalController.showModal(RekisteriGUIController.class.getResource("TietoView.fxml"), "saarekisteri", null, "");
    }

}
