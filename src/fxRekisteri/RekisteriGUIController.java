/**
 * Rekisterin käyttöliittymälle kontrolleriluokka
 */

package fxRekisteri;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List; 
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import rekisteri.Huomio;
import rekisteri.Paiva;
import rekisteri.Rekisteri;
import rekisteri.SailoException;


/**
 * @author Teemu Kupiainen, Pauli Koivuniemi
 * @version 17 Feb 2021
 *
 */
public class RekisteriGUIController implements Initializable {
    @FXML private TextField hakuField;
    @FXML private ComboBoxChooser<String> cbPaikka;
    @FXML private Label labelVirhe;
    @FXML private ScrollPane panelPaiva;
    @FXML private ListChooser<Paiva> chooserPaiva; 
    
    private String rekisterinimi = "2020-2021";
    
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }
    
    
    /// Eri menujen käsittelyt alempana
    
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
        //Dialogs.showMessageDialog("Ei toimi vielä");
        uusiPaiva();
    }
    
    
    @FXML private void handleMenuMuokkaa() {
        ModalController.showModal(RekisteriGUIController.class.getResource("MuokkausView.fxml"), "Muokkaus", null, "");
    }
    
    
    @FXML private void handleMenuPoista() {
        Dialogs.showMessageDialog("Ei toimi vielä");
    }
    
    
    @FXML private void handleMenuApua() {
        //Dialogs.showMessageDialog("Ei toimi vielä");
        avustus();
    }
    
    
    @FXML private void handleMenuTietoja() {        
        ModalController.showModal(RekisteriGUIController.class.getResource("TietoView.fxml"), "saarekisteri", null, "");
    }
    
    
    @FXML private void handleLisaaHuomio() {
        //Dialogs.showMessageDialog("Ei toimi vielä");
        uusiHuomio();
    }
    
    
    @FXML private void handlePoistaHuomio() {
        Dialogs.showMessageDialog("Ei toimi vielä");
    }
    
    
    @FXML private void handleMuokkaaHuomio() {
        Dialogs.showMessageDialog("Ei toimi vielä");
    }
    
    
    @FXML private void handleUusiPaiva() {
        //Dialogs.showMessageDialog("Ei toimi vielä");
        uusiPaiva();
    }
    
    
    
    @FXML private void handleHaku () {
        String haku = cbPaikka.getSelectedText();
        String hakuehto = hakuField.getText();
        if (hakuehto.isEmpty())
            virhe(null);
        else 
            virhe("Ei osata vielä hakea " + haku + ": " + hakuehto);
    }
    
    @FXML private void handleAvaa() {
        avaa();
    }
    
    /// Alemmat eivät liity suoraan käyttöliittymään
    
    private Rekisteri rekisteri;
    private Paiva paivaKohta;
    private TextArea areaPaiva = new TextArea();
    
    
    /**
     * Tekee alustukset
     */
    protected void alusta() {
        panelPaiva.setContent(areaPaiva);
        areaPaiva.setFont(new Font("Times New Roman", 12));
        panelPaiva.setFitToHeight(true);
        
        chooserPaiva.clear();
        chooserPaiva.addSelectionListener(e -> naytaPaiva());
        
        
    }
    
       
    /*
     * Virheenkäsittely
     */
    private void virhe(String virhe) {
        if (virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
    
        
    private void setTitle(String title) {
        ModalController.getStage(hakuField).setTitle(title);
    }
    
    
    /**
     * Tiedoston nimen kysely ja lukeminen
     * @return onnistuiko tiedoston avaus vai ei; true tai false
     */
    public boolean avaa() {
        String nimi = RekisteriNimiController.kysyNimi(null, rekisterinimi);
        if (nimi == null) return false;
        lueTiedosto(nimi);
        return true;
    }
    
    
    /**
     * Luetaan rekisteri oikeannimisestä tiedostosta
     * @param nimi Luettava nimi
     */
    protected void lueTiedosto(String nimi) {
        rekisterinimi = nimi;
        setTitle("Rekisteri - " + rekisterinimi);
        String virhe = "Ei toimi vielä";
            Dialogs.showMessageDialog(virhe);
    }
    
    /**
     * Tallennusmetodi
     */
    private void tallenna() {
        Dialogs.showMessageDialog("Tallennus ei toimi vielä");
    }
    
    
    /**
     * Tarkastetaan voidaanko sulkea eli onko tallennettu
     * @return Tallennustila, true tai false 
     */
    public boolean saakoSulkea() {
        tallenna();
        return true;
    }
    
    
    /**
     * Näyttää päivän tiedot
     */
    protected void naytaPaiva() {
        paivaKohta = chooserPaiva.getSelectedObject();
        
        if (paivaKohta == null) return;
        
        areaPaiva.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaPaiva)) {
            tulosta(os,paivaKohta);  
        }        
    }
    
    
    /**
     * Hakee päivän tiedot listaan
     * @param paivanro päivän numero
     */
    protected void hae(int paivanro) {
        chooserPaiva.clear();
        
        int indeksi = 0;
        for (int i = 0; i < rekisteri.getPaivia(); i++) {
            Paiva paiva = rekisteri.annaPaiva(i);
            if (paiva.getTunnusNro() == paivanro) indeksi = i;
            chooserPaiva.add(paiva.getPaikka(), paiva);
        }
        chooserPaiva.setSelectedIndex(indeksi);
    }
    
    
    /**
     * Luodaan uusi päivä
     */
    protected void uusiPaiva() {
        Paiva uusiPaiva = new Paiva();
        uusiPaiva.rekisteroi();
        uusiPaiva.paivanTiedot();
        try {
            rekisteri.lisaa(uusiPaiva);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden päivän luomisessa" + e.getMessage());
        }
        hae(uusiPaiva.getTunnusNro());
    }
    
    
    /**
     * 
     */
    public void uusiHuomio() {
        if (paivaKohta == null) return;
        Huomio huom = new Huomio();
        huom.rekisterointi();
        huom.testiHuomio(paivaKohta.getTunnusNro());
        rekisteri.lisaa(huom);
        hae(paivaKohta.getTunnusNro());
        //TODO: kesken
    }
    
    
    /**
     * @param rekisteri Käyttöliittymässä käytettävä rekisteri
     */
    public void setRekisteri(Rekisteri rekisteri) {
        this.rekisteri = rekisteri;
        naytaPaiva();
    }
    
    
    /**
     * @param os Tietovirta (outputstream)
     * @param paiva Tulostettava päivä
     */
    public void tulosta (PrintStream os, final Paiva paiva) {
        os.println("----------------------------------------------");
        paiva.tulosta(os);
        os.println("----------------------------------------------");
        List<Huomio> huomiot = rekisteri.annaHuomio(paiva);  
        for (Huomio huom : huomiot)
            huom.tulostus(os);  

    }
    
    /**
     * Tulostaa listan päivät tekstialueeseen
     * @param text Tulostettava alue
     */
    public void tulostaHalutut(TextArea text) {
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
            os.println("Tulostetaan päivät");
            for (int i = 0; i <rekisteri.getPaivia(); i++) {
                Paiva paiva = rekisteri.annaPaiva(i);
                tulosta(os,paiva);
                os.println("\n\n");
            }
        }
    }
    
    /*
     * Avaa ohjelman suunnitelmasivun (TIM)
     */
    private void avustus() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2021k/ht/teeilmku");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }
    

}
