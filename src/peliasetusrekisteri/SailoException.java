package peliasetusrekisteri;

/**
 * Poikkeusluokka tietorakenteesta aiheutuville poikkeuksille
 * @author Sami
 * @version 30.6.2020
 *
 */
public class SailoException extends Exception {
    private static final long serialVersionUID = 1L;
    
    
    /**
     * Poikkeuksen muodostaja jolle tuodaan poikkeuksessa käytettävän viesti
     * @param viesti Poikkeuksen viesti
     */
    public SailoException(String viesti) {
        super(viesti);
    }
}
