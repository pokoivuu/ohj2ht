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
     * @param id Uniikki identifikaattori
     */
    public Huomio(int id) {
        this.huomiotunnus = id;
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
}
