package rekisteri;

import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Huomio-luokka, rekisteröidään huomioita uniikilla identifikaattorilla
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
        return paivatunnus;
    }


    /**
     * Getteri
     * @return huomion uniikki identifikaattori
     */
    public int getHuomioId() {
        return huomiotunnus;
    }
    
    private void setHuomioId(int nro) {
        huomiotunnus = nro;
        if (huomiotunnus >= seuraava) seuraava = huomiotunnus + 1;
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
     * Huomion tiedot merkkijonoksi, jonka voi tallentaa tiedostoon
     * @return Huomion tiedot tolppaeroteltuna
     * @example
     * <pre name="test">
     *   Huomio huomio = new Huomio();
     *   huomio.parse("   1   |  5  |   Sataa paljon  | 12:00");
     *   huomio.toString()    === "1|5|Sataa paljon|12:00";
     * </pre>
     * 
     */
    @Override
    public String toString() {
        return "" + getHuomioId() + "|" + paivatunnus + "|" + merkinta + "|" + klo;
    }
    
    /**
     * Jäsentää huomion tiedot |-tolpalla erotellusta merkkijonosta.
     * @param rivi josta huomion tiedot otetaan
     * @example
     * <pre name="test">
     *   Huomio huomio = new Huomio();
     *   huomio.parse("   1   |  5  |   Sataa paljon  | 12:00");
     *   huomio.getPaivaId() === 5;
     *   huomio.toString()    === "1|5|Sataa paljon|12:00";
     *   
     *   huomio.rekisterointi();
     *   int n = huomio.getHuomioId();
     *   huomio.parse(""+(n+5));
     *   huomio.rekisterointi();
     *   huomio.getHuomioId() === n+5+1;
     *   huomio.toString()     === "" + (n+5+1) + "|5|Sataa paljon|12:00";

     * </pre>
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setHuomioId(Mjonot.erota(sb, '|', getHuomioId()));
        paivatunnus = Mjonot.erota(sb, '|', paivatunnus);
        merkinta = Mjonot.erota(sb, '|', merkinta);
        klo = Mjonot.erota(sb, '|', klo);
    }
    
    @Override
    public boolean equals (Object obj) {
        if (obj == null) return false;
        return this.toString().equals(obj.toString());
    }
    
    @Override
    public int hashCode() {
        return huomiotunnus;
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
