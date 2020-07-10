package peliasetusrekisteri;

import java.util.*;

/**
 * - pit�� yll� varsinaista joukkuerekisteri� eli osaa lis�t� ja poistaa joukkueen
 * - lukee ja kirjoittaa joukkueet tiedostoon
 * - osaa etsi� ja lajitella
 * @author Sami
 * @version 8.7.2020
 *
 */
public class Joukkueet implements Iterable<Joukkue>{
    private final Collection<Joukkue> alkiot = new ArrayList<Joukkue>();
    
    private String tiedostonNimi = "";

    
    /**
     * Ei tarvita viel�
     */
    public Joukkueet() {
        //
    }
    
    
    /**
     * Lis�� uuden joukkueen tietorakenteeseen.
     * @param jou lis�tt�v� joukkue
     */
    public void lisaa(Joukkue jou) {
        alkiot.add(jou);
    }
    
    
    /**
     * Lukee j�senist�n tiedostosta.  
     * TODO Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen ep�onnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + ".jou";
        throw new SailoException("Ei osata viel� lukea tiedostoa " + tiedostonNimi);
    }


    /**
     * Tallentaa joukkueet tiedostoon.  
     * TODO Kesken.
     * @throws SailoException jos tallennus ep�onnistuu
     */
    public void tallenna() throws SailoException {
        throw new SailoException("Ei osata viel� tallentaa tiedostoa " + tiedostonNimi);
    }


    /**
     * Palauttaa joukkueiden lukum��r�n
     * @return joukkueiden lukum��r�
     */
    public int getLkm() {
        return alkiot.size();
    }


    /**
     * Iteraattori kaikkien joukkueiden l�pik�ymiseen
     * @return joukkueiteraattori
     * 
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     *  Joukkueet joukkueet = new Joukkueet();
     *  Joukkue pitsi21 = new Joukkue(); joukkueet.lisaa(pitsi21);
     *  Joukkue pitsi11 = new Joukkue(); joukkueet.lisaa(pitsi11);
     *  Joukkue pitsi22 = new Joukkue(); joukkueet.lisaa(pitsi22);
     *  Joukkue pitsi12 = new Joukkue(); joukkueet.lisaa(pitsi12);
     *  Joukkue pitsi23 = new Joukkue(); joukkueet.lisaa(pitsi23);
     * 
     *  Iterator<Joukkue> i2=joukkueet.iterator();
     *  i2.next() === pitsi21;
     *  i2.next() === pitsi11;
     *  i2.next() === pitsi22;
     *  i2.next() === pitsi12;
     *  i2.next() === pitsi23;
     *  i2.next() === pitsi12;  #THROWS NoSuchElementException  
     *  
     * </pre>
     */
    @Override
    public Iterator<Joukkue> iterator() {
        return alkiot.iterator();
    }
    
    
    /**
     * Haetaan profiilin joukkue
     * @param tunnusnro profiilin tunnusnumero jolle joukkuetta haetaan
     * @return tietorakenne jossa viiteet l�ydettyyn joukkueeseen
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Joukkueet joukkueet = new Joukkueet();
     *  Joukkue pitsi21 = new Joukkue("pitsi21"); joukkueet.lisaa(pitsi21);
     *  Joukkue pitsi11 = new Joukkue(); joukkueet.lisaa(pitsi11);
     *  joukkueet.annaJoukkue(1) === pitsi21;
     * </pre> 
     */
    public Joukkue annaJoukkue(int tunnusnro) {
        Joukkue joukkue = new Joukkue();
        for (Joukkue jou : alkiot)
            if (jou.getTunnusNro() == tunnusnro) joukkue = jou;
            return joukkue;
    }

    
    /**
     * Haetaan joukkueen tunnusnumero
     * @param jou Joukkue jonka tunnusnumero halutaan
     * @return joukkueen tunnusnumeron
     */
    public int annaJnro(Joukkue jou) {
        return jou.getTunnusNro();
    }
    
    /**
     * Testiohjelma luokalle
     * @param args Ei k�yt�ss�
     */
    public static void main(String[] args) {
        Joukkueet joukkueet = new Joukkueet();
        Joukkue pitsi1 = new Joukkue();
        pitsi1.taytaJoukkueTiedoilla();
        Joukkue pitsi2 = new Joukkue();
        pitsi1.taytaJoukkueTiedoilla();
        Joukkue pitsi3 = new Joukkue();
        pitsi1.taytaJoukkueTiedoilla();
        Joukkue pitsi4 = new Joukkue();
        pitsi1.taytaJoukkueTiedoilla();

        joukkueet.lisaa(pitsi1);
        joukkueet.lisaa(pitsi2);
        joukkueet.lisaa(pitsi3);
        joukkueet.lisaa(pitsi2);
        joukkueet.lisaa(pitsi4);
        
        System.out.println("============= Joukkueet testi =================");

        
        

    }

}
