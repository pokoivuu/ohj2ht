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
import kanta.Tietue;



/**
 * @author Teemu Kupiainen & Pauli Koivuniemi
 * @version 22.4.2021
 *
 * @param <TYPE> minkä tyyppisiä olioita
 */
public class TietueDialogController <TYPE extends Tietue> implements ModalControllerInterface<TYPE>, Initializable {
    
    @FXML private ScrollPane panelTietue;
    @FXML private GridPane gridTietue;
    @FXML private Label labelVirhe;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        //alusta();
    }
    
    @FXML private void handleOK() {
        if ( tietueKohdalla != null && tietueKohdalla.anna(tietueKohdalla.ensimmainenKentta()).trim().equals("") ) {
            naytaVirhe("Ei saa olla tyhjä");
            return;
        }
        ModalController.closeStage(labelVirhe);
    }

    @FXML private void handleCancel() {
        tietueKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }
    
    ///
    private TYPE tietueKohdalla;
    private TextField[] muutokset;
    private int kentta = 0;

    
    /**
     * Luodaan GridPaneen tietueen tiedot
     * @param gridTietue mihin tiedot luodaan
     * @param aputietue malli josta tiedot otetaan
     * @return luodut tekstikentät
     */
    public static<TYPE extends Tietue> TextField[] luoKentat(GridPane gridTietue, TYPE aputietue) {
        gridTietue.getChildren().clear();
        TextField[] muutokset = new TextField[aputietue.getKenttia()];
        
        for (int i=0, k = aputietue.ensimmainenKentta(); k < aputietue.getKenttia(); k++, i++) {
            Label label = new Label(aputietue.getKysymys(k));
            gridTietue.add(label, 0, i);
            TextField muutos = new TextField();
            muutokset[k] = muutos;
            muutos.setId("e"+k);
            gridTietue.add(muutos, 1, i);
        }
        return muutokset;
    }

    /**
     * Palautetaan komponentin id:stä saatava luku
     * @param obj tutkittava komponentti
     * @param oletus mikä arvo jos id ei ole kunnollinen
     * @return komponentin id lukuna 
     */
    public static int getFieldId(Object obj, int oletus) {
        if ( !( obj instanceof Node)) return oletus;
        Node node = (Node)obj;
        return Mjonot.erotaInt(node.getId().substring(1),oletus);
    }
    
    /**
     * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
     * yksi iso tekstikenttä, johon voidaan tulostaa tietueen tiedot.
     */
    protected void alusta() {
        muutokset = luoKentat(gridTietue, tietueKohdalla);
        
        for (TextField muutos : muutokset)
            if ( muutos != null )
                muutos.setOnKeyReleased( e -> kasitteleMuutosTietueeseen((TextField)(e.getSource())));
        // panelTietue.setFitToHeight(true);
    }
    
    @Override
    public void setDefault(TYPE oletus) {
        tietueKohdalla = oletus;
        alusta();
        naytaTietue(muutokset, tietueKohdalla);
    }

    @Override
    public TYPE getResult() {
        return tietueKohdalla;
    }

    private void setKentta(int kentta) {
        this.kentta = kentta;
    }
    
    /**
     * Mitä tehdään kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        kentta = Math.max(tietueKohdalla.ensimmainenKentta(), Math.min(kentta, tietueKohdalla.getKenttia()-1));
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
     * Käsitellään teitueeseen tullut muutos
     * @param edit muuttunut kenttä
     */
    protected void kasitteleMuutosTietueeseen(TextField edit) {
        if (tietueKohdalla == null) return;
        int k = getFieldId(edit,tietueKohdalla.ensimmainenKentta());
        String s = edit.getText();
        String virhe = null;
        virhe = tietueKohdalla.aseta(k,s); 
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
     * Näytetään tietueen tiedot TextField komponentteihin
     * @param edits taulukko TextFieldeistä johon näytetään
     * @param tietue näytettävä tietue
     */
    public static void naytaTietue(TextField[] edits, Tietue tietue) {
        if (tietue == null) return;
        for (int k = tietue.ensimmainenKentta(); k < tietue.getKenttia(); k++) {
            edits[k].setText(tietue.anna(k));
        }
    }

    /**
     * Luodaan tietueen kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä dataan näytetään oletuksena
     * @param kentta mikä kenttä saa fokuksen kun näytetään
     * @return null jos painetaan Cancel, muuten täytetty tietue
     */
    public static<TYPE extends Tietue> TYPE kysyTietue(Stage modalityStage, TYPE oletus, int kentta) {
        return ModalController.<TYPE, TietueDialogController<TYPE>>showModal(
                TietueDialogController.class.getResource("TietueView.fxml"),
                "Rekisteri",
                modalityStage, oletus,
                ctrl -> ctrl.setKentta(kentta) 
                );
    }
}
