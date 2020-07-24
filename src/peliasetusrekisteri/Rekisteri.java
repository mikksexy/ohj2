package peliasetusrekisteri;

import java.util.Collection;
import java.util.List;

/**
 * - huolehtii profiilit ja joukkueet -luokkien v‰lisest‰ yhteistyˆst‰ ja v‰litt‰‰ n‰it‰ tietoja pyydett‰ess‰
 * - lukee ja kirjoittaa rekisterin tiedostoon pyyt‰m‰ll‰ apua avustajiltaan
 * @author Sami
 * @version 24.7.2020
 *
 */
public class Rekisteri {
    
    private Profiilit profiilit = new Profiilit();
    private Joukkueet joukkueet = new Joukkueet();
    
    /**
     * Lis‰t‰‰n uusi profiili rekisteriin
     * @param profiili lis‰tt‰v‰ profiili
     * @throws SailoException jos ei mahdu
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Rekisteri rekisteri = new Rekisteri();
     * Profiili allu1 = new Profiili(), allu2 = new Profiili();
     * allu1.rekisteroi(); allu2.rekisteroi();
     * rekisteri.getProfiileja() === 0;  
     * rekisteri.lisaa(allu1); rekisteri.getProfiileja() === 1;
     * rekisteri.lisaa(allu2); rekisteri.getProfiileja() === 2;
     * rekisteri.lisaa(allu1); rekisteri.getProfiileja() === 3;
     * rekisteri.annaProfiili(0) === allu1;
     * rekisteri.annaProfiili(1) === allu2;
     * rekisteri.annaProfiili(2) === allu1;
     * rekisteri.annaProfiili(3) === allu1; #THROWS IndexOutOfBoundsException
     * rekisteri.lisaa(allu1); rekisteri.getProfiileja() === 4;
     * rekisteri.lisaa(allu1); rekisteri.getProfiileja() === 5;
     * rekisteri.lisaa(allu1); #THROWS SailoException
     * </pre>
     */
    public void lisaa(Profiili profiili) throws SailoException {
        profiilit.lisaa(profiili);
    }

    
    /**
     * List‰‰n uusi joukkue rekisteriin
     * @param jou lis‰tt‰v‰ joukkue
     */
    public void lisaa(Joukkue jou) {
        joukkueet.lisaa(jou);
    }

    
    /**
     * Palauttaa profiilien lukum‰‰r‰n
     * @return profiilien lukum‰‰r‰
     */
    public int getProfiileja() {
        return profiilit.getLkm();
    }
    
    
    /**
     * Poistaa profiileista ja joukkueista ne joilla on nro. Kesken.
     * @param nro viitenumero, jonka mukaan poistetaan
     * @return montako profiilia poistettiin
     */
    public int poista(@SuppressWarnings("unused") int nro) {
        return 0;
    }
    
    
    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien j‰senten viitteet 
     * @param hakuehto hakuehto  
     * @param k etsitt‰v‰n kent‰n indeksi  
     * @return tietorakenteen lˆytyneist‰ j‰senist‰ 
     * @throws SailoException Jos jotakin menee v‰‰rin
     */ 
    public Collection<Profiili> etsi(String hakuehto, int k) throws SailoException { 
        return profiilit.etsi(hakuehto, k); 
    } 
    
    
    /**
     * Haetaan joukkueen tunnusnumero
     * @param jou Joukkue jonka numero halutaan tiet‰‰
     * @return Joukkueen numeron
     */
    public int annaJnro(Joukkue jou) {
        return joukkueet.annaJnro(jou);
    }
    
    
    /**
     * Asetetaan profiilille joukkue
     * @param pro profiili, jolle laitetaan joukkue
     * @param jou joukkuenumero, joka laitetaan profiiliin
     */
    public void asetaJoukkue(Profiili pro, Joukkue jou) {
        int jnro = annaJnro(jou);
        profiilit.asetaJoukkue(pro, jnro);
    }
    
    
    /**
     * Palauttaa i:n profiilin
     * @param i monesko profiili palautetaan
     * @return viite i:teen profiiliin
     */
    public Profiili annaProfiili(int i) {
        return profiilit.anna(i);
    }
    
    
    /**
     * Haetaan profiilin joukkue
     * @param profiili
     * @return Listan profiilin joukkueesta...
     * TODO: uusi joukkue pit‰isi paikata vanha joukkue
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     * Rekisteri rekisteri = new Rekisteri();
     * Profiili allu1 = new Profiili(), allu2 = new Profiili(), allu3 = new Profiili();
     * allu1.rekisteroi(); allu2.rekisteroi(); allu3.rekisteroi();
     * Joukkue ence1 = new Joukkue("ence1"); rekisteri.lisaa(ence1);
     * Joukkue ence2 = new Joukkue("ence2"); rekisteri.lisaa(ence2);
     * rekisteri.asetaJoukkue(allu1, ence1);
     * rekisteri.annaJoukkue(allu1) === ence1;
     * </pre>
     */
    public Joukkue annaJoukkue(Profiili profiili) {
        return joukkueet.annaJoukkue(profiili.getJoukkue());
    }
    
    
    /**
     * Haetaan joukkueen profiilit
     * @param jou Joukkue jonka profiileja haetaan
     * @return Listan profiilin joukkueesta...
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     * Rekisteri rekisteri = new Rekisteri();
     * Profiili allu1 = new Profiili(), allu2 = new Profiili(), allu3 = new Profiili();
     * allu1.rekisteroi(); allu2.rekisteroi(); allu3.rekisteroi();
     * Joukkue ence1 = new Joukkue("ence1"); rekisteri.lisaa(ence1);
     * Joukkue ence2 = new Joukkue("ence2"); rekisteri.lisaa(ence2);
     * rekisteri.asetaJoukkue(allu1, ence1);
     * rekisteri.asetaJoukkue(allu2, ence1);
     *
     * List<Profiili> loytyneet;
     * loytyneet = rekisteri.annaProfiilit(ence2);
     * loytyneet.size() === 0;
     * rekisteri.asetaJoukkue(allu3, ence2);
     * loytyneet = rekisteri.annaProfiilit(ence2);
     * loytyneet.size() === 1;
     * loytyneet.get(0) == allu3 === true;
     * loytyneet = rekisteri.annaProfiilit(ence1);
     * loytyneet.size() === 2;
     * loytyneet.get(0) == allu1 === true;
     * </pre>
     */
    public List<Profiili> annaProfiilit(Joukkue jou) {
        return profiilit.annaProfiilit(jou.getTunnusNro());
    }
    
    
    /**
     * Lukee rekisterin tiedot tiedostosta
     * @throws SailoException jos lukeminen ep‰onnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.*;
     * #import java.util.*;
     * 
     *  Rekisteri rekisteri = new Rekisteri();
     *  
     *  Profiili allu1 = new Profiili(); allu1.taytaAlluTiedoilla(); allu1.rekisteroi();
     *  Profiili allu2 = new Profiili(); allu2.taytaAlluTiedoilla(); allu2.rekisteroi();
     *  Joukkue ence1 = new Joukkue();
     *  Joukkue ence2 = new Joukkue();
     *   
     *  File ftied = new File("profiilit.dat");
     *  File fhtied = new File("joukkueet.dat"); 
     *  ftied.delete();
     *  fhtied.delete();
     *  rekisteri.lueTiedostosta(); #THROWS SailoException
     *  rekisteri.lisaa(allu1);
     *  rekisteri.lisaa(allu2);
     *  rekisteri.lisaa(ence1);
     *  rekisteri.lisaa(ence2);
     *  rekisteri.tallenna();
     *  rekisteri = new Rekisteri();
     *  rekisteri.lueTiedostosta();
     *  Collection<Profiili> kaikki = rekisteri.etsi("",-1); 
     *  Iterator<Profiili> it = kaikki.iterator();
     *  it.next() === allu1;
     *  it.next() === allu2;
     *  it.hasNext() === false;
     *  rekisteri.lisaa(allu2);
     *  rekisteri.lisaa(ence2);
     *  rekisteri.tallenna();
     *  ftied.delete()  === true;
     *  fhtied.delete() === true;
     * </pre>
     */
    public void lueTiedostosta() throws SailoException {
        profiilit = new Profiilit();
        joukkueet = new Joukkueet();
        
        profiilit.lueTiedostosta();
        joukkueet.lueTiedostosta();
    }

    
    /**
     * Tallentaa rekisterin tiedot tiedostoon
     * @throws SailoException jos tallentamisessa ongelmia
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            profiilit.tallenna();
        } catch (SailoException ex) {
            virhe = ex.getMessage();
        }

        try {
            joukkueet.tallenna();
        } catch (SailoException ex) {
            virhe += ex.getMessage();
        }
        if ( !"".equals(virhe) ) throw new SailoException(virhe);
    }
    
    
    /**
     * @param args Ei k‰ytˆss‰
     */
    public static void main(String[] args) {
        var rekisteri = new Rekisteri();
        
        try {
            
            var allu1 = new Profiili();
            var allu2 = new Profiili();
            
            allu1.rekisteroi();
            allu1.taytaAlluTiedoilla();
            allu2.rekisteroi();
            allu2.taytaAlluTiedoilla();
            
            rekisteri.lisaa(allu1);
            rekisteri.lisaa(allu2);
            
            rekisteri.annaJoukkue(allu1).tulosta(System.out);
            Joukkue ence1 = new Joukkue(); ence1.taytaJoukkueTiedoilla(); rekisteri.lisaa(ence1);
            Joukkue ence2 = new Joukkue(); ence2.taytaJoukkueTiedoilla(); rekisteri.lisaa(ence2);
            
            System.out.println("testi");
            for (int i = 0; i < rekisteri.getProfiileja(); i++) {
                Profiili profiili = rekisteri.annaProfiili(i);
                System.out.println("Profiilin indeksi: " +  i);
                profiili.tulosta(System.out);
            } 
        } catch (SailoException e) {
            System.err.println(e.getMessage());
            System.err.flush();
        }
    }

}
