package peliasetusrekisteri;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import fi.jyu.mit.ohj2.WildChars;

/**
 * - pit‰‰ yll‰ varsinaista joukkuerekisteri‰ eli osaa lis‰t‰ ja poistaa joukkueen
 * - lukee ja kirjoittaa joukkueet tiedostoon
 * - osaa etsi‰ ja lajitella
 * @author Sami
 * @version 31.7.2020
 *
 */
public class Joukkueet implements Iterable<Joukkue> {
    private final List<Joukkue> alkiot = new ArrayList<Joukkue>();
    private boolean muutettu = false;

    
    /**
     * Joukkueiden alustaminen
     */
    public Joukkueet() {
        //
    }
    
    
    /**
     * Lis‰‰ uuden joukkueen tietorakenteeseen.
     * @param jou lis‰tt‰v‰ joukkue
     */
    public void lisaa(Joukkue jou) {
        alkiot.add(jou);
        muutettu = true;
    }
    
    
    /**
     * @param joukkue joka halutaan lis‰t‰
     * @return joukkueen tunnusnumeron
     */
    public int korvaaTaiLisaa(Joukkue joukkue) {
        if ( onkoMuita(joukkue.getNimi()) == 0) lisaa(joukkue);
        return joukkue.getTunnusNro();
    }
    
    
    /**
     * K‰y joukkueet tietorakenteen l‰pi, ja yritt‰‰ etsi‰ saman nimist‰ joukkuetta
     * @param nimi Nimi, jota etsit‰‰n
     * @return Jos ei ole muita palauttaa 0, muuten palauttaa joukkueen numeron
     */
    public int onkoMuita(String nimi) {
        for ( Joukkue jou : alkiot ) {
            if ( jou.getNimi().equalsIgnoreCase(nimi) ) {
                return jou.getTunnusNro();
            }
        }
        return 0;
    }
    
    
    /**
     * K‰y tietorakenteen l‰pi, samalla etsien halutun nimisi‰ joukkueita
     * @param nimi Nimi, jota etsit‰‰n
     * @return Jos ei ole muita palauttaa 0, muuten palauttaa joukkueiden m‰‰r‰n
     */
    public int onkoMonta(String nimi) {
        int lkm = 0;
        for ( Joukkue jou : alkiot ) {
            if ( jou.getNimi().equalsIgnoreCase(nimi) ) {
                lkm++;
            }
        }
        return lkm;
    }
    
    
    /**
     * Poistaa valitun joukkueen
     * @param joukkue poistettava joukkue
     * @return Palauttaa tosi, jos poistettava joukkue lˆytyi
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     *  Joukkueet joukkueet = new Joukkueet();
     *  Joukkue pitsi21 = new Joukkue(); pitsi21.taytaJoukkueTiedoilla();
     *  Joukkue pitsi11 = new Joukkue(); pitsi11.taytaJoukkueTiedoilla();
     *  Joukkue pitsi22 = new Joukkue(); pitsi22.taytaJoukkueTiedoilla();
     *  Joukkue pitsi12 = new Joukkue(); pitsi12.taytaJoukkueTiedoilla();
     *  Joukkue pitsi23 = new Joukkue(); pitsi23.taytaJoukkueTiedoilla();
     *  joukkueet.lisaa(pitsi21);
     *  joukkueet.lisaa(pitsi11);
     *  joukkueet.lisaa(pitsi22);
     *  joukkueet.lisaa(pitsi12);
     *  joukkueet.poista(pitsi23) === false ; joukkueet.getLkm() === 4;
     *  joukkueet.poista(pitsi11) === true;   joukkueet.getLkm() === 3;
     * </pre>

     */
    public boolean poista(Joukkue joukkue) {
        boolean ret = alkiot.remove(joukkue);
        if ( ret ) muutettu = true;
        return ret;
    }
    
    
    /**
     * Lukee joukkueet tiedostosta. 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     *  Joukkueet joukkueet = new Joukkueet();
     *  Joukkue ence1 = new Joukkue();
     *  Joukkue ence2 = new Joukkue();
     *  File tied = new File("joukkueet.dat");
     *  tied.delete();
     *  joukkueet.lueTiedostosta(); #THROWS SailoException
     *  joukkueet.lisaa(ence1);
     *  joukkueet.lisaa(ence2);
     *  joukkueet.tallenna();
     *  joukkueet = new Joukkueet();
     *  joukkueet.lueTiedostosta();
     *  Iterator<Joukkue> i = joukkueet.iterator();
     *  i.next().toString() === ence1.toString();
     *  i.next().toString() === ence2.toString();
     *  i.hasNext() === false;
     *  joukkueet.lisaa(ence1);
     *  joukkueet.tallenna();
     * </pre>
     * @throws SailoException jos lukeminen ep‰onnistuu
     */
    public void lueTiedostosta() throws SailoException {
        try ( BufferedReader fi = new BufferedReader(new FileReader("joukkueet.dat")) ) {
            String rivi;
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Joukkue joukkue = new Joukkue();
                joukkue.parse(rivi);
                lisaa(joukkue);
            }
            muutettu = false;
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto joukkueet.dat ei aukea " + e.getMessage());
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }

    
    /**
     * Tallentaa profiilit tiedostoon.  
     * Tiedoston muoto:
     * <pre>
     * ; kommenttirivi
     * 1|Natus Vincere
     * 2|ENCE
     * </pre>
     * @throws SailoException jos talletus ep‰onnistuu
     */
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;
        
