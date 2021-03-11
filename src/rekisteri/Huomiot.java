package rekisteri;

<<<<<<< HEAD
public class Huomiot {
    
=======
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
        huomiotTiedosto = hakemisto + ".h";
        throw new SailoException("Ei osaa vielä lukea" + huomiotTiedosto);
    }
    
    
    /**
     * Tallentaa tiedostoon
     * @throws SailoException Jos epäonnistuu tallennuksessa
     */
    public void tallenna() throws SailoException {
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
     */
    @Override
    public Iterator<Huomio> iterator() {
        return taulukko.iterator();
    }
    
    
    /**
     * 
     * @param huomiotunnus huomion uniikki identifikaattori
     * @return Tietorakenne, jossa viitteet löydettyihin huomioihin
     */
    public List<Huomio> annaHuomio(int huomiotunnus) {
        List<Huomio> loytyy = new ArrayList<Huomio>();
        for (Huomio h : taulukko) {
            if (h.getPaivaId() == huomiotunnus) loytyy.add(h);
        }
        return loytyy;
    }
    
    
    /**
     * 
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        //Tähän tulee testiohjelma
    }
>>>>>>> a74d3f03cd3e36b2f353433aa8f7defba282b7c8
}
