package fxRekisteri;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import rekisteri.Paiva;


/**
 * @author Teemu Kupiainen, Pauli Koivuniemi
 * @version 14 Apr 2021
 *
 */
public class PaivaDialogController implements ModalControllerInterface<Paiva>, Initializable {
    
    @FXML private ScrollPane panelPaiva;
    @FXML private GridPane gridPaiva;
    @FXML private Label labelVirhe;
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }
    
    @FXML private void handleOK() {
        if (paivaKohdalla != null && paivaKohdalla.getPaikka().trim().equals("")) {
            naytaVirhe("Paikka ei voi olla tyhjä");
            return;
        }
        ModalController.closeStage(labelVirhe);
    }
    
    @FXML private void handleCancel() {
        paivaKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }
    
    ///
    private Paiva paivaKohdalla;
    private static Paiva apu = new Paiva();
    private TextField[] muutokset;
    private int kentta = 0;
    
    /**
     * Luodaan GridPaneen tiedot
     * @param gridPaiva mihin tiedot tuodaan
     * @return tekstikentät
     */
    public static TextField[] luoKentat(GridPane gridPaiva) {
        gridPaiva.getChildren().clear();
        TextField [] muutokset = new TextField[apu.getKenttia()];
        
        for (int i = 0, k = apu.ensimmainenKentta(); k < apu.getKenttia(); k++, i++) {
            Label label = new Label(apu.getKysymys(k));
            gridPaiva.add(label, 0, i);
            TextField muutos = new TextField();
            muutokset[k] = muutos;
            muutos.setId("e" + k);
            gridPaiva.add(muutos, 1, i);
        }
        return muutokset;
    }
    
    /**
     * Tekstikenttien tyhjennys
     * @param muutokset kentät, jotka tyhjennetään
     */
    public static void tyhjenna(TextField[] muutokset) {
        for (TextField muutos: muutokset)
            if (muutos != null) muutos.setText("");
    }
    
    /**
     * Palautetaan komponentin id:n luku
     * @param obj tutkittava komponentti
     * @param oletus mikä arvo jos id ei käy
     * @return komponentin id kokonaislukuna
     */
    public static int getFieldId(Object obj, int oletus) {
        if (!( obj instanceof Node)) return oletus;
        Node node = (Node)obj;
        return Mjonot.erotaInt(node.getId().substring(1), oletus);
    }
    
    /**
     * Tekee alustukset
     */
    protected void alusta() {
        muutokset = luoKentat(gridPaiva);
        for (TextField muutos: muutokset)
            if (muutos != null)
                muutos.setOnKeyReleased(e-> kasitteleMuutosPaivaan((TextField)(e.getSource())));
        panelPaiva.setFitToHeight(true);
    }
    
    @Override
    public void setDefault(Paiva oletus) {
        paivaKohdalla = oletus;
        naytaPaiva(muutokset, paivaKohdalla);
    }

    
    @Override
    public Paiva getResult() {
        return paivaKohdalla;
    }
    
    private void setKentta(int kentta) {
        this.kentta = kentta;
    }
    
    @Override
    public void handleShown() {
        kentta = Math.max(apu.ensimmainenKentta(), Math.min(kentta, apu.getKenttia()-1));
        muutokset[kentta].requestFocus();
    }

    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
    
    /**
     * Käsitellään päivään tullut muutos
     * @param edit muuttunut kenttä
     */
    protected void kasitteleMuutosPaivaan(TextField edit) {
        if (paivaKohdalla == null) return;
        int k = getFieldId(edit,apu.ensimmainenKentta());
        String s = edit.getText();
        String virhe = null;
        virhe = paivaKohdalla.aseta(k,s); 
        if (virhe == null) {
            Dialogs.setToolTipText(edit,"");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit,virhe);
            edit.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        }
    }

    /**
     * Näytetään jäsenen tiedot TextField komponentteihin
     * @param muutokset taulukko TextFieldeistä johon näytetään
     * @param paiva näytettävä jäsen
     */
    public static void naytaPaiva(TextField[] muutokset, Paiva paiva) {
        if (paiva == null) return;
        for (int k = paiva.ensimmainenKentta(); k < paiva.getKenttia(); k++) {
            muutokset[k].setText(paiva.anna(k));
        }
    }

    /**
     * Luodaan jäsenen kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * TODO: korjattava toimimaan
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä dataan näytetään oletuksena
     * @param kentta mikä kenttä saa fokuksen kun näytetään
     * @return null jos painetaan Cancel, muuten täytetty tietue
     */
    public static Paiva kysyPaiva(Stage modalityStage, Paiva oletus, int kentta) {
        return ModalController.<Paiva, PaivaDialogController>showModal(
                    PaivaDialogController.class.getResource("MuokkausView.fxml"),
                    "Rekisteri",
                    modalityStage, oletus,
                    ctrl -> ctrl.setKentta(kentta) 
                );
    }   
}
