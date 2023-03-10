package fxRekisteri;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Dialogi rekisterin nimeä varten
 * 
 * @author Pauli Koivuniemi ja Teemu Kupiainen
 * @version 22.4.2021
 *
 */
public class RekisteriNimiController implements ModalControllerInterface<String> {

    @FXML private TextField textVastaus;
    private String vastaus = null;
    
    @FXML private void handleOK() {
        vastaus = textVastaus.getText();
        ModalController.closeStage(textVastaus);
    }
    
    @FXML private void handleCancel() {
        ModalController.closeStage(textVastaus);
    }
    
    @Override
    public String getResult() {
        return vastaus;
    }
    
    @Override 
    public void setDefault(String oletus ) {
        textVastaus.setText(oletus);
    }
    
    /*
     * Mitä tehdään näyttämisen jälkeen
     */
   
    @Override
    public void handleShown() {
        textVastaus.requestFocus();
    }
    
    /**
     * Dialogi nimen kysymiselle, joka palauttaa nimen tai null
     * @param modalityStage mille ollaan modaalisia
     * @param oletus mikä nimi oletuksena
     * @return null jos painetaan cancel, muuten kirjoitetaan nimi
     */
    public static String kysyNimi(Stage modalityStage, String oletus) {
        return ModalController.showModal(
                RekisteriNimiController.class.getResource("MainView.fxml"),
                "saarekisteri",
                modalityStage, oletus);
    }
}
