package kanta;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * @author Teemu Kupiainen, Pauli Koivuniemi
 * @version 22 Apr 2021
 *
 */
public class PvmTarkistus {
    
    
    /**
     * @param pvm tarkistetava päivämäärä
     * @return null jos käy, muuten valittaa
     * @example
     * <pre name="test">
     * #STATICIMPORT
     * #import java.time.LocalDate;
     * #import java.time.format.DateTimeFormatter;
     * #import java.time.format.DateTimeParseException;
     * #import java.time.format.ResolverStyle;
     *  tarkistaPvm("koira") === "Väärä päivämäärämuoto.";
     *  tarkistaPvm("1.25.20") === "Väärä päivämäärämuoto.";
     *  tarkistaPvm("1.1.01") === "Väärä päivämäärämuoto.";
     *  tarkistaPvm("500.1.1990") === "Väärä päivämäärämuoto.";
     *  tarkistaPvm("1.1.50001") === "Väärä päivämäärämuoto.";
     *  tarkistaPvm("1.1.1990") === null; 
     * </pre>
     */
    public String tarkistaPvm(String pvm) {
        boolean sopii = false;
        try {
            LocalDate.parse(pvm, DateTimeFormatter.ofPattern("d.M.uuuu")
                    .withResolverStyle(ResolverStyle.STRICT));
            sopii = true;
        } catch (DateTimeParseException e){
            //e.printStackTrace();
            sopii = false;
        }
        if (sopii == true) return null;      
        return "Väärä päivämäärämuoto.";
    }
    
    /**
     * @param klo kellonaika, joka tuodaan sisään
     * @return null jos ok, muuten valitusviesti
     * @example
     * <pre name="test">
     * #STATICIMPORT
     *  tarkistaKello("koira") === "Ei ole sopiva kellonaika.";
     *  tarkistaKello("00:1") === "Ei ole sopiva kellonaika.";
     *  tarkistaKello("56:23") === "Ei ole sopiva kellonaika.";
     *  tarkistaKello("00:70") === "Ei ole sopiva kellonaika.";
     *  tarkistaKello("21:60") === "Ei ole sopiva kellonaika.";
     *  tarkistaKello("00:00") === null;
     *  tarkistaKello("23:59") === null;
     * </pre>
     */
    public String tarkistaKello(String klo) {
        if (klo == null || "".equals(klo.trim())) return "";
        if (!klo.matches("(((0|1)[0-9])|(2[0-3])):[0-5][0-9]")) { return "Ei ole sopiva kellonaika."; }
        return null;      
    }
}
