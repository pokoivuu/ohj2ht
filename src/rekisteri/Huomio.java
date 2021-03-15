package rekisteri;

import java.io.*;

/**
 * Huomio-luokka
 * @author Teemu Kupiainen, Pauli Koivuniemi
 * @version 11 Mar 2021
 *
 */
public class Huomio {
    private int paivatunnus;
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
     * Alustetaan tietty huomio uniikilla identifikaattorilla
     * @param paivatunnus Uniikki identifikaattori
     */
    public Huomio(int paivatunnus) {
        this.paivatunnus = paivatunnus;
    }
    
    
    /**
     * Annetaan seuraava vapaa rekisterinumero
     * @return Päivän uusi identifikaationumero
     */
    public int rekisterointi() {
        paivatunnus = seuraava;
        seuraava++;
        return paivatunnus;
        
    }


    /**
     * Getteri
     * @return päivän uniikki identifikaattori
     */
    public int getPaivaId() {
        return paivatunnus;
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
        pvm = "2.1.2021";
    }
    
    public void tulosta(PrintStream out) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * testiohjelma Huomioille
     * @param args ei käytössä
     */
    public static void main(String[] args)  {
        //
    }



}
