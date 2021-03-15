package rekisteri;

import java.io.*;

/**
 * Päivä-luokka
 * @author Teemu Kupiainen, Pauli Koivuniemi
 * @version 25 Feb 2021
 *
 */
public class Paiva {

    private int tunnusNro           = 0;
    private String paikka           = "";
    private String aika             = "";
    private String paivaMaara       = "";
    private double lampoTila        = 0;
    private double sadeMaara        = 0;
    private double kosteus          = 0;
    private double tuulenNopeus     = 0;
    
    private static int seuraava = 1;
    
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
     * Getteri
     * @return paikka
     */
    public String getPaikka() {
        return paikka;
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
     * Tulostetaan päivän tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }

    
    /**
     * Antaa päivälle seuraavan (vapaan) rekisterinumeron
     * @return Päivän uusi tunnunsnumero
     */
    public int rekisteroi() {
        tunnusNro = seuraava;
        seuraava++;
        return tunnusNro;
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
        Paiva keskiviikko = new Paiva();
        keskiviikko.paivanTiedot();
        keskiviikko.tulosta(System.out);
    }

}
