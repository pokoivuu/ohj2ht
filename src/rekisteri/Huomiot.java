package rekisteri;



    

import java.util.*;

/**
 * Huomiot-luokka, jossa lisätään esim. uusi huomio
 * @author Teemu Kupiainen, Pauli Koivuniemi
 * @version 11 Mar 2021
 *
 */
public class Huomiot implements Iterable<Huomio> {

    private String huomiotTiedosto ="";
    
    private final Collection<Huomio> taulukko = new ArrayList<Huomio>();
    
    
    /**
     * Alustaminen
     */
    public Huomiot() {
        // Ei vielä mitään
    }
    
    
    /**
     * Lisätään uusi huomio tietorakenteeseen
     * @param h Lisättävä huomio
     */
    public void lisaa(Huomio h) {
        taulukko.add(h);
    }
    
    
    /**
     * Lukee tiedoston //(KESKEN)
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException Jos lukeminen ei onnistu
     * 
     */
    public void lueTiedosto(String hakemisto) throws SailoException {
        huomiotTiedosto = hakemisto + ".huom";
        throw new SailoException("Ei osaa vielä lukea" + huomiotTiedosto);
    }
    
    
    /**
     * Tallentaa tiedostoon
     * @throws SailoException Jos epäonnistuu tallennuksessa
     */
    public void tallennus() throws SailoException {
        throw new SailoException("Ei osaa vielä tallentaa" + huomiotTiedosto);
    }
    
    
    /**
     * Palauttaa rekisterin huomioiden lukumäärän
     * @return Taulukon alkioiden lukumäärän eli huomioiden määrä
     */
    public int getLkm() {
        return taulukko.size();
    }
    
    
    /**
     * Iteraattori kaikkien taulukon huomioiden läpikäymiseen
     * @example
     * <pre name="test">
     *  #PACKAGEIMPORT
     *  #import java.util.*;
     *  
     *  Huomiot huomioita = new Huomiot();
     *  Huomio sataa1 = new Huomio(1); huomioita.lisaa(sataa1);
     *  Huomio sataa2 = new Huomio(2); huomioita.lisaa(sataa2);
     *  Huomio sataa12 = new Huomio(1); huomioita.lisaa(sataa12);
     *  Huomio sataa21 = new Huomio(2); huomioita.lisaa(sataa21);
     *  Huomio sataa13 = new Huomio(1); huomioita.lisaa(sataa13);
     *  
     *  Iterator<Huomio> ite = huomioita.iterator();
     *  ite.next() === sataa1;
     *  ite.next() === sataa2;
     *  ite.next() === sataa12;
     *  ite.next() === sataa21;
     *  ite.next() === sataa13;
     *  ite.next() === sataa1; #THROWS NoSuchElementException
     *  
     *  int n = 0;
     *  int htunnus[] = {1,2,1,2,1};
     *  
     *  for (Huomio h : huomioita) {
     *      h.getPaivaId() === htunnus[n]; n++;
     *  }
     *
     *  n === 5;
     * </pre>
     */
    
    @Override
    public Iterator<Huomio> iterator() {
        return taulukko.iterator();
    }
    
    
    /**
     * 
     * @param tunnus uniikki identifikaattori
     * @return Tietorakenne, jossa viitteet löydettyihin huomioihin
     * @example
     * <pre name="test">
     *  #import java.util.*;
     *  
     *  Huomiot huomioita = new Huomiot();
     *  Huomio sataa1 = new Huomio(1); huomioita.lisaa(sataa1);
     *  Huomio sataa2 = new Huomio(2); huomioita.lisaa(sataa2);
     *  Huomio sataa12 = new Huomio(1); huomioita.lisaa(sataa12);
     *  Huomio sataa21 = new Huomio(2); huomioita.lisaa(sataa21);
     *  Huomio sataa13 = new Huomio(1); huomioita.lisaa(sataa13);
     *  Huomio sataa41 = new Huomio(4); huomioita.lisaa(sataa41);
     *  
     *  List <Huomio> test;
     *  test = huomioita.annaHuomio(10);
     *  test.size() === 0;
     *  test = huomioita.annaHuomio(1);
     *  test.size() === 3;
     *  test.get(0) == sataa1 === true;
     *  test.get(1) == sataa12 === true;
     *  test = huomioita.annaHuomio(4);
     *  test.size() === 1;
     *  test.get(0) == sataa41 === true;
     *  
     * </pre>
     */
    public List<Huomio> annaHuomio(int tunnus) {
        List<Huomio> loytyy = new ArrayList<Huomio>();
        for (Huomio h : taulukko) {
            if (h.getPaivaId() == tunnus) loytyy.add(h);
        }
        return loytyy;
    }
    
    
    /**
     * 
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Huomiot huomioita = new Huomiot();
        Huomio sataa1 = new Huomio();
        sataa1.testiHuomio(2);
        Huomio sataa2 = new Huomio();
        sataa2.testiHuomio(1);
        Huomio sataa3 = new Huomio();
        sataa3.testiHuomio(2);
        
        
        
        huomioita.lisaa(sataa1);
        huomioita.lisaa(sataa2);
        huomioita.lisaa(sataa3);
        huomioita.lisaa(sataa2);
        
        List<Huomio> huomiot2 = huomioita.annaHuomio(1);
        for (Huomio h : huomiot2) {
            System.out.print(h.getPaivaId() + " ");
            h.tulostus(System.out);
        }
    }

}
