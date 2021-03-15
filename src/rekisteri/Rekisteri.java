package rekisteri;

import java.util.List;

/**
 * Rekisteriluokka
 * @author Teemu Kupiainen, Pauli Koivuniemi
 * @version 11 Mar 2021
 *
 */
public class Rekisteri {
    private final Huomiot huomiot = new Huomiot();
    private final Paivat paivat = new Paivat();
    
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
     */
    public void lisaa(Paiva paiva) throws SailoException {
        paivat.lisaa(paiva);
    }
    
    /**
     * Lisää rekisteriin uuden huomion
     * @param h lisättävä huomio
     */
    public void lisaa(Huomio h) {
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
     */
    public List<Huomio> annaHuomio(Paiva paiva)  {
        return huomiot.annaHuomio(paiva.getTunnusNro());      
    }
    
    /**
     * Lukee rekisterin tiedot tiedostosta
     * @param tiedostonNimi nimi jota käytetään
     * @throws SailoException mikäli luku epäonnistuu
     */
    public void lueTiedosto(String tiedostonNimi) throws SailoException {
        paivat.lueTiedosto(tiedostonNimi);
        huomiot.lueTiedosto(tiedostonNimi);
    }
    
    /**
     * Tallentaa rekisterin tiedot tiedostoon
     * @throws SailoException jos tallentaminen ei onnistu
     */
    public void talleta() throws SailoException {
        paivat.tallennus();
        huomiot.tallennus();        
    }

    /**
     * Pääohjelma rekisterin testausta varten
     * @param args ei käytetä
     */
    public static void main(String[] args) {
        //
    }
    
}
