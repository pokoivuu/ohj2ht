package rekisteri;

import java.io.*;

/**
 * Huomio-luokka, rekisteröidään huomioita uniikilla identifikaattorilla
 * @author Teemu Kupiainen, Pauli Koivuniemi
 * @version 11 Mar 2021
 *
 */
public class Huomio {
    private int tunnusNro;
    private int huomiotunnus;
    private String merkinta;
    private String klo;
    
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
     * Alustetaan tietty huomio päivän uniikilla identifikaattorilla
     * @param paivatunnus Uniikki identifikaattori
     */
    public Huomio(int paivatunnus) {
        this.paivatunnus = paivatunnus;
    }
    
    
    /**
     * Annetaan seuraava vapaa rekisterinumero
     * @return Päivän uusi identifikaationumero
     * @example
     * <pre name="test">
<<<<<<< HEAD
     *   Huomio huom1 = new Huomio();
     *   huom1.getHuomioId() === 0;
     *   huom1.rekisterointi();
     *   Huomio huom2 = new Huomio();
     *   huom2.rekisterointi();
     *   int n1 = huom1.getHuomioId();
     *   int n2 = huom2.getHuomioId();
     *   n1 === n2-1;
=======
     *  Huomio sataa1 = new Huomio();
     *  sataa1.getHuomioId() === 0;
     *  sataa1.rekisterointi();
     *  Huomio sataa2 = new Huomio();
     *  sataa2.rekisterointi();
     *  sataa1.getHuomioId() === 1;
     *  int n1 = sataa1.getHuomioId();
     *  int n2 = sataa2.getHuomioId();
     *  n1 === n2-1;
>>>>>>> ffe04a3fe75ac04eea6189ac36e85be011a80ad8
     * </pre>
     */
    public int rekisterointi() {
        huomiotunnus = seuraava;
        seuraava++;
<<<<<<< HEAD
        return huomiotunnus;
        
=======
        return huomiotunnus;       
>>>>>>> ffe04a3fe75ac04eea6189ac36e85be011a80ad8
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
     * Täytetään testiarvot
     * @param nro viite
     */
    public void testiHuomio(int nro) {
        paivatunnus = nro;
        merkinta = "Sataa tavallista enemmän";
        klo = "11:00";
    }
    
    
    /**
     * Huomion tiedot
     * @param out tietovirta (out)
     */
    public void tulostus(PrintStream out) {
        out.println(huomiotunnus+ " " + merkinta + " " + klo);
        
    }
    
    /**
     * Tulostus 
     * @param os tietovirta (outputstream)
     */
    public void tulostus(OutputStream os) {
        tulostus(new PrintStream(os));
        
    }
    
    /**
     * testiohjelma Huomioille
     * @param args ei käytössä
     */
    public static void main(String[] args)  {
        Huomio huom = new Huomio();
<<<<<<< HEAD
        huom.vastaaHuomio(2);
        huom.tulosta(System.out);
=======
        huom.testiHuomio(2);
        huom.tulostus(System.out);
>>>>>>> ffe04a3fe75ac04eea6189ac36e85be011a80ad8
    }



}
