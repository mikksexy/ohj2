package peliasetusrekisteri;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * - pitää yllä varsinaista profiilirekisteriä eli osaa lisätä ja poistaa profiilin
 * - lukee ja kirjoittaa profiilin tiedostoon
 * - osaa etsiä ja lajitella
 * @author Sami
 * @version 24.7.2020
 *
 */
public class Profiilit implements Iterable<Profiili>{
    private static final int MAX_PROFIILEJA = 5;
    private boolean muutettu = false;
    private int lkm = 0;
    private Profiili[] alkiot = new Profiili[MAX_PROFIILEJA];
    
    
    /**
     * Lisää uuden profiilin tietorakenteeseen. Ottaa profiilin omistukseensa
     * @param profiili lisättävän profiilin viite
     * @example
     * <pre name="test">
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
     * profiilit.lisaa(allu1); profiilit.getLkm() === 6;
     * </pre>
     */
    public void lisaa(Profiili profiili) {
        if (lkm >= alkiot.length) {
            Profiili[] uusi = new Profiili[lkm + 10];
            for ( int i = 0; i < lkm; i++) {
                uusi[i] = alkiot[i];
            }
            alkiot = uusi;
        }
        alkiot[lkm] = profiili;
        lkm++;
        muutettu = true;
    }
    
    
    /**
     * Korvaa profiilin tietorakenteessa. Tarkastetaan löytyykö profiili jo,
     * jos ei niin lisätään uutena profiilina
     * @param profiili
     * TODO: testit
     */
    public void korvaaTaiLisaa(Profiili profiili) {
        int id = profiili.getTunnusNro();
        for ( int i = 0; i < lkm; i++ ) {
            if ( alkiot[i].getTunnusNro() == id ) {
                alkiot[i] = profiili;
                muutettu = true;
                return;
            }
        }
        lisaa(profiili);
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
     * Poistaa jäsenen jolla on valittu tunnusnumero  
     * @param id poistettavan jäsenen tunnusnumero 
     * @return 1 jos poistettiin, 0 jos ei löydy 
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Profiilit profiilit = new Profiilit(); 
     * Profiili allu1 = new Profiili(), allu2 = new Profiili(), allu3 = new Profiili(); 
     * allu1.rekisteroi(); allu2.rekisteroi(); allu3.rekisteroi(); 
     * int id1 = allu1.getTunnusNro(); 
     * profiilit.lisaa(allu1); profiilit.lisaa(allu2); profiilit.lisaa(allu3); 
     * profiilit.poista(id1+1) === 1; 
     * profiilit.annaId(id1+1) === null; profiilit.getLkm() === 2; 
     * profiilit.poista(id1) === 1; profiilit.getLkm() === 1; 
     * profiilit.poista(id1+3) === 0; profiilit.getLkm() === 1; 
     * </pre> 
     *  
     */ 
    public int poista(int id) { 
        int ind = etsiId(id);
        if (ind < 0) return 0;
        lkm--;
        for (int i = ind; i < lkm; i++)
            alkiot[i] = alkiot[i + 1];
        alkiot[lkm] = null;
        muutettu = true;
        return 1;
    } 
    
    
    /**
     * Lukee profiilit tiedostosta. 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * 
     *  Profiilit profiilit = new Profiilit();
     *  Profiili allu1 = new Profiili(), allu2 = new Profiili();
     *  allu1.taytaAlluTiedoilla();
     *  allu2.taytaAlluTiedoilla();
     *  File tied = new File("profiilit.dat");
     *  tied.delete();
     *  profiilit.lueTiedostosta(); #THROWS SailoException
     *  profiilit.lisaa(allu1);
     *  profiilit.lisaa(allu2);
     *  profiilit.tallenna();
     *  profiilit = new Profiilit();  // Poistetaan vanhat luomalla uusi
     *  profiilit.lueTiedostosta();  // johon ladataan tiedot tiedostosta.
     *  Iterator<Profiili> i = profiilit.iterator();
     *  i.next() === allu1;
     *  i.next() === allu2;
     *  i.hasNext() === false;
     *  profiilit.lisaa(allu2);
     *  profiilit.tallenna();
     * </pre>
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta() throws SailoException {
        try ( BufferedReader fi = new BufferedReader(new FileReader("profiilit.dat")) ) {
            String rivi;
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Profiili profiili = new Profiili();
                profiili.parse(rivi);
                lisaa(profiili);
            }
            muutettu = false;
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto profiilit.dat ei aukea" + e.getMessage());
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }

    
    /**
     * Tallentaa profiilit tiedostoon.  
     * Tiedoston muoto:
     * <pre>
     * ; kommenttirivi
     * 2|allu|2|3.3|400|1024x768|4:3|black bars|144
     * 5|Jamppi|2|1.25|800|1440x1080|4:3|stretched|144
     * </pre>
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;
        
        try ( PrintWriter fo = new PrintWriter(new FileWriter("profiilit.dat")) ) {
            for (Profiili profiili : this) {
                fo.println(profiili.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto profiilit.dat ei aukea" + ex);
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston profiilit.dat kirjoittamisessa ongelmia" + ex);
        }

        muutettu = false;
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
    public void setJoukkue(Profiili pro, int jnro) {
        pro.setJoukkue(jnro);
    }
    
    
    /**
     * Palauttaa listan joukkueeseen kuuluvista profiileista
     * @param tunnusNro joukkueen tunnusnumero
     * @return Lista profiileista, jotka kuuluvat joukkueeseen
     */
    public List<Profiili> annaProfiilit(int tunnusNro) {
        List<Profiili> loydetyt = new ArrayList<Profiili>();
        for (Profiili pro : this)
            if (pro.getJoukkue() == tunnusNro) loydetyt.add(pro);
        return loydetyt;
    }
    
    
    /**
     * Luokka jäsenten iteroimiseksi.
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Profiilit profiilit = new Profiilit();
     * Profiili allu1 = new Profiili(), allu2 = new Profiili();
     * allu1.rekisteroi(); allu2.rekisteroi();
     *
     * profiilit.lisaa(allu1); 
     * profiilit.lisaa(allu2); 
     * profiilit.lisaa(allu1); 
     * 
     * StringBuilder ids = new StringBuilder(30);
     * for (Profiili profiili:profiilit)   // Kokeillaan for-silmukan toimintaa
     *   ids.append(" "+profiili.getTunnusNro());           
     * 
     * String tulos = " " + allu1.getTunnusNro() + " " + allu2.getTunnusNro() + " " + allu1.getTunnusNro();
     * 
     * ids.toString() === tulos; 
     * 
     * ids = new StringBuilder(30);
     * for (Iterator<Profiili>  i=profiilit.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
     *   Profiili profiili = i.next();
     *   ids.append(" "+profiili.getTunnusNro());           
     * }
     * 
     * ids.toString() === tulos;
     * 
     * Iterator<Profiili>  i=profiilit.iterator();
     * i.next() == allu1  === true;
     * i.next() == allu2  === true;
     * i.next() == allu1  === true;
     * 
     * i.next();  #THROWS NoSuchElementException
     *  
     * </pre>
     */
    public class ProfiilitIterator implements Iterator<Profiili> {
        private int kohdalla = 0;


        /**
         * Onko olemassa vielä seuraavaa profiilia
         * @see java.util.Iterator#hasNext()
         * @return true jos on vielä profiileja
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }


        /**
         * Annetaan seuraava profiili
         * @return seuraava profiili
         * @throws NoSuchElementException jos seuraava alkiota ei enää ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Profiili next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
            return anna(kohdalla++);
        }


        /**
         * Tuhoamista ei ole toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Me ei poisteta");
        }
    }


    /**
     * Palautetaan iteraattori profiileistaan.
     * @return profiili iteraattori
     */
    @Override
    public Iterator<Profiili> iterator() {
        return new ProfiilitIterator();
    }
    
    
    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien profiilien viitteet 
     * @param hakuehto hakuehto 
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä profiileista 
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     *   Profiilit profiilit = new Profiilit(); 
     *   Profiili pro1= new Profiili(); pro1.parse("1|s1mple|1|3.09"); 
     *   Profiili pro2 = new Profiili(); pro2.parse("2|allu|2|3.3"); 
     *   Profiili pro4 = new Profiili(); pro4.parse("4|ropz|3|1.77"); 
     *   Profiili pro5 = new Profiili(); pro5.parse("5|Jamppi|2|1.25"); 
     *   profiilit.lisaa(pro1); profiilit.lisaa(pro2); profiilit.lisaa(pro4); profiilit.lisaa(pro5);
     *   // TODO: toistaiseksi palauttaa kaikki profiilit
     * </pre> 
     */ 
    @SuppressWarnings("unused")
    public Collection<Profiili> etsi(String hakuehto, int k) { 
        Collection<Profiili> loytyneet = new ArrayList<Profiili>(); 
        for (Profiili profiili : this) { 
            loytyneet.add(profiili);  
        } 
        return loytyneet; 
    }
    
    
    /** 
     * Etsii profiilin id:n perusteella 
     * @param id tunnusnumero, jonka mukaan etsitään 
     * @return löytyneen profiilin indeksi tai -1 jos ei löydy 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Profiilit profiilit = new Profiilit(); 
     * Profiili allu1 = new Profiili(), allu2 = new Profiili(), allu3 = new Profiili(); 
     * allu1.rekisteroi(); allu2.rekisteroi(); allu3.rekisteroi(); 
     * int id1 = allu1.getTunnusNro(); 
     * profiilit.lisaa(allu1); profiilit.lisaa(allu2); profiilit.lisaa(allu3); 
     * profiilit.etsiId(id1+1) === 1; 
     * profiilit.etsiId(id1+2) === 2; 
     * </pre> 
     */ 
    public int etsiId(int id) { 
        for (int i = 0; i < lkm; i++) 
            if (id == alkiot[i].getTunnusNro()) return i; 
        return -1; 
    }
    
    
    /**
     * Laskee kaikkien profiilien eDPI:n keskiarvon
     * @return Palauttaa eDPI keskiarvon
     */
    public double edpiKa() {
        double ka = 0;
        for (Profiili profiili : this) {
            ka += profiili.getEdpi();
        }
        return (ka / lkm);
    }
    
    
    /**
     * Profiilien testausta
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        Profiilit profiilit = new Profiilit();
        Profiili allu = new Profiili(), allu2 = new Profiili();
        allu.rekisteroi();
        allu.taytaAlluTiedoilla();
        allu2.rekisteroi();
        allu2.taytaAlluTiedoilla();
        
        profiilit.lisaa(allu);
        profiilit.lisaa(allu2);
        
        System.out.println("testejä");
        
        for (int i = 0; i < profiilit.getLkm(); i++) {
            Profiili profiili = profiilit.anna(i);
            System.out.println("Profiilin indeksi: " +  i);
            profiili.tulosta(System.out);
        }
    }
}