        try ( PrintWriter fo = new PrintWriter(new FileWriter("joukkueet.dat")) ) {
            for (Joukkue joukkue : alkiot) {
                fo.println(joukkue.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto joukkueet.dat ei aukea " + ex);
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston joukkueet.dat kirjoittamisessa ongelmia " + ex);
        }

        muutettu = false;
    }


    /**
     * Palauttaa joukkueiden lukum‰‰r‰n
     * @return joukkueiden lukum‰‰r‰
     */
    public int getLkm() {
        return alkiot.size();
    }


    /**
     * Iteraattori kaikkien joukkueiden l‰pik‰ymiseen
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
     * @return tietorakenne jossa viiteet lˆydettyyn joukkueeseen
     * @example
     * <pre name="test">
     * 
     *  Joukkueet joukkueet = new Joukkueet();
     *  Joukkue pitsi21 = new Joukkue(); joukkueet.lisaa(pitsi21);
     *  Joukkue pitsi11 = new Joukkue(); joukkueet.lisaa(pitsi11);
     * </pre> 
     */
    public Joukkue annaJoukkue(int tunnusnro) {
        Joukkue joukkue = new Joukkue();
        for (Joukkue jou : this)
            if (jou.getTunnusNro() == tunnusnro) {
                joukkue = jou;
            }
            return joukkue;
    }
    
    
    /**
     * Haetaan joukkuet nimen perusteella
     * @param nimi nimi, jolla haetaan
     * @return Palauttaa joukkueen
     */
    public Joukkue annaJoukkue(String nimi) {
        Joukkue joukkue = new Joukkue();
        for (Joukkue jou : this)
            if (jou.getNimi() == nimi) {
                joukkue = jou;
            }
            return joukkue;
    }
    
    
    /**
     * Etsit‰‰n ne joukkueet, jotka sopivat hakuehtoon
     * @param hakuehto merkkijono, jolla joukkuetta haetaan
     * @return Palauttaa listan joukkueita, jotka sopivat hakuehtoon
     */
    public List<Joukkue> etsi(String hakuehto) {
        List<Joukkue> loytyneet = new ArrayList<Joukkue>();

        for (Joukkue joukkue : this) { 
            if ( WildChars.onkoSamat(joukkue.getNimi(), hakuehto) )loytyneet.add(joukkue);  
        };
        return loytyneet; 
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
     * @param args Ei k‰ytˆss‰
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
