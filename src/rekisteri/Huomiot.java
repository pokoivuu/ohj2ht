package rekisteri;



    

import java.io.*;
import java.util.*;

/**
 * Huomiot-luokka, jossa lisätään esim. uusi huomio
 * Luetaan tiedosto, johon tallennetaan
 * Käyttää iterable-rajapintaa
 * @author Teemu Kupiainen, Pauli Koivuniemi
 * @version 11 Mar 2021
 *
 */
public class Huomiot implements Iterable<Huomio> {

    private String tiedostonPerusNimi ="";
    private boolean muutos = false;
    
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
        muutos = true;
    }
    
    
    /**
     * Lukee tiedoston //(KESKEN)
     * @param tiedosto tiedosto
     * @throws SailoException Jos lukeminen ei onnistu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     *  Huomiot huomioita = new Huomiot();
     *  Huomio sataa2 = new Huomio(); sataa2.testiHuomio(2);
     *  Huomio sataa1 = new Huomio(); sataa1.testiHuomio(1);
     *  Huomio sataa21 = new Huomio(); sataa21.testiHuomio(2); 
     *  Huomio sataa12 = new Huomio(); sataa12.testiHuomio(1); 
     *  Huomio sataa22 = new Huomio(); sataa22.testiHuomio(2); 
     *  String tiedostoNimi = "testirekisteri";
     *  File ftied = new File(tiedostoNimi+".dat");
     *  ftied.delete();
     *  huomioita.lueTiedosto(tiedostoNimi); #THROWS SailoException
     *  huomioita.lisaa(sataa2);
     *  huomioita.lisaa(sataa1);
     *  huomioita.lisaa(sataa21);
     *  huomioita.lisaa(sataa12);
     *  huomioita.lisaa(sataa22);
     *  huomioita.tallennus();
     *  huomioita = new Huomiot();
     *  huomioita.lueTiedosto(tiedostoNimi);
     *  Iterator<Huomio> i = huomioita.iterator();
     *  i.next().toString() === sataa2.toString();
     *  i.next().toString() === sataa1.toString();
     *  i.next().toString() === sataa21.toString();
     *  i.next().toString() === sataa12.toString();
     *  i.next().toString() === sataa22.toString();
     *  i.hasNext() === false;
     *  huomioita.lisaa(sataa22);
     *  huomioita.tallennus();
     *  ftied.delete() === true;
     *  File fback = new File(tiedostoNimi+".bak");
     *  fback.delete() === true;
     * </pre>
     */
    public void lueTiedosto(String tiedosto) throws SailoException {
        setTiedostonPerusNimi(tiedosto);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {

            String rivi;
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Huomio huom = new Huomio();
                huom.parse(rivi);
                lisaa(huom);
            }
            muutos = false;

        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei auennut");
        } catch ( IOException e ) {
            throw new SailoException("Ongelma tiedoston kanssa: " + e.getMessage());
        }
    }
    
    /**
     * Luetaan annetun nimisestä tiedostosta
     * @throws SailoException Jos ei onnistu
     */
    public void lueTiedosto() throws SailoException {
        lueTiedosto(getTiedostonPerusNimi());
    }

    
    
    /**
     * Tallentaa tiedostoon
     * @throws SailoException Jos epäonnistuu tallennuksessa
     */
    public void tallennus() throws SailoException {
        if (!muutos) return;
        
        File fbak = new File(getBakNimi());
        File ftied = new File (getTiedostonNimi());
        fbak.delete();
        ftied.renameTo(fbak);
        
        try (PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ){
            for (Huomio huom : this) {
                fo.println(huom.toString());
            }
        } catch (FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch (IOException ex) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }
        muutos = false;
    }
    
    
    /**
     * Palauttaa rekisterin huomioiden lukumäärän
     * @return Taulukon alkioiden lukumäärän eli huomioiden määrä
     */
    public int getLkm() {
        return taulukko.size();
    }
    
    /**
     * Asettaa tiedoston nimen
     * @param tiedosto tiedoston nimi
     */
    public void setTiedostonPerusNimi(String tiedosto) {
        tiedostonPerusNimi = tiedosto;
    }
    
    /**
     * Palauttaa tiedoston nimen, jota tallennetaan
     * @return tiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }
    
    /**
     * Palauttaa tiedoston nimen, jota tallennetaan
     * @return tiedoston nimi
     */
    public String getTiedostonNimi() {
        return tiedostonPerusNimi + ".dat";
    }
    
    /**
     * Palauttaa backupin nimen
     * @return backupin nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }
    
    /**
     * Iteraattori kaikkien huomioiden läpikäymiseen
     * @example
     * <pre name="test">
     *  #PACKAGEIMPORT
     *  #import java.util.*;
     *  //Luodaan Huomiot ja lisätään huomioita
     *  Huomiot huomioita = new Huomiot();
     *  Huomio sataa1 = new Huomio(1); huomioita.lisaa(sataa1);
     *  Huomio sataa2 = new Huomio(2); huomioita.lisaa(sataa2);
     *  Huomio sataa12 = new Huomio(1); huomioita.lisaa(sataa12);
     *  Huomio sataa21 = new Huomio(2); huomioita.lisaa(sataa21);
     *  Huomio sataa13 = new Huomio(1); huomioita.lisaa(sataa13);
     *  
     *  //Iteraattorilla käydään läpi
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
     * Haetaan kaikki huomiot
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
