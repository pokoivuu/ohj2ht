package rekisteri;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Rekisteriluokka
 * @author Pauli Koivuniemi ja Teemu Kupiainen
 * @version 22.4.2021
 * Testien alustus
 ** @example
 * <pre name="testJAVA">
 * #import rekisteri.SailoException;
 *  private Rekisteri rekisteri;
 *  private Paiva maa1;
 *  private Paiva maa2;
 *  private int pid1;
 *  private int pid2;
 *  private Huomio sataa21;
 *  private Huomio sataa11;
 *  private Huomio sataa22; 
 *  private Huomio sataa12; 
 *  private Huomio sataa23;
 *  
 *  @SuppressWarnings("javadoc")
 *  public void alustaRekisteri() {
 *    rekisteri = new Rekisteri();
 *    maa1 = new Paiva(); maa1.paivanTiedot(); maa1.rekisteroi();
 *    maa2 = new Paiva(); maa2.paivanTiedot(); maa2.rekisteroi();
 *    pid1 = maa1.getTunnusNro();
 *    pid2 = maa2.getTunnusNro();
 *    sataa21 = new Huomio(pid2); sataa21.testiHuomio(pid2);
 *    sataa11 = new Huomio(pid1); sataa11.testiHuomio(pid1);
 *    sataa22 = new Huomio(pid2); sataa22.testiHuomio(pid2); 
 *    sataa12 = new Huomio(pid1); sataa12.testiHuomio(pid1); 
 *    sataa23 = new Huomio(pid2); sataa23.testiHuomio(pid2);
 *    try {
 *    rekisteri.lisaa(maa1);
 *    rekisteri.lisaa(maa2);
 *    rekisteri.lisaa(sataa21);
 *    rekisteri.lisaa(sataa11);
 *    rekisteri.lisaa(sataa22);
 *    rekisteri.lisaa(sataa12);
 *    rekisteri.lisaa(sataa23);
 *    } catch ( Exception e) {
 *       System.err.println(e.getMessage());
 *    }
 *  }
 * </pre>

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
     *  alustaRekisteri();
     *  rekisteri.etsi("*",0).size() === 2;
     *  rekisteri.lisaa(maa1);
     *  rekisteri.etsi("*",0).size() === 3;
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
     * Korvaa päivän tietorakenteessa.
     * Etsitään samalla tunnusnumerolla oleva päivä.  Jos ei löydy, 
     * niin lisätään uutena päivänä. 
     * @param paiva lisättävän päivän viite.  Huom tietorakenne muuttuu omistajaksi 
     * @throws SailoException jos tietorakenne on jo täynnä 
     * @example
     * <pre name="test">
     * #THROWS SailoException  
     *  alustaRekisteri();
     *  rekisteri.etsi("*",0).size() === 2;
     *  rekisteri.korvaaLisaa(maa1);
     *  rekisteri.etsi("*",0).size() === 2;
     * </pre>
     */ 
    public void korvaaLisaa(Paiva paiva) throws SailoException { 
        paivat.korvaaLisaa(paiva); 
    } 

    /** 
     * Korvaa huomion tietorakenteessa.
     * Etsitään samalla tunnusnumerolla oleva huomio.  Jos ei löydy, 
     * niin lisätään uutena huomiona. 
     * @param huomio lisättävän huomion viite.  Huom tietorakenne muuttuu omistajaksi 
     * @throws SailoException jos tietorakenne on jo täynnä 
     */ 
    public void korvaaLisaa(Huomio huomio) throws SailoException { 
        huomiot.korvaaLisaa(huomio); 
    } 

    
    /**
     * Poistaa päivistä ja huomioista päivän tiedot
     * @param paiva päivä, jota poistetaan
     * @return montako päivää poistettiin
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   alustaRekisteri();
     *   rekisteri.etsi("*",0).size() === 2;
     *   rekisteri.annaHuomio(maa1).size() === 2;
     *   rekisteri.poista(maa1) === 1;
     *   rekisteri.etsi("*",0).size() === 1;
     *   rekisteri.annaHuomio(maa1).size() === 0;
     *   rekisteri.annaHuomio(maa2).size() === 3;
     * </pre>
     */
    public int poista(Paiva paiva) {
        if (paiva == null) return 0;
        int ret = paivat.poista(paiva.getTunnusNro()); 
        huomiot.poistaPaivanHuomiot(paiva.getTunnusNro()); 
        return ret;
    }
    
    /** 
     * Poistaa tämän huomion 
     * @param huomio poistettava huomio 
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   alustaRekisteri();
     *   rekisteri.annaHuomio(maa1).size() === 2;
     *   rekisteri.poistaHuomio(sataa11);
     *   rekisteri.annaHuomio(maa1).size() === 1;
     */ 
    public void poistaHuomio(Huomio huomio) {
        huomiot.poista(huomio);
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
     * #import java.util.*;
     * #THROWS SailoException
     * 
     *  alustaRekisteri();
     *  Paiva maa3 = new Paiva();
     *  maa3.rekisteroi();
     *  rekisteri.lisaa(maa3);
     *  
     *  List<Huomio> loytyneet;
     *  loytyneet = rekisteri.annaHuomio(maa3);
     *  loytyneet.size() === 0; 
     *  loytyneet = rekisteri.annaHuomio(maa1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == sataa11 === true;
     *  loytyneet.get(1) == sataa12 === true;
     *  loytyneet = rekisteri.annaHuomio(maa2);
     *  loytyneet.size() === 3; 
     *  loytyneet.get(0) == sataa21 === true;
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
     * @example 
     * <pre name="test">
     *   #THROWS CloneNotSupportedException, SailoException
     *   alustaRekisteri();
     *   Paiva maa3 = new Paiva(); maa3.rekisteroi();
     *   maa3.aseta(1,"Peruna");
     *   rekisteri.lisaa(maa3);
     *   Collection<Paiva> loytyneet = rekisteri.etsi("*Peruna*", 1);
     *   loytyneet.size() === 1;
     *   Iterator<Paiva> it = loytyneet.iterator();
     *   it.next() == maa3 === true; 
     * </pre>
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
