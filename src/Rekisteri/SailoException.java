package Rekisteri;

/**
 * Poikkeusluokka
 * @author Teemu Kupiainen, Pauli Koivuniemi
 * @version 25 Feb 2021
 *
 */
public class SailoException extends Exception {
    private static final long serialVersionUID = 1L;
    
    
    /**
     * Muodostaja poikkeukselle
     * @param viesti Viesti, jonka poikkeus näyttää
     */
    public SailoException (String viesti) {
        super(viesti);
    }
}
