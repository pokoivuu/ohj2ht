package rekisteri;

import java.io.*;

import org.junit.platform.engine.support.descriptor.CompositeTestSource;

import fi.jyu.mit.ohj2.Mjonot;

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
        return "" +
                getTunnusNro() + "|" +
                paikka + "|" +
                aika + "|" +
                paivaMaara + "|" +
                lampoTila + "|" +
                sadeMaara + "|" +
                kosteus + "|" +
                tuulenNopeus + "|";
                
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
                    setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
                    paikka = Mjonot.erota(sb, '|', paikka);
                    aika = Mjonot.erota(sb, '|', aika);
                    paivaMaara = Mjonot.erota(sb, '|', paivaMaara);
                    lampoTila = Mjonot.erota(sb, '|', lampoTila);
                    sadeMaara = Mjonot.erota(sb, '|', sadeMaara);
                    kosteus = Mjonot.erota(sb, '|', kosteus);
                    tuulenNopeus = Mjonot.erota(sb, '|', tuulenNopeus);
                }

    
    @Override
    public int hashCode() {
        return tunnusNro;
    }
    
    @Override
    public boolean equals (Object paiva) {
        if (paiva == null) return false;
        return this.toString().equals(paiva.toString());
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
