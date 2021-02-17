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
public class RekisteriGUIController implements Initializable {
    @FXML private TextField hakuField;
    @FXML private ComboBoxChooser<String> cbPaikka;
    @FXML private Label labelVirhe;
    
    private String rekisterinimi = "2020-2021";
    
 
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        //      
    }
    
    @FXML private void handleMenuTallenna() {
        tallenna();
    }
    
    
    @FXML private void handleMenuAvaa() {
        avaa();
    }
    
    @FXML private void handleMenuLopeta() {
        tallenna();
        Platform.exit();
    }
    
    
    @FXML private void handleMenuLisaa() {
        Dialogs.showMessageDialog("Ei toimi vielä");
    }
    
    
    @FXML private void handleMenuMuokkaa() {
        ModalController.showModal(RekisteriGUIController.class.getResource("MuokkausView.fxml"), "saarekisteri", null, "");
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
        String hakuehto = hakuField.getText();
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
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
    
        
    public boolean avaa() {
        String nimi = RekisteriNimiController.kysyNimi(null, rekisterinimi);
        if (nimi == null) return false;
        lueTiedosto(nimi);
        return true;
    }
    
    
    private void setTitle(String title) {
        ModalController.getStage(hakuField).setTitle(title);
    }
    
    
    protected void lueTiedosto(String nimi) {
        rekisterinimi = nimi;
        setTitle("Rekisteri - " + rekisterinimi);
        String virhe = "Ei toimi vielä";
            Dialogs.showMessageDialog(virhe);
    }
    
    
    private void tallenna() {
        Dialogs.showMessageDialog("Tallennus ei toimi vielä");
    }
    
    
    public boolean saakoSulkea() {
        tallenna();
        return true;
    }
    

    @FXML private void handleAvaa() {
        avaa();
    }
    

}
