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
     *  Huomio sataa1 = new Huomio();
     *  sataa1.getHuomioId() === 0;
     *  sataa1.rekisterointi();
     *  Huomio sataa2 = new Huomio();
     *  sataa2.rekisterointi();
     *  sataa1.getHuomioId() === 1;
     *  int n1 = sataa1.getHuomioId();
     *  int n2 = sataa2.getHuomioId();
     *  n1 === n2-1;
     * </pre>
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
        Huomio huom = new Huomio();
        huom.testiHuomio(2);
        huom.tulostus(System.out);
    }



}
