package rekisteri;

import java.io.*;
import java.util.Comparator;

import fi.jyu.mit.ohj2.Mjonot;
import kanta.PvmTarkistus;
import kanta.Tietue;


/**
 * Päivä-luokka
 * @author Teemu Kupiainen, Pauli Koivuniemi
 * @version 25 Feb 2021
 *
 */
public class Paiva implements Cloneable, Tietue {

    private int tunnusNro           = 0;
    private String paikka           = "";
    private String aika             = "";
    private String paivaMaara       = "";
    private double lampoTila        = 0;
    private double sadeMaara        = 0;
    private double kosteus          = 0;
    private double tuulenNopeus     = 0;
    
    private static int seuraava = 1;
    
    @SuppressWarnings("javadoc")
    public static class Vertailija implements Comparator<Paiva>{
        private int k;
        
        public Vertailija(int k) {
            this.k = k;
        }
        
        @Override
        public int compare (Paiva paivaEka, Paiva paivaToka) {
            return paivaEka.getAvain(k).compareToIgnoreCase(paivaToka.getAvain(k));
        }
    }
    
    /**
     * Metodi, jolla täytetään päivän tiedot
     */
    public void paivanTiedot() {
       paikka = "Lempäälä";
       aika = "12:00";
       paivaMaara = "12.12.2020";
       lampoTila = 13.5;
       sadeMaara = 44;
       kosteus = 100;
       tuulenNopeus = 2;
    }
    
    
    /**
     * @param k minkä kentän sisältö palautetaan
     * @return kentän sisältö merkkijonona
     */
    public String getAvain(int k) {
        switch (k) {
        case 0: return "" + tunnusNro;
        case 1: return "" + paikka.toUpperCase();
        case 2: return "" + aika;
        case 3: return "" + paivaMaara;
        case 4: return "" + lampoTila;
        case 5: return "" + sadeMaara;
        case 6: return "" + kosteus;
        case 7: return "" + tuulenNopeus;
        default: return "???";
        }
    }
    
    /**
     * Päivän kenttien lkm
     * @return kenttien lkm
     */
    @Override
    public int getKenttia() {
        return 8;
    }
    
    /**
     * Ensimmäinen kenttä, jota kysytään
     * @return ensimmäisen kentän indeksi
     */
    @Override
    public int ensimmainenKentta() {
        return 1;
    }

    /**
     * Getteri
     * @return paikka
     */
    public String getPaikka() {
        return paikka;
    }
    
    public String getPaivamaara() {
        return paivaMaara;
    }
    
    public double getSademaara() {
        return sadeMaara;
    }
    
    /**
     * Merkkijonoalustus tyhjiksi jonoiksi
     * tunnusnro = 0;
     */
    public Paiva() {
        //ei mitään
    }
    
    /**
     * @param k monesko sisältö palautetaan
     * @return kentän sisältö merkkijono
     */
    @Override
    public String anna (int k) {
        switch (k) {
        case 0: return "" + tunnusNro;
        case 1: return "" + paikka;
        case 2: return "" + aika;
        case 3: return "" + paivaMaara;
        case 4: return "" + lampoTila;
        case 5: return "" + sadeMaara;
        case 6: return "" + kosteus;
        case 7: return "" + tuulenNopeus;
        default: return "???";
        }
    }
    
    @Override
    public String aseta(int k, String jono) {
        String tjono = jono.trim();
        StringBuilder sb = new StringBuilder(tjono);
        switch (k) {
        case 0:
            setTunnusNro(Mjonot.erota(sb, '§', getTunnusNro()));
            return null;
        case 1:
            paikka = tjono;
            return null;
        case 2:
            PvmTarkistus klo = new PvmTarkistus();
            String virhe = klo.tarkistaKello(tjono);
            if (virhe != null) return virhe;
            aika = tjono;
            return null;
        case 3:
            PvmTarkistus pvm = new PvmTarkistus();
            String virh = pvm.tarkistaPvm(tjono);
            if (virh != null) return virh;
            paivaMaara = tjono;
            return null;
        case 4:
            try {
                lampoTila = Mjonot.erotaEx(sb, '§', lampoTila);
            } catch ( NumberFormatException ex ) {
                return "Lämpötila väärin " + ex.getMessage();
            }
            return null;
        case 5:
            try {
                sadeMaara = Mjonot.erotaEx(sb, '§', sadeMaara);
            } catch ( NumberFormatException ex ) {
                return "Sademäärä väärin " + ex.getMessage();
            }
            return null;
        case 6:
            try {
                kosteus = Mjonot.erotaEx(sb, '§', kosteus);
            } catch ( NumberFormatException ex ) {
                return "Kosteus väärin " + ex.getMessage();
            }
            return null;
        case 7:
            try {
                tuulenNopeus = Mjonot.erotaEx(sb, '§', tuulenNopeus);
            } catch ( NumberFormatException ex ) {
                return "Tuulen nopeus väärin " + ex.getMessage();
            }
            return null;
        default:
            return "???";
        }              
    }
    
