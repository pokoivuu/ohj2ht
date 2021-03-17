package rekisteri;

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
     * Palautetaan viite päivään kohdassa i
     * @param i monesko päivään viite
     * @return viite päivään
     * @throws IndexOutOfBoundsException jos i menee alueen ulkopuolelle
     */
    public Paiva anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    /**
     * Uuden päivän lisääminen tietorakenteeseen.
     * @param paiva Päivän viite.
     * @throws SailoException tietorakenteen ollessa täynnä
     * @example
     * <pre name="test">
     *  #THROWS SailoException
     *  Paivat paivat = new Paivat();
     *  Paiva maanantai = new Paiva(), tiistai = new Paiva();
     *  paivat.getLkm() === 0;
     *  paivat.lisaa(maanantai); paivat.getLkm() === 1;
     *  paivat.lisaa(tiistai); paivat.getLkm() === 2;
     *  paivat.lisaa(maanantai); paivat.getLkm() === 3;
     *  
     *  paivat.anna(0) === maanantai;
     *  paivat.anna(1) === tiistai;
     *  paivat.anna(2) === maanantai;
     *  paivat.anna(1) == maanantai === false;
     *  paivat.anna(2) == tiistai === false;
     *  paivat.anna(3) === maanantai; #THROWS IndexOutOfBoundsException
     *  paivat.lisaa(maanantai); paivat.getLkm() === 4;
     *  paivat.lisaa(maanantai); paivat.getLkm() === 5;
     *  paivat.lisaa(maanantai); paivat.getLkm() === 6;
     *  paivat.lisaa(maanantai); paivat.getLkm() === 7;
     *  paivat.lisaa(maanantai); paivat.getLkm() === 8;
     *  paivat.lisaa(maanantai); paivat.getLkm() === 9;
     *  paivat.lisaa(maanantai); paivat.getLkm() === 10;
     *  paivat.lisaa(tiistai); #THROWS SailoException
     * </pre>
     */
    public void lisaa (Paiva paiva) throws SailoException {
        if (lkm >= alkiot.length) throw new SailoException("Tietorakenne täynnä");
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
        Paivat paivat = new Paivat();
        
        Paiva maanantai = new Paiva(), tiistai = new Paiva();
        maanantai.rekisteroi();
        maanantai.paivanTiedot();
        tiistai.rekisteroi();
        tiistai.paivanTiedot();
        
        try {
            paivat.lisaa(maanantai);
            paivat.lisaa(tiistai);
            
            for (int i = 0; i < paivat.getLkm(); i++) {
                Paiva paiva = paivat.anna(i);
                System.out.println("Päivä nro: " + i);
                paiva.tulosta(System.out);
            }
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
