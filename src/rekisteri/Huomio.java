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
    private String klo;
    
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
        huomiotunnus = seuraava;
        seuraava++;
        return huomiotunnus;       
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
        //
    }



}
