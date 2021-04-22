package kanta;

/**
 * @author Teemu Kupiainen, Pauli Koivuniemi
 * @version 22 Apr 2021
 *
 */
public interface Tietue {
    /**
     * @return kenttien lukumäärä
     * @example
     * <pre name="test">
     *   #import rekisteri.Huomio;
     *   Huomio huom = new Huomio();
     *   huom.getKenttia() === 4;
     * </pre>
     */
    public abstract int getKenttia();
    
    /**
     * @return ensimmäinen käyttäjän syötettävän kentän indeksi
     * @example
     * <pre name="test">
     *   Huomio huom = new Huomio();
     *   huom.ensimmainenKentta() === 2;
     * </pre>
     */
    public abstract int ensimmainenKentta();
 
    /**
     * @param k minkä kentän kysymys halutaan
     * @return valitun kentän kysymysteksti
     * @example
     * <pre name="test">
     *   Huomio huom = new Huomio();
     *   huom.getKysymys(3) === "klo";
     * </pre>
     */
    public abstract String getKysymys(int k);
    
    /**
     * @param k Minkä kentän sisältö halutaan
     * @return valitun kentän sisältö
     * @example
     * <pre name="test">
     *   Huomio huom = new Huomio();
     *   huom.parse("   1   |  5  |   Sataa paljon  | 12:00");
     *   huom.anna(0) === "1";   
     *   huom.anna(1) === "5";   
     *   huom.anna(2) === "Sataa paljon";   
     *   huom.anna(3) === "12:00";
     * </pre>
     */
    public abstract String anna(int k);
    
    /**
     * Asetetaan valitun kentän sisältö.  Mikäli asettaminen onnistuu,
     * palautetaan null, muutoin virheteksti.
     * @param k minkä kentän sisältö asetetaan
     * @param s asetettava sisältö merkkijonona
     * @return null jos ok, muuten virheteksti
     * @example
     * <pre name="test">
     *   Huomio huom = new Huomio();
     *   huom.aseta(2,"Sataa vettä") === null;
     *   huom.aseta(3,"1940")  === null;
     * </pre>
     */
    public abstract String aseta(int k, String s);
    
    /**
     * Tehdään identtinen klooni tietueesta
     * @return kloonattu tietue
     * @throws CloneNotSupportedException jos kloonausta ei tueta
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Huomio huom = new Huomio();
     *   huom.parse("   1   |  5  |   Sataa paljon  | 12:00");
     *   Object kopio = huom.clone();
     *   kopio.toString() === huom.toString();
     *   huom.parse("   2   |  15  |   Ei sada  | 11:00");
     *   kopio.toString().equals(huom.toString()) === false;
     *   kopio instanceof Huomio === true;
     * </pre>
     */
    public abstract Tietue clone() throws CloneNotSupportedException;
    

    /**
     * Palauttaa tietueen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return tietue tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Huomio huomio = new Huomio();
     *   huomio.parse("   1   |  5  |   Sataa paljon  | 12:00");
     *   huomio.toString()    =R= "1\\|5\\|Sataa paljon\\|12:00";
     * </pre>
     */

    @Override
    public abstract String toString();
}
