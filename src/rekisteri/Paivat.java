package rekisteri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Teemu Kupiainen, Pauli Koivuniemi
 * @version 25 Feb 2021
 *
 */
public class Paivat implements Iterable<Paiva> {
    private static final int MAX_PAIVAT = 10;
    private int lkm = 0;
    //private String paivatTiedosto ="";
    private Paiva alkiot[] = new Paiva[MAX_PAIVAT];
    
    private String tiedostonPerusNimi = "paivat";
    private boolean muutos = false;
    private String paivatTiedosto = "";
    
    
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
        muutos = true;
    }
    
    /**
     * @param tiedosto tiedoston nimi
     * @throws SailoException jos tulee virhe
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * #import java.util.Iterator;
     * 
     *  Paivat paivat = new Paivat();
     *  Paiva maanantai1 = new Paiva(), maanantai2 = new Paiva();
     *  maanantai1.paivanTiedot();
     *  maanantai2.paivanTiedot();
     *  String hakemisto = "testirekisteri";
     *  String tiedostoNimi = hakemisto+"/paivat";
     *  File ftied = new File(tiedostoNimi+".dat");
     *  File dir = new File(hakemisto);
     *  dir.mkdir();
     *  ftied.delete();
     *  paivat.lueTiedostosta(tiedostoNimi); #THROWS SailoException
     *  paivat.lisaa(maanantai1);
     *  paivat.lisaa(maanantai2);
     *  paivat.tallenna();
     *  paivat = new Paivat();            // Poistetaan vanhat luomalla uusi
     *  paivat.lueTiedostosta(tiedostoNimi);  // johon ladataan tiedot tiedostosta.
     *  Iterator<Paiva> i = paivat.iterator();
     *  i.next() === maanantai1;
     *  i.next() === maanantai2;
     *  i.hasNext() === false;
     *  paivat.lisaa(maanantai2);
     *  paivat.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedostoNimi+".bak");
     *  fbak.delete() === true;
     *  dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tiedosto) throws SailoException {
        setTiedostonPerusNimi(tiedosto);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            paivatTiedosto = fi.readLine();
            if ( paivatTiedosto == null ) throw new SailoException("Rekisterin nimi puuttuu");
            String rivi = fi.readLine();
            if ( rivi == null ) throw new SailoException("Maksimikoko puuttuu");

            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Paiva paiva = new Paiva();
                paiva.parse(rivi);
                lisaa(paiva);
            }
            muutos = false;
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }
    
    /**
     * Luetaan aikaisemmin annetun nimisestä tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }
    
    /**
     * Tallentaa päivät tiedostoon
     * @throws SailoException Jos epäonnistuu
     */
    public void tallenna() throws SailoException {
        if ( !muutos ) return;

        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); // if .. System.err.println("Ei voi tuhota");
        ftied.renameTo(fbak); // if .. System.err.println("Ei voi nimetä");

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            fo.println(getPaivatTiedosto());
            fo.println(alkiot.length);
            for (Paiva paiva : this) {
                fo.println(paiva.toString());
            }
            //} catch ( IOException e ) { // ei heitä poikkeusta
            //  throw new SailoException("Tallettamisessa ongelmia: " + e.getMessage());
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutos = false;
    }
    
    /**
     * Asettaa tiedostolle perusnimen
     * @param tied nimi jota hakutaan käyttää
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied;
    }
    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }
    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return tiedostonPerusNimi + ".dat";
    }
    
    /**
         * Palauttaa varakopiotiedoston nimen
         * @return varakopiotiedoston nimi
         */
        public String getBakNimi() {
            return tiedostonPerusNimi + ".bak";
        }
        
        /**
         * Palauttaa Rekisterin koko nimen
         * @return Rekisterin koko nimi merkkijononna
         */
        public String getPaivatTiedosto() {
            return paivatTiedosto;
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
     * Getteri päivien lukumäärälle
     * @return päivien lukumäärän
     */
    public int getLkm() {
        return lkm;
    }
    
    public class PaivatIterator implements Iterator<Paiva> {
        private int kohdalla = 0;


        /**
         * Onko olemassa vielä seuraavaa päivää
         * @see java.util.Iterator#hasNext()
         * @return true jos on vielä päiviä
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }


        /**
         * Annetaan seuraava päivä
         * @return seuraava päivä
         * @throws NoSuchElementException jos seuraava alkiota ei enää ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Paiva next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
            return anna(kohdalla++);
        }


        /**
         * Tuhoamista ei ole toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Me ei poisteta");
        }
    }


    /**
     * Palautetaan iteraattori päivistään.
     * @return päivä iteraattori
     */
    @Override
    public Iterator<Paiva> iterator() {
        return new PaivatIterator();
    }
    
    @SuppressWarnings("unused")
    public Collection<Paiva> etsi(String hakuehto, int k) { 
        Collection<Paiva> loytyneet = new ArrayList<Paiva>(); 
        for (Paiva paiva : this) { 
            loytyneet.add(paiva);  
        } 
        return loytyneet; 
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
