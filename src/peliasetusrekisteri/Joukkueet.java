package peliasetusrekisteri;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * - pitää yllä varsinaista joukkuerekisteriä eli osaa lisätä ja poistaa joukkueen
 * - lukee ja kirjoittaa joukkueet tiedostoon
 * - osaa etsiä ja lajitella
 * @author Sami
 * @version 31.7.2020
 *
 */
public class Joukkueet implements Iterable<Joukkue> {
    private final List<Joukkue> alkiot = new ArrayList<Joukkue>();
    private boolean muutettu = false;

    
    /**
     * Ei tarvita vielä
     */
    public Joukkueet() {
        //
    }
    
    
    /**
     * Lisää uuden joukkueen tietorakenteeseen.
     * @param jou lisättävä joukkue
     */
    public void lisaa(Joukkue jou) {
        alkiot.add(jou);
        muutettu = true;
    }
    
    
    /**
     * @param joukkue joka halutaan lisätä
     * @return joukkueen tunnusnumeron
     */
    public int korvaaTaiLisaa(Joukkue joukkue) {
        if ( onkoMuita(joukkue.getNimi()) == 0) lisaa(joukkue);
        return joukkue.getTunnusNro();
    }
    
    
    /**
     * @param nimi
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
     * @param nimi
     * @return Jos ei ole muita palauttaa 0, muuten palauttaa joukkueen profiilien määrän
     */
    public int onkoMuita2(String nimi) {
        int lkm = 0;
        for ( Joukkue jou : alkiot ) {
            if ( jou.getNimi().equalsIgnoreCase(nimi) ) {
                lkm++;
            }
        }
        return lkm;
    }
    
    
    /**
     * TODO: asd
     * @param joukkue poistettava joukkue
     * @return Palauttaa tosi, jos poistettava joukkue löytyi
     */
    public boolean poista(Joukkue joukkue) {
        if ( onkoMuita2(joukkue.getNimi()) > 1 ) return false;
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
     * @throws SailoException jos lukeminen epäonnistuu
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
            throw new SailoException("Tiedosto joukkueet.dat ei aukea" + e.getMessage());
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
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;
        
        try ( PrintWriter fo = new PrintWriter(new FileWriter("joukkueet.dat")) ) {
            for (Joukkue joukkue : alkiot) {
                fo.println(joukkue.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto joukkueet.dat ei aukea" + ex);
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston joukkueet.dat kirjoittamisessa ongelmia" + ex);
        }

        muutettu = false;
    }


    /**
     * Palauttaa joukkueiden lukumäärän
     * @return joukkueiden lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }
    
    
    /**
     * @param roskat
     */
    public void roskat(List<Integer> roskat) {
        for ( Iterator<Joukkue> jou = alkiot.iterator(); jou.hasNext();) {
            boolean roskaa = true;
            for ( Integer roska : roskat ) {
                if ( jou.next().getTunnusNro() == roska.intValue() ) roskaa = false; 
            }
            if ( roskaa ) poista(jou.next());
        }
    }


    /**
     * Iteraattori kaikkien joukkueiden läpikäymiseen
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
     * @return tietorakenne jossa viiteet löydettyyn joukkueeseen
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Joukkueet joukkueet = new Joukkueet();
     *  Joukkue pitsi21 = new Joukkue(); joukkueet.lisaa(pitsi21);
     *  Joukkue pitsi11 = new Joukkue(); joukkueet.lisaa(pitsi11);
     *  joukkueet.annaJoukkue(1) === pitsi21;
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
     * @param nimi
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
     * Haetaan joukkueen tunnusnumero
     * @param jou Joukkue jonka tunnusnumero halutaan
     * @return joukkueen tunnusnumeron
     */
    public int annaJnro(Joukkue jou) {
        return jou.getTunnusNro();
    }
    
    
    /**
     * Testiohjelma luokalle
     * @param args Ei käytössä
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
