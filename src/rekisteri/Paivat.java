package rekisteri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import fi.jyu.mit.ohj2.WildChars;


/**
 * @author Pauli Koivuniemi ja Teemu Kupiainen
 * @version 22.4.2021
 *
 */
public class Paivat implements Iterable<Paiva> {
    private static final int MAX_PAIVAT = 15;
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
    protected Paiva anna(int i) throws IndexOutOfBoundsException {
        if ( i < 0 || lkm <= i ) 
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
     *  Iterator<Paiva> it = paivat.iterator();
     *  it.next() === maanantai;
     *  it.next() === tiistai;
     *  it.next() === maanantai;
     *  paivat.lisaa(maanantai); paivat.getLkm() === 4;
     *  paivat.lisaa(maanantai); paivat.getLkm() === 5;
     * </pre>
     */
    public void lisaa (Paiva paiva) throws SailoException {
        if (lkm >= alkiot.length) alkiot = Arrays.copyOf(alkiot, lkm +20);
        alkiot[lkm] = paiva;
        lkm++;
        muutos = true;
    }
    
    /**
     * Korvaa päivän tietorakenteessa.
     * Etsitään samalla tunnusnumerolla oleva päivä.  Jos ei löydy,
     * niin lisätään uutena päivänä.
     * @param paiva lisättävän päivän viite
     * @throws SailoException jos tietorakenne täynnä
     * <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * Paivat paivat = new Paivat();
     * Paiva maa1 = new Paiva(), tii1 = new Paiva();
     * maa1.rekisteroi(); tii1.rekisteroi();
     * paivat.getLkm() === 0;
     * paivat.korvaaLisaa(maa1); paivat.getLkm() === 1;
     * paivat.korvaaLisaa(tii1); paivat.getLkm() === 2;
     * Paiva maa2 = maa1.clone();
     * maa2.aseta(3,"kkk");
     * Iterator<Paiva> it = paivat.iterator();
     * it.next() == maa1 === true;
     * paivat.korvaaLisaa(maa2); paivat.getLkm() === 2;
     * it = paivat.iterator();
     * Paiva p0 = it.next();
     * p0 === maa2;
     * p0 == maa2 === true;
     * p0 == maa1 === false;
     * </pre>

     */
    public void korvaaLisaa(Paiva paiva) throws SailoException {
        int id = paiva.getTunnusNro();
        for (int i = 0; i < lkm; i++) {
            if ( alkiot[i].getTunnusNro() == id ) {
                alkiot[i] = paiva;
                muutos = true;
                return;
            }
        }
        lisaa(paiva);
    }

    /**
    * Poistaa päivän jolla on valittu tunnusnumero  
     * @param id poistettavan päivän tunnusnumero 
     * @return 1 jos poistettiin, 0 jos ei löydy 
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Paivat paivat = new Paivat(); 
     * Paiva maa1 = new Paiva(), maa2 = new Paiva(), maa3 = new Paiva(); 
     * maa1.rekisteroi(); maa2.rekisteroi(); maa3.rekisteroi(); 
     * int id1 = maa1.getTunnusNro(); 
     * paivat.lisaa(maa1); paivat.lisaa(maa2); paivat.lisaa(maa3); 
     * paivat.poista(id1+1) === 1; 
     * paivat.annaId(id1+1) === null; paivat.getLkm() === 2; 
     * paivat.poista(id1) === 1; paivat.getLkm() === 1; 
     * paivat.poista(id1+3) === 0; paivat.getLkm() === 1; 
     * </pre> 

     */
    public int poista(int id) {
        int ind = etsiId(id); 
        if (ind < 0) return 0; 
        lkm--; 
        for (int i = ind; i < lkm; i++) 
            alkiot[i] = alkiot[i + 1]; 
        alkiot[lkm] = null; 
        muutos = true; 
        return 1; 

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
    
    /**
     * Iteraattori luokka päiville
     * @author pokoivuu & teemukupiainen
     * @version 8.4.2021
     *
     */
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
    
    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien päivien viitteet 
     * @param hakuehto hakuehto 
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä päivistä
     * @example 
     * <pre name="test"> 
     * #import java.util.List;
     * #THROWS SailoException  
     *   Paivat paivat = new Paivat(); 
     *   Paiva paiva1 = new Paiva(); paiva1.parse("1|Lempäälä|10:00|20 C|"); 
     *   Paiva paiva2 = new Paiva(); paiva2.parse("2|Vihti||11:00|"); 
     *   Paiva paiva3 = new Paiva(); paiva3.parse("3|Paltamo|12:00||131313|19 C"); 
     *   Paiva paiva4 = new Paiva(); paiva4.parse("4|Jyväskylä|13:00|5 C"); 
     *   Paiva paiva5 = new Paiva(); paiva5.parse("5|Puuhamaa|14:00|15 C"); 
     *   #THROWS SailoException
     *   paivat.lisaa(paiva1); paivat.lisaa(paiva2); paivat.lisaa(paiva3); paivat.lisaa(paiva4); paivat.lisaa(paiva5);
     *   List<Paiva> loytyneet;  
     *   loytyneet = (List<Paiva>)paivat.etsi("*l*",1);  
     *   loytyneet.size() === 3; 
     *   loytyneet.get(0) == paiva4 === true;  
     *   loytyneet.get(1) == paiva1 === true;
     *   loytyneet.get(2) == paiva3 === true;
     *     
     *   loytyneet = (List<Paiva>)paivat.etsi("*Pal*",1);  
     *   loytyneet.size() === 1;  
     *   loytyneet.get(0) == paiva3 === true;  
     *   
     *     
     *   loytyneet = (List<Paiva>)paivat.etsi(null,-1);  
     *   loytyneet.size() === 5;  
     * </pre>
     */
    public Collection<Paiva> etsi(String hakuehto, int k) { 
        String ehto = "*";
        if ( hakuehto != null && hakuehto.length() > 0 ) ehto = hakuehto; 
        int hk = k; 
        if ( hk < 0 ) hk = 0;
        List<Paiva> loytyneet = new ArrayList<Paiva>(); 
        for (Paiva paiva : this) { 
            if (WildChars.onkoSamat(paiva.anna(hk), ehto)) loytyneet.add(paiva);   
        } 
        Collections.sort(loytyneet, new Paiva.Vertailija(hk)); 
        return loytyneet; 
    }
    
    /** 
     * Etsii päivän id:n perusteella 
     * @param id tunnusnumero, jonka mukaan etsitään 
     * @return päivä jolla etsittävä id tai null 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Paivat paivat = new Paivat(); 
     * Paiva maa1 = new Paiva(), maa2 = new Paiva(), maa3 = new Paiva(); 
     * maa1.rekisteroi(); maa2.rekisteroi(); maa3.rekisteroi(); 
     * int id1 = maa1.getTunnusNro(); 
     * paivat.lisaa(maa1); paivat.lisaa(maa2); paivat.lisaa(maa3); 
     * paivat.annaId(id1  ) == maa1 === true; 
     * paivat.annaId(id1+1) == maa2 === true; 
     * paivat.annaId(id1+2) == maa3 === true; 
     * </pre> 
     */ 
    public Paiva annaId(int id) { 
        for (Paiva paiva : this) { 
            if (id == paiva.getTunnusNro()) return paiva; 
        } 
        return null; 
    } 

    
    /**
     * Etsii Päivän id:n perusteella 
     * @param id tunnusnumero, jonka mukaan etsitään 
     * @return löytyneen päivän indeksi tai -1 jos ei löydy 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Paivat paivat = new Paivat(); 
     * Paiva maa1 = new Paiva(), maa2 = new Paiva(), maa3 = new Paiva(); 
     * maa1.rekisteroi(); maa2.rekisteroi(); maa3.rekisteroi(); 
     * int id1 = maa1.getTunnusNro(); 
     * paivat.lisaa(maa1); paivat.lisaa(maa2); paivat.lisaa(maa3); 
     * paivat.etsiId(id1+1) === 1; 
     * paivat.etsiId(id1+2) === 2; 
     * </pre> 
     */
    public int etsiId(int id) {
        for (int i = 0; i < lkm; i++)
            if (id == alkiot[i].getTunnusNro()) return i;
        return -1;
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
            int i = 0;
            for (Paiva paiva: paivat) {              
                System.out.println("Päivä nro: " + i++);
                paiva.tulosta(System.out);
            }
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
