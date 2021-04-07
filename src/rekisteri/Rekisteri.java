package rekisteri;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Rekisteriluokka
 * @author Teemu Kupiainen, Pauli Koivuniemi
 * @version 11 Mar 2021
 *
 */
public class Rekisteri {
    private Huomiot huomiot = new Huomiot();
    private Paivat paivat = new Paivat();
    
    /**
     * Palauttaa päivien määrän
     * @return päivät
     */
    public int getPaivia() {
        return paivat.getLkm();
    }
     
    /**
     * Lisää rekisteriin uuden päivän
     * @param paiva lisättävä päivä
     * @throws SailoException Jos lisäys ei onnistu
     * @example
     * <pre name="test">
     * #THROWS SailoException
     *  Rekisteri rekisteri = new Rekisteri();
     *  Paiva maanantai = new Paiva(), tiistai = new Paiva();
     *  maanantai.rekisteroi(); tiistai.rekisteroi();
     *  
     *  rekisteri.getPaivia() === 0;
     *  rekisteri.lisaa(maanantai); rekisteri.getPaivia() === 1;
     *  rekisteri.lisaa(tiistai); rekisteri.getPaivia() === 2;
     *  rekisteri.lisaa(maanantai); rekisteri.getPaivia() === 3;
     *  rekisteri.annaPaiva(0) === maanantai;
     *  rekisteri.annaPaiva(1) === tiistai;
     *  rekisteri.annaPaiva(2) === maanantai;
     *  rekisteri.annaPaiva(3) === maanantai; #THROWS IndexOutOfBoundsException
     *  rekisteri.lisaa(maanantai); rekisteri.getPaivia() === 4;
     *  rekisteri.lisaa(maanantai); rekisteri.getPaivia() === 5;
     *  rekisteri.lisaa(maanantai); //6
     *  rekisteri.lisaa(maanantai); //7
     *  rekisteri.lisaa(maanantai); //8
     *  rekisteri.lisaa(maanantai); //9
     *  rekisteri.lisaa(maanantai); //10
     *  rekisteri.getPaivia() === 10;
     *  rekisteri.lisaa(tiistai); #THROWS SailoException
     * </pre>
     */
    public void lisaa(Paiva paiva) throws SailoException {
        paivat.lisaa(paiva);
    }
    
    /**
     * Lisää rekisteriin uuden huomion
     * @param h lisättävä huomio
     * @throws SailoException jos ongelma
     */
    public void lisaa(Huomio h) throws SailoException {
        huomiot.lisaa(h);
    }
    
    /**
     * Palauttaa i:n päiviä
     * @param i monesko päivä palautetaan
     * @return viite i:teen päivään
     * @throws IndexOutOfBoundsException jos i väärä
     */
    public Paiva annaPaiva(int i) throws IndexOutOfBoundsException {
        return paivat.anna(i);
    }

    /**
     * Haetaan kaikki huomiot
     * @param paiva päivä jolta huomioita haetaan
     * @return palauttaa tietorakenne jossa viitteet löydettyihin päiviin
     * @throws SailoException jos tulee ongelma
     * @example
     * <pre name="test">
     *  #import java.util.*;
     *  
     *  Rekisteri rekisteri = new Rekisteri();
     *  Paiva maanantai = new Paiva(), tiistai = new Paiva(), keskiviikko = new Paiva();
     *  maanantai.rekisteroi(); tiistai.rekisteroi(); keskiviikko.rekisteroi();
     *  int tunnus1 = maanantai.getTunnusNro();
     *  int tunnus2 = tiistai.getTunnusNro();
     *  Huomio sataa1 = new Huomio(tunnus1); rekisteri.lisaa(sataa1);
     *  Huomio sataa12 = new Huomio(tunnus1); rekisteri.lisaa(sataa12);
     *  Huomio sataa2 = new Huomio(tunnus2); rekisteri.lisaa(sataa2);
     *  Huomio sataa21 = new Huomio(tunnus2); rekisteri.lisaa(sataa21);
     *  Huomio sataa22 = new Huomio(tunnus2); rekisteri.lisaa(sataa22);
     *  
     *  List <Huomio> test;
     *  test = rekisteri.annaHuomio(keskiviikko);
     *  test.size() === 0;
     *  test = rekisteri.annaHuomio(maanantai);
     *  test.size() === 2;
     *  test.get(0) == sataa1 === true;
     *  test.get(1) == sataa12 === true;
     *  test = rekisteri.annaHuomio(tiistai);
     *  test.size() === 3;
     *  test.get(0) == sataa2 === true;
     * </pre>
     */

