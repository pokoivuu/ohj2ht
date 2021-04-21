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
import java.util.Collection;
import static fxRekisteri.TietueDialogController.getFieldId; 


import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.StringGrid;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
    @FXML private GridPane gridPaiva;
    @FXML private ListChooser<Paiva> chooserPaiva; 
    @FXML private StringGrid<Huomio> tableHuomiot;
    
    
    
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
    
    
    @FXML private void handleMuokkaaPaivaa() {
        muokkaa(kentta);
        //ModalController.showModal(RekisteriGUIController.class.getResource("MuokkausView.fxml"), "Muokkaus", null, "");
    }
    
    @FXML private void handleHakuehto() {
        hae(0);
    }
       
    @FXML private void handleMenuPoista() {
        poistaPaiva();
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
        poistaHuomio();
    }
    
    
    @FXML private void handleMuokkaaHuomio() {
        muokkaaHuomiota();
    }
    
    
    @FXML private void handleUusiPaiva() {
        //Dialogs.showMessageDialog("Ei toimi vielä");
        uusiPaiva();
    }
    
    
    
    @FXML private void handleHaku () {
        if (paivaKohta != null)
            hae(paivaKohta.getTunnusNro());
    }
    
    @FXML private void handleAvaa() {
        avaa();
    }
    
    /// Alemmat eivät liity suoraan käyttöliittymään
    
    private Rekisteri rekisteri;
    private Paiva paivaKohta;
    //private TextArea areaPaiva = new TextArea();
    private String rekisterinimi = "saarekisteri";
    private int kentta = 0;
    private TextField muutokset[];
    private static Huomio apuhuomio = new Huomio();
    private static Paiva apupaiva = new Paiva();
    
    
    
    /**
     * Tekee alustukset
     */
    protected void alusta() {
        chooserPaiva.clear();
        chooserPaiva.addSelectionListener(e -> naytaPaiva());
        
        cbPaikka.clear();
        for (int k = apupaiva.ensimmainenKentta(); k < apupaiva.getKenttia(); k++) 
            cbPaikka.add(apupaiva.getKysymys(k), null);
        cbPaikka.getSelectionModel().select(0);
        
        muutokset = TietueDialogController.luoKentat(gridPaiva, apupaiva);
        for (TextField muutos: muutokset)
            if (muutos != null) {
                muutos.setEditable(false);
                muutos.setOnMouseClicked(e -> { if ( e.getClickCount() > 1 ) muokkaa(getFieldId(e.getSource(),0)); });  
                muutos.focusedProperty().addListener((a,o,n) -> kentta = getFieldId(muutos,kentta));
            }
        
        int eka = apuhuomio.ensimmainenKentta();
        int lkm = apuhuomio.getKenttia();
        String[] headings = new String[lkm-eka];
        for (int i = 0, k = eka; k < lkm; i++, k++) headings[i] = apuhuomio.getKysymys(k);
        tableHuomiot.initTable(headings);
        tableHuomiot.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableHuomiot.setEditable(false);
        tableHuomiot.setPlaceholder(new Label("Ei vielä huomioita"));
        
        tableHuomiot.setColumnSortOrderNumber(1);
        tableHuomiot.setColumnSortOrderNumber(2);
        tableHuomiot.setColumnWidth(1, 60);
        tableHuomiot.setColumnWidth(2, 60);
        
        tableHuomiot.setOnMouseClicked( e -> { if ( e.getClickCount() > 1 ) tableHuomiot.setEditable(true); } );
        tableHuomiot.setOnMouseClicked( e -> { if ( e.getClickCount() > 1 ) muokkaaHuomiota(); } );       
        
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
     * @return Null jos onnistuu tai virheteksti
     */
    protected String lueTiedosto(String nimi) {
        rekisterinimi = nimi;
        setTitle("Rekisteri - " + rekisterinimi);
        try {
            rekisteri.lueTiedosto(nimi);
            hae(0);
            return null;
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage();
            if (virhe != null) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
    }
    
    /**
     * Tallennusmetodi
     */
    private String tallenna() {
        try {
            rekisteri.tallenna();
            return null;
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennus ei onnistunut! " + ex.getMessage());
            return ex.getMessage();
        }
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

        TietueDialogController.naytaTietue(muutokset, paivaKohta);
        naytaHuomiot(paivaKohta);
    }
    
    
    /**
     * Hakee päivän tiedot listaan
     * @param paivanro päivän numero
     */
    protected void hae(int paivanro) {
        int pnro = paivanro;
        if (pnro <= 0) {
            Paiva kohdalla = paivaKohta;
            if (kohdalla != null) pnro = kohdalla.getTunnusNro();
        }
        
        int ko = cbPaikka.getSelectionModel().getSelectedIndex() + apupaiva.ensimmainenKentta();
        String haku = hakuField.getText();
        if (haku.indexOf('*') < 0) haku = "*" + haku + "*";
        
        chooserPaiva.clear();
        
        int indeksi = 0;
        Collection<Paiva> paivat;
        try {
            paivat = rekisteri.etsi(haku, ko);
            int i = 0;
            for (Paiva paiva : paivat) {
                if (paiva.getTunnusNro() == paivanro) indeksi = i;
                chooserPaiva.add(paiva.getPaikka() + " " + paiva.getPaivamaara(), paiva);
                i++;
        }

        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Päivän hakeminen epäonnistui! " + ex.getMessage());
        }
        chooserPaiva.setSelectedIndex(indeksi);
    }
    
    
    /**
     * Luodaan uusi päivä
     */
    protected void uusiPaiva() {
        try {
        Paiva uusiPaiva = new Paiva();
        uusiPaiva = TietueDialogController.kysyTietue(null, uusiPaiva, 1);
        if (uusiPaiva == null) return;
        uusiPaiva.rekisteroi();
        rekisteri.lisaa(uusiPaiva);
        hae(uusiPaiva.getTunnusNro());
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }                
    }
    
    private void naytaHuomiot(Paiva paiva) {
        tableHuomiot.clear();
        if (paiva == null) return;
        
        try {
            List<Huomio> huomiot = rekisteri.annaHuomio(paiva);
            if (huomiot.size() == 0) return;
            for (Huomio huom: huomiot)
                naytaHuomio(huom);
        } catch (SailoException e) {
            virhe(e.getMessage());
        }
    }
    
    private void naytaHuomio(Huomio huom) {
        int kenttia = huom.getKenttia(); 
        String[] rivi = new String[kenttia-huom.ensimmainenKentta()]; 
        for (int i=0, k = huom.ensimmainenKentta(); k < kenttia; i++, k++) 
            rivi[i] = huom.anna(k); 
        tableHuomiot.add(huom,rivi);
    }
    
    
    /**
     * 
     */
    private void uusiHuomio() {
        if (paivaKohta == null) return;
        try {
            Huomio huom = new Huomio(paivaKohta.getTunnusNro());
            huom = TietueDialogController.kysyTietue(null, huom, 0);
            if (huom == null) return;
            huom.rekisterointi();
            rekisteri.lisaa(huom);
            naytaHuomiot(paivaKohta);
            tableHuomiot.selectRow(1000);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelma huomion lisäämisessä! " + e.getMessage());
        }
    }
    
    private void muokkaaHuomiota() {
        int r = tableHuomiot.getRowNr();
        if ( r < 0 ) return;
        Huomio huom = tableHuomiot.getObject();
        if ( huom == null ) return;
        int k = tableHuomiot.getColumnNr()+huom.ensimmainenKentta();
        try {
            huom = TietueDialogController.kysyTietue(null, huom.clone(), k);
            if ( huom == null ) return;
            rekisteri.korvaaLisaa(huom); 
            naytaHuomiot(paivaKohta); 
            tableHuomiot.selectRow(r);  
        } catch (CloneNotSupportedException  e) { /* clone on tehty */  
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia lisäämisessä: " + e.getMessage());
        }
    }
    
    private void muokkaa(int k) {
        if (paivaKohta == null) return;
        try {
            Paiva paiva;
            paiva = TietueDialogController.kysyTietue(null, paivaKohta.clone(), k);
            if (paiva == null) return;
            rekisteri.korvaaLisaa(paiva);
            hae(paiva.getTunnusNro());
        } catch (CloneNotSupportedException e){
            //
        } catch (SailoException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
    }
    
    
    /**
     * @param rekisteri Käyttöliittymässä käytettävä rekisteri
     */
    public void setRekisteri(Rekisteri rekisteri) {
        this.rekisteri = rekisteri;
        naytaPaiva();
    }
    
    /**
     * Poistetaan huomiotaulukosta valitulla kohdalla oleva huomio. 
     */
    private void poistaHuomio() {
        int rivi = tableHuomiot.getRowNr();
        if ( rivi < 0 ) return;
        Huomio huom = tableHuomiot.getObject();
        if ( huom == null ) return;
        rekisteri.poistaHuomio(huom);
        naytaHuomiot(paivaKohta);
        int huomioita = tableHuomiot.getItems().size(); 
        if ( rivi >= huomioita ) rivi = huomioita -1;
        tableHuomiot.getFocusModel().focus(rivi);
        tableHuomiot.getSelectionModel().select(rivi);
    }

    private void poistaPaiva() {
        Paiva paiva = paivaKohta;
        if ( paiva == null ) return;
        if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko päivä: " + paiva.getPaivamaara(), "Kyllä", "Ei") )
            return;
        rekisteri.poista(paiva);
        int index = chooserPaiva.getSelectedIndex();
        hae(0);
        chooserPaiva.setSelectedIndex(index);
    }
    
    
    /**
     * @param os Tietovirta (outputstream)
     * @param paiva Tulostettava päivä
     */
    public void tulosta (PrintStream os, final Paiva paiva) {
        os.println("----------------------------------------------");
        paiva.tulosta(os);
        os.println("----------------------------------------------");
        try {
            List<Huomio> huomiot = rekisteri.annaHuomio(paiva);
            for (Huomio huom : huomiot)
                huom.tulostus(os);
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Huomioiden hakemisessa ongelma! " + ex.getMessage());
        }
    }
    
    /**
     * Tulostaa listan päivät tekstialueeseen
     * @param text Tulostettava alue
     */
    public void tulostaHalutut(TextArea text) {
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
            os.println("Tulostetaan päivät");
            Collection <Paiva> paivat = rekisteri.etsi("", -1);
            for (Paiva paiva : paivat) {
                tulosta(os,paiva);
                os.println("\n\n");
            }
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Päivän hakemisessa ongelma! " + ex.getMessage()); 
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
