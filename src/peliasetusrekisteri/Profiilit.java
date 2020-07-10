package peliasetusrekisteri;

/**
 * - pit‰‰ yll‰ varsinaista profiilirekisteri‰ eli osaa lis‰t‰ ja poistaa profiilin
 * - lukee ja kirjoittaa profiilin tiedostoon
 * - osaa etsi‰ ja lajitella
 * @author Sami
 * @version 30.6.2020
 *
 */
public class Profiilit {
    private static final int MAX_PROFIILEJA = 5;
    private int lkm = 0;
    private String tiedostonNimi = "";
    private Profiili[] alkiot = new Profiili[MAX_PROFIILEJA];
    
    
    /**
     * Lis‰‰ uuden profiilin tietorakenteeseen. Ottaa profiilin omistukseensa
     * @param profiili lis‰tt‰v‰n profiilin viite
     * @throws SailoException jos ei mahdu
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Profiilit profiilit = new Profiilit();
     * Profiili allu1 = new Profiili(), allu2 = new Profiili();
     * profiilit.getLkm() === 0;
     * profiilit.lisaa(allu1); profiilit.getLkm() === 1;
     * profiilit.lisaa(allu2); profiilit.getLkm() === 2;
     * profiilit.lisaa(allu1); profiilit.getLkm() === 3;
     * profiilit.anna(0) === allu1;
     * profiilit.anna(1) === allu2;
     * profiilit.anna(2) === allu1;
     * profiilit.anna(1) == allu1 === false;
     * profiilit.anna(1) == allu2 === true;
     * profiilit.anna(3) === allu1; #THROWS IndexOutOfBoundsException
     * profiilit.lisaa(allu1); profiilit.getLkm() === 4;
     * profiilit.lisaa(allu1); profiilit.getLkm() === 5;
     * profiilit.lisaa(allu1); #THROWS SailoException
     * </pre>
     */
    public void lisaa(Profiili profiili) throws SailoException {
        if (lkm >= alkiot.length) throw new SailoException("Liikaa alkioita");
        alkiot[lkm] = profiili;
        lkm++;
    }
    
    
    /**
     * Palauttaa viitteen i:teen profiiliin.
     * @param i monennenko profiilin viite halutaan
     * @return viite profiiliin, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public Profiili anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i) 
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    
    /**
     * Lukee j‰senistˆn tiedostosta.  Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen ep‰onnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "/profiilit.dat";
        throw new SailoException("Ei osata viel‰ lukea tiedostoa " + tiedostonNimi);
    }

    
    /**
     * Tallentaa j‰senistˆn tiedostoon.  Kesken.
     * @throws SailoException jos tallennus ep‰onnistuu
     */
    public void tallenna() throws SailoException {
        throw new SailoException("Ei osata viel‰ tallentaa tiedostoa " + tiedostonNimi);
    }
    
    
    /**
     * Palauttaa rekisterin profiilien lukum‰‰r‰n
     * @return profiilien lukum‰‰r‰
     */
    public int getLkm() {
        return lkm;
    }
    
    /**
     * @param args Ei k‰ytˆss‰
     */
    public static void main(String[] args) {
        Profiilit profiilit = new Profiilit();
        Profiili allu = new Profiili(), allu2 = new Profiili();
        allu.rekisteroi();
        allu.taytaAlluTiedoilla();
        allu2.rekisteroi();
        allu2.taytaAlluTiedoilla();
        
        try {
            profiilit.lisaa(allu);
            profiilit.lisaa(allu2);
        
            System.out.println("testej‰");
        
            for (int i = 0; i < profiilit.getLkm(); i++) {
                Profiili profiili = profiilit.anna(i);
                System.out.println("Profiilin indeksi: " +  i);
                profiili.tulosta(System.out);
            }
        } catch (SailoException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