    public List<Huomio> annaHuomio(Paiva paiva) throws SailoException {
        return huomiot.annaHuomio(paiva.getTunnusNro());   

    }
    
    /**
     * Asettaa tiedostojen nimet
     * @param nimi uusi nimi
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi="";
        if (!nimi.isEmpty()) hakemistonNimi = nimi +"/";
        paivat.setTiedostonPerusNimi(hakemistonNimi + "paivat");
        huomiot.setTiedostonPerusNimi(hakemistonNimi + "huomiot");
    }
    
    /**
     * Lukee rekisterin tiedot tiedostosta
     * @param tiedostonNimi nimi jota käytetään
     * @throws SailoException mikäli luku epäonnistuu
     */
    public void lueTiedosto(String tiedostonNimi) throws SailoException {
        paivat = new Paivat();
        huomiot = new Huomiot();
        
        setTiedosto(tiedostonNimi);
        paivat.lueTiedostosta();
        huomiot.lueTiedosto();
    }
    
    /**
     * Tallentaa rekisterin tiedot tiedostoon
     * @throws SailoException jos tallentaminen ei onnistu
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            paivat.tallenna();
        } catch (SailoException ex) {
            virhe = ex.getMessage();
        }

        try {
            huomiot.tallennus();
        } catch (SailoException ex) {
            virhe += ex.getMessage();
        }
        if (!"".equals(virhe)) throw new SailoException(virhe);
   
    }

    /**
     * @param haku Annettu hakuehto
     * @param ko Indeksi kentälle
     * @return tietorakenne löytyneistä päivistä
     * @throws SailoException Jos tulee virheitä
     */
    public Collection<Paiva> etsi(String haku, int ko) throws SailoException {
        return paivat.etsi(haku, ko);
    }
    
    /**,
     * Pääohjelma rekisterin testausta varten
     * @param args ei käytetä
     */
    public static void main(String[] args) {
        Rekisteri rekisteri = new Rekisteri();
        
        try {
            Paiva maanantai = new Paiva(), tiistai = new Paiva();
            maanantai.rekisteroi();
            maanantai.paivanTiedot();
            tiistai.rekisteroi();
            tiistai.paivanTiedot();
            
            rekisteri.lisaa(maanantai);
            rekisteri.lisaa(tiistai);
            int tunnus1 = maanantai.getTunnusNro();
            int tunnus2 = tiistai.getTunnusNro();
            Huomio sataa1 = new Huomio(tunnus1); sataa1.testiHuomio(tunnus1); rekisteri.lisaa(sataa1);
            Huomio sataa12 = new Huomio(tunnus1); sataa1.testiHuomio(tunnus1); rekisteri.lisaa(sataa12);
            Huomio sataa2 = new Huomio(tunnus2); sataa2.testiHuomio(tunnus2); rekisteri.lisaa(sataa2);
            Huomio sataa21 = new Huomio(tunnus2); sataa21.testiHuomio(tunnus2); rekisteri.lisaa(sataa21);
            Huomio sataa22 = new Huomio(tunnus2); sataa22.testiHuomio(tunnus2); rekisteri.lisaa(sataa22);
            
            System.out.println("============= Rekisterin testi =================");
            Collection <Paiva> paivat = rekisteri.etsi("", -1);
            int i = 0;
            for (Paiva paiva : paivat) {
                System.out.println("Päivä kohdassa: " + i);
                paiva.tulosta(System.out);
                List<Huomio> loytyneet = rekisteri.annaHuomio(paiva);
                for (Huomio huomio : loytyneet)
                    huomio.tulosta(System.out);
                i++;                
            }
            
        }  catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
        
    }

    
}
