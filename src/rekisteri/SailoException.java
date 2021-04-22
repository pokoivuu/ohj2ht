package rekisteri;

/**
 * Poikkeusluokka
 * @author Pauli Koivuniemi ja Teemu Kupiainen
 * @version 22.4.2021
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