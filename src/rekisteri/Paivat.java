package Rekisteri;

/**
 * @author Teemu Kupiainen, Pauli Koivuniemi
 * @version 25 Feb 2021
 *
 */
public class Paivat {
    private static final int MAX_PAIVAT = 10;
    private int lkm = 0;
    private String paivatTiedosto ="";
    private Paiva alkiot[] = new Paiva[MAX_PAIVAT];
    
    
    /**
     * Tyhjä konstruktori
     */
    public Paivat() {
        //Tyhjä konstruktori
    }
    
    
    /**
     * Uuden päivän lisääminen tietorakenteeseen.
     * @param paiva Päivän viite.
     * @throws SailoException tietorakenteen ollessa täynnä
     */
    public void lisaa (Paiva paiva) throws SailoException {
        if (lkm >= alkiot.length) throw new SailoException("Taulukko täynnä");
        alkiot[lkm] = paiva;
        lkm++;
    }
    
    
    /**
     * Viite paivaan indeksissä i, alkiot[i]-taulukossa
     * @param i monesko viite haetaan
     * @return palautetaan se viite päivään, jonka indeksi on i
     * @throws IndexOutOfBoundsException kun i ei ole sallitulla alueella
     */
    public Paiva viite(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Kelvoton indeksi: " + i);
        return alkiot[i];
    }
    
    
    /**
     * Lukee päivät tiedostosta saatiedot.dat
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException lukeminen epäonnistuu
     */
    public void lueTiedosto(String hakemisto) throws SailoException {
        paivatTiedosto = hakemisto + "/saatiedot.dat";
        throw new SailoException("Ei osata lukea" + paivatTiedosto);       
    }
    
    
    /**
     * Päivien tallennus tiedostoon.
     * @throws SailoException tallennus epäonnistuu
     */
    public void tallennus() throws SailoException {
        throw new SailoException("Ei osata vielä tallentaa " + paivatTiedosto);
    }
    
    
    /**
     * Getteri päivien lukumäärälle
     * @return päivien lukumäärän
     */
    public int getLkm() {
        return lkm;
    }
    
    
    /**
     * Tuleva testiohjelma
     * @param args ei käytössä
     */
    public static void main (String args[]) {
        // ei vielä toteutusta
    }
}
