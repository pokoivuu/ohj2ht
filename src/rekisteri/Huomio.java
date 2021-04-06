package rekisteri;

import java.io.*;

/**
 * Huomio-luokka
 * @author Teemu Kupiainen, Pauli Koivuniemi
 * @version 11 Mar 2021
 *
 */
public class Huomio {
    private int tunnusNro;
    private int huomiotunnus;
    private String merkinta;
    private String pvm;
    
    private static int seuraava = 1;
    
    
    /**
     * Alustus
     */
    public Huomio() {
        //
    }
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot huomiolle
     * @param id viite Päivään, jonka huomioon viitataan
     */
    public void vastaaHuomio(int id) {
        tunnusNro = id;
        merkinta = "Sataa vettä";
        pvm = "12.12.2020";
    }
    
    /**
     * Alustetaan tietty huomio uniikilla identifikaattorilla
     * @param id Uniikki identifikaattori
     */
    public Huomio(int id) {
        this.huomiotunnus = id;
    }
    
    
    /**
     * Annetaan seuraava vapaa rekisterinumero
     * @return Päivän uusi identifikaationumero
     * @example
     * <pre name="test">
     *   Huomio huom1 = new Huomio();
     *   huom1.getHuomioId() === 0;
     *   huom1.rekisterointi();
     *   Huomio huom2 = new Huomio();
     *   huom2.rekisterointi();
     *   int n1 = huom1.getHuomioId();
     *   int n2 = huom2.getHuomioId();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisterointi() {
        huomiotunnus = seuraava;
        seuraava++;
        return huomiotunnus;
        
    }


    /**
     * Tulostetaan huomion tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(merkinta + " " + pvm);
    }


    /**
     * Tulostetaan päivän tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * Getteri
     * @return päivän uniikki identifikaattori
     */
    public int getPaivaId() {
        return tunnusNro;
    }


    /**
     * Getteri
     * @return huomion uniikki identifikaattori
     */
    public int getHuomioId() {
        return huomiotunnus;
    }
    
    /**
     * testiohjelma Huomioille
     * @param args ei käytössä
     */
    public static void main(String[] args)  {
        Huomio huom = new Huomio();
        huom.vastaaHuomio(2);
        huom.tulosta(System.out);
    }
}
