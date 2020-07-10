package peliasetusrekisteri;

import java.util.ArrayList;
import java.util.List;

/**
 * - pitää yllä varsinaista profiilirekisteriä eli osaa lisätä ja poistaa profiilin
 * - lukee ja kirjoittaa profiilin tiedostoon
 * - osaa etsiä ja lajitella
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
     * Lisää uuden profiilin tietorakenteeseen. Ottaa profiilin omistukseensa
     * @param profiili lisättävän profiilin viite
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
     * Lukee jäsenistön tiedostosta.  Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "/profiilit.dat";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }

    
    /**
     * Tallentaa jäsenistön tiedostoon.  Kesken.
     * @throws SailoException jos tallennus epäonnistuu
     */
    public void tallenna() throws SailoException {
        throw new SailoException("Ei osata vielä tallentaa tiedostoa " + tiedostonNimi);
    }
    
    
    /**
     * Palauttaa rekisterin profiilien lukumäärän
     * @return profiilien lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    
    /**
     * Asetetaan profiilille joukkuenumero
     * @param pro profiili, jolle joukkuenumero asetetaan
     * @param jnro joukkueen tunnusnumero
     */
    public void asetaJoukkue(Profiili pro, int jnro) {
        pro.asetaJoukkue(jnro);
    }
    
    
    /**
     * Palauttaa listan joukkueeseen kuuluvista profiileista
     * @param tunnusNro joukkueen tunnusnumero
     * @return Lista profiileista, jotka kuuluvat joukkueeseen
     */
    public List<Profiili> annaProfiilit(int tunnusNro) {
        List<Profiili> loydetyt = new ArrayList<Profiili>();
        for (Profiili pro : alkiot)
            if (pro.getJoukkue() == tunnusNro) loydetyt.add(pro);
        return loydetyt;
    }
    
    
    /**
     * @param args Ei käytössä
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
        
            System.out.println("testejä");
        
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
