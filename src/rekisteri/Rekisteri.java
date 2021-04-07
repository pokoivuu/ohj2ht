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
     * @example
     * <pre name="test">
<<<<<<< HEAD
     * #THROWS SailoException
     * Rekisteri rekisteri = new Rekisteri();
     * Paiva paiva1 = new Paiva(), paiva2 = new Paiva();
     * paiva1.rekisteroi(); paiva2.rekisteroi();
     * rekisteri.getPaivia() === 0;
     * rekisteri.lisaa(paiva1); rekisteri.getPaivia() === 1;
     * rekisteri.lisaa(paiva2); rekisteri.getPaivia() === 2;
     * rekisteri.lisaa(paiva1); rekisteri.getPaivia() === 3;
     * rekisteri.getPaivia() === 3;
     * rekisteri.annaPaiva(0) === paiva1;
     * rekisteri.annaPaiva(1) === paiva2;
     * rekisteri.annaPaiva(2) === paiva1;
     * rekisteri.annaPaiva(3) === paiva1; #THROWS IndexOutOfBoundsException 
     * rekisteri.lisaa(paiva1); rekisteri.getPaivia() === 4;
     * rekisteri.lisaa(paiva1); rekisteri.getPaivia() === 5;
=======
     *  #THROWS SailoException
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
>>>>>>> ffe04a3fe75ac04eea6189ac36e85be011a80ad8
     * </pre>
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
    
<<<<<<< HEAD

=======
    /**
     * Haetaan kaikki huomiot
     * @param paiva päivä jolta huomioita haetaan
     * @return palauttaa tietorakenne jossa viitteet löydettyihin päiviin
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
>>>>>>> ffe04a3fe75ac04eea6189ac36e85be011a80ad8
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

    /**,
     * Pääohjelma rekisterin testausta varten
     * @param args ei käytetä
     */
    public static void main(String[] args) {
        Rekisteri rekisteri = new Rekisteri();
        
        try {
<<<<<<< HEAD

            Paiva paiva1 = new Paiva(), paiva2 = new Paiva();
            paiva1.rekisteroi();
            paiva2.paivanTiedot();
            paiva2.rekisteroi();
            paiva2.paivanTiedot();

            rekisteri.lisaa(paiva1);
            rekisteri.lisaa(paiva2);
            int id1 = paiva1.getTunnusNro();
            int id2 = paiva2.getTunnusNro();
            Huomio huom11 = new Huomio(id1); huom11.vastaaHuomio(id1); rekisteri.lisaa(huom11);
            Huomio huom12 = new Huomio(id1); huom12.vastaaHuomio(id1); rekisteri.lisaa(huom12);
            Huomio huom21 = new Huomio(id2); huom21.vastaaHuomio(id2); rekisteri.lisaa(huom21);
            Huomio huom22 = new Huomio(id2); huom22.vastaaHuomio(id2); rekisteri.lisaa(huom22);
            Huomio huom23 = new Huomio(id2); huom23.vastaaHuomio(id2); rekisteri.lisaa(huom23);

            System.out.println("============= Kerhon testi =================");

=======
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
            
>>>>>>> ffe04a3fe75ac04eea6189ac36e85be011a80ad8
            for (int i = 0; i < rekisteri.getPaivia(); i++) {
                Paiva paiva = rekisteri.annaPaiva(i);
                System.out.println("Päivä paikassa: " + i);
                paiva.tulosta(System.out);
<<<<<<< HEAD
                List<Huomio> loytyneet = rekisteri.annaHuomio(paiva);
                for (Huomio huomio : loytyneet)
                    huomio.tulosta(System.out);
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
=======
                List<Huomio> test = rekisteri.annaHuomio(paiva);
                for(Huomio huom : test) 
                    huom.tulostus(System.out);
                
            }
            
        }  catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
        
>>>>>>> ffe04a3fe75ac04eea6189ac36e85be011a80ad8
    }
    
}