    @Override
    public String getKysymys(int k) {
        switch (k) {
        case 0: return "TunnusNro";
        case 1: return "paikka";
        case 2: return "aika";
        case 3: return "päivämäärä";
        case 4: return "lämpötila celsius";
        case 5: return "sademäärä mm";
        case 6: return "kosteus %";
        case 7: return "tuulennopeus m/s";
        default:
            return "???";
        }
    }
    
    
    /**
     * Tulostetaan päivän tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(tunnusNro);
        out.println(paikka + " | " + paivaMaara + " | " + aika);
        out.println("Lämpötila: " + lampoTila + "C");
        out.println("Sademäärä: " + sadeMaara + "mm");
        out.println("Ilmankosteus: " + kosteus + "%");
        out.println("Tuulen nopeus: " + tuulenNopeus + "m/s");
    }

    /**
     * Päivän tiedot merkkijonona, jonka voi tallentaa tiedostoon.
     * @return päivä tolppaeroteltuna merkkijonona
     * @example
     * <pre name="test">
     *   Paiva paiva = new Paiva();
     *   paiva.parse("   1  |  Lempäälä   | 12:00");
     *   paiva.toString().startsWith("1|Lempäälä|12:00|") === true;
     * </pre>
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        String e = "";
        for (int k = 0; k < getKenttia(); k++) {
            sb.append(e);
            sb.append(anna(k));
            e = "|";
        }
        return sb.toString();
    }
    
    /**
     * Jäsentää päivän tiedot |-tolpalla erotellusta merkkijonosta
     * @param rivi josta päivän tiedot otetaan
     * @example
     * <pre name="test">
     *   Paiva paiva = new Paiva();
     *   paiva.parse("   1  |  Lempäälä   | 12:00");
     *   paiva.getTunnusNro() === 1;
     *   paiva.toString().startsWith("1|Lempäälä|12:00|") === true;
     *   
     *   paiva.rekisteroi();
     *   int n = paiva.getTunnusNro();
     *   paiva.parse(""+(n+5));
     *   paiva.rekisteroi();
     *   paiva.getTunnusNro() === n+5+1;
     * </pre>
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        for (int k = 0; k < getKenttia(); k++) {
            aseta(k, Mjonot.erota(sb, '|'));
        }
    }

    
    @Override
    public int hashCode() {
        return tunnusNro;
    }
    
    /**
     * Tehdään identtinen klooni päivästä
     * @return Object kloonattu päivä
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Paiva paiva = new Paiva();
     *   paiva.parse("   1  |  Lempäälä   | 12:00");
     *   Paiva kopio = paiva.clone();
     *   kopio.toString() === paiva.toString();
     *   paiva.parse("   2  |  Vihti   | 12:00");
     *   kopio.toString().equals(paiva.toString()) === false;
     * </pre>
     */

    @Override
    public Paiva clone() throws CloneNotSupportedException {
        Paiva uusi;
        uusi = (Paiva) super.clone();
        return uusi;
    }
    

    
    /**
     * Tutkii onko päivän tiedot samat kuin parametrina tuodun päivän tiedot
     * @param paiva päivä johon verrataan
     * @return true jos kaikki tiedot samat, false muuten
     * @example
     * <pre name="test">
     *   Paiva paiva1 = new Paiva();
     *   paiva1.parse("   2  |  Lempäälä   | 12:00");
     *   Paiva paiva2 = new Paiva();
     *   paiva2.parse("   2  |  Lempäälä   | 12:00");
     *   Paiva paiva3 = new Paiva();
     *   paiva3.parse("   2  |  Lempäälä   | 12:01");
     *   
     *   paiva1.equals(paiva2) === true;
     *   paiva2.equals(paiva1) === true;
     *   paiva1.equals(paiva3) === false;
     *   paiva3.equals(paiva2) === false;
     * </pre>
     */
    public boolean equals (Paiva paiva) {
        if (paiva == null) return false;
        for (int k = 0; k < getKenttia(); k++)
            if (!anna(k).equals(paiva.anna(k)) ) return false;
        return true;
    }
    
    @Override
    public boolean equals (Object paiva) {
        if (paiva instanceof Paiva) return equals((Paiva)paiva);
        return false;
    }
    
    /**
     * Tulostetaan päivän tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }

    
    /**
     * Antaa päivälle seuraavan (vapaan) rekisterinumeron
     * @return Päivän uusi tunnunsnumero
     * @example
     * <pre name="test">
     *   Paiva maanantai1 = new Paiva();
     *   maanantai1.getTunnusNro() === 0;
     *   maanantai1.rekisteroi();
     *   Paiva maanantai2 = new Paiva();
     *   maanantai2.rekisteroi();
     *   int n1 = maanantai1.getTunnusNro();
     *   int n2 = maanantai2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraava;
        seuraava++;
        return tunnusNro;
    }
    
    /**
     * Asettaa tunnusnumeron ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if (tunnusNro >= seuraava) seuraava = tunnusNro + 1;
    }
    
    /**
     * Palauttaa päivän tunnusnumeron
     * @return päivän tunnusnumero
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    
    
    /**
     * Pääohjelma luokan testausta varten
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Paiva paiv1 = new Paiva();
        Paiva paiv2 = new Paiva();
        paiv1.paivanTiedot();
        paiv1.tulosta(System.out);
        paiv2.paivanTiedot();
        paiv2.tulosta(System.out);
    }

}
