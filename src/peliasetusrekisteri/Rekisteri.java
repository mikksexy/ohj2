package peliasetusrekisteri;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * - huolehtii profiilit ja joukkueet -luokkien välisestä yhteistyöstä ja välittää näitä tietoja pyydettäessä
 * - lukee ja kirjoittaa rekisterin tiedostoon pyytämällä apua avustajiltaan
 * @author Sami
 * @version 31.7.2020
 * 
 * Testien alustus
 * @example
 * <pre name="testJAVA">
 * #import peliasetusrekisteri.SailoException;
 *  private Rekisteri rekisteri;
 *  private Profiili allu1;
 *  private Profiili allu2;
 *  private int pid1;
 *  private int pid2;
 *  private Joukkue ence1;
 *  private Joukkue ence2;
 *  private Joukkue ence3; 
 *  
 *  public void alustaRekisteri() {
 *    rekisteri = new Rekisteri();
 *    allu1 = new Profiili(); allu1.taytaAlluTiedoilla(); allu1.rekisteroi();
 *    allu2 = new Profiili(); allu2.taytaAlluTiedoilla(); allu2.rekisteroi();
 *    pid1 = allu1.getTunnusNro();
 *    pid2 = allu2.getTunnusNro();
 *    ence1 = new Joukkue("ence1");
 *    ence2 = new Joukkue("ence2");
 *    ence3 = new Joukkue("ence3");
 *    try {
 *    rekisteri.lisaa(allu1);
 *    rekisteri.lisaa(allu2);
 *    rekisteri.lisaa(ence1);
 *    rekisteri.lisaa(ence2);
 *    rekisteri.lisaa(ence3);
 *    } catch ( Exception e) {
 *       System.err.println(e.getMessage());
 *    }
 *  }

 */
public class Rekisteri {

    private Profiilit profiilit = new Profiilit();
    private Joukkueet joukkueet = new Joukkueet();

    /**
     * Lisätään uusi profiili rekisteriin
     * @param profiili lisättävä profiili
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
     * rekisteri.lisaa(allu1); 
     * </pre>
     */
    public void lisaa(Profiili profiili) {
        profiilit.lisaa(profiili);
    }
    

    /**
     * Korvaa profiilin tietorakenteessa. Tarkastetaan löytyykö profiili jo,
     * jos ei niin lisätään uutena profiilina
     * @param profiili profiili joka lisätään tai korvataan
     * @example
     * <pre name="test">
     * #THROWS SailoException  
     *  alustaRekisteri();
     *  rekisteri.etsi("*",0).size() === 2;
     *  rekisteri.korvaaTaiLisaa(allu1);
     *  rekisteri.etsi("*",0).size() === 2;
     * </pre>
     */
    public void korvaaTaiLisaa(Profiili profiili) {
        profiilit.korvaaTaiLisaa(profiili);
    }
    
    
    /**
     * Korvaa joukkueen tietorakenteessa. Tarkastetaan löytyykö joukkue jo,
     * jos ei niin lisätään uutena joukkueena
     * @param joukkue Joukkue joka halutaan lisätä
     * @return Palauttaa joukkueen tunnusnumeron
     */
    public int korvaaTaiLisaa(Joukkue joukkue) {
        return joukkueet.korvaaTaiLisaa(joukkue);
    }
    
    
    /**
     * Käy joukkueet tietorakenteen läpi, ja yrittää etsiä saman nimistä joukkuetta
     * @param nimi Nimi, jota etsitään
     * @return Jos ei ole muita palauttaa 0, muuten palauttaa joukkueen numeron
     */
    public int onkoMuita(String nimi) {
        return joukkueet.onkoMuita(nimi);
    }
    
    
    /**
     * Käy tietorakenteen läpi, samalla etsien halutun nimisiä joukkueita
     * @param nimi Nimi, jota etsitään
     * @return Jos ei ole muita palauttaa 0, muuten palauttaa joukkueiden määrän
     */
    public int onkoMonta(String nimi) {
        return joukkueet.onkoMonta(nimi);
    }
    

    /**
     * Lisätään uusi joukkue rekisteriin
     * @param joukkue lisättävä joukkue
     */
    public void lisaa(Joukkue joukkue) {
        joukkueet.lisaa(joukkue);
    }
    

    /**
     * Palauttaa profiilien lukumäärän
     * @return profiilien lukumäärä
     */
    public int getProfiileja() {
        return profiilit.getLkm();
    }
    

    /**
     * Poistaa profiileista profiilin sekä joukkueen, jos joukkueessa ei muita profiileja
     * @param profiili profiili joka poistetaan
     * @return montako profiilia poistettiin
     * @example
     * <pre name="test">
     * #THROWS Exception
     * alustaRekisteri();
     * rekisteri.etsi("*",1).size() === 2;
     * rekisteri.poista(allu1) === 1;
     * rekisteri.etsi("*",1).size() === 1;
     * </pre>
     */
    public int poista(Profiili profiili) {
        if ( profiili == null ) return 0;
        int ret = profiilit.poista(profiili.getTunnusNro()); 
        joukkueet.poista(annaJoukkue(profiili.getJoukkue())); 
        return ret; 
    }
    
    
    /**
     * Poistaa joukkueen tietorakenteestaan
     * @param joukkue Joukkue, joka halutaan poistaa
     * @example
     * <pre name="test">
     * #THROWS Exception
     * alustaRekisteri();
     * rekisteri.etsi("*").size() === 6;
     * rekisteri.poista(ence1);
     * rekisteri.etsi("*").size() === 4;
     * </pre>
     */
    public void poista(Joukkue joukkue) {
        joukkueet.poista(joukkue);
    }


    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien jäsenten viitteet 
     * @param hakuehto hakuehto  
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä jäsenistä 
     * @throws SailoException Jos jotakin menee väärin
     * @example 
     * <pre name="test">
     *   #THROWS CloneNotSupportedException, SailoException
     *   alustaRekisteri();
     *   Profiili allu3 = new Profiili(); allu3.rekisteroi();
     *   allu3.setNimi("allu3");
     *   rekisteri.lisaa(allu3);
     *   Collection<Profiili> loytyneet = rekisteri.etsi("*allu*",0);
     *   loytyneet.size() === 3;
     *   Iterator<Profiili> it = loytyneet.iterator();
     *   it.next() == allu2 === false; 
     * </pre>
     */
    public Collection<Profiili> etsi(String hakuehto, int k) throws SailoException {
        return profiilit.etsi(hakuehto, k);
    }
    

    /**
     * Haetaan joukkueen tunnusnumero
     * @param jou Joukkue jonka numero halutaan tietää
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
    public void setJoukkue(Profiili pro, Joukkue jou) {
        int jnro = annaJnro(jou);
        profiilit.setJoukkue(pro, jnro);
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
     * @param jnro joukkueen numero
     * @return Palauttaa joukkueen
     * @example
     * <pre name="test">
     * #import java.util.*;
     * Rekisteri rekisteri = new Rekisteri();
     * Profiili allu1 = new Profiili(), allu2 = new Profiili(), allu3 = new Profiili();
     * allu1.rekisteroi(); allu2.rekisteroi(); allu3.rekisteroi();
     * Joukkue ence1 = new Joukkue("ence1"); rekisteri.lisaa(ence1);
     * Joukkue ence2 = new Joukkue("ence2"); rekisteri.lisaa(ence2);
     * rekisteri.setJoukkue(allu1, ence1);
     * </pre>
     */
    public Joukkue annaJoukkue(int jnro) {
        return joukkueet.annaJoukkue(jnro);
    }
    
    
    /**
     * Etsitään joukkue nimen perusteella
     * @param nimi Joukkueen nimi
     * @return Palauttaa halutun nimisen joukkueen, jos löytyy
     * @example
     * <pre name="test">
     * alustaRekisteri();
     * rekisteri.annaJoukkue("ence1").getNimi() === "ence1";
     * </pre>
     */
    public Joukkue annaJoukkue(String nimi) {
        return joukkueet.annaJoukkue(nimi);
    }

    
    /**
     * Haetaan joukkueen profiilit
     * @param ehto Joukkue jonka profiileja haetaan
     * @return Listan profiilin joukkueesta...
     * @throws SailoException Jos jotain menee väärin
     * @example
     * <pre name="test">
     * #THROWS Exception
     * alustaRekisteri();
     * rekisteri.etsi("*").size() === 6;
     * rekisteri.poista(ence1);
     * rekisteri.etsi("*").size() === 4;
     * </pre>
     */
    public Collection<Profiili> etsi(String ehto) throws SailoException {
        List<Profiili> loytyneet = new ArrayList<Profiili>();
        List<Joukkue> jou = joukkueet.etsi(ehto);
        for ( Joukkue joukkue : jou ) {
            Collection<Profiili> prof = profiilit.annaProfiilit(joukkue.getTunnusNro());
            for ( Profiili pro : prof ) {
                loytyneet.add(pro);
            }
        }
        return loytyneet;
    }


    /**
     * Lukee rekisterin tiedot tiedostosta
     * @throws SailoException jos lukeminen epäonnistuu
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
     *  Collection<Profiili> kaikki = rekisteri.etsi("",0);
     *  Iterator<Profiili> it = kaikki.iterator();
     *  it.hasNext() === true;
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
        if (!"".equals(virhe))
            throw new SailoException(virhe);
    }
    
    
    /**
     * Laskee kaikkien profiilien eDPI-keskiarvon
     * @return Palauttaa eDPI-keskiarvon
     */
    public double edpiKa() {
        return profiilit.edpiKa();
    }
    

    /**
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        var rekisteri = new Rekisteri();

        var allu1 = new Profiili();
        var allu2 = new Profiili();

        allu1.rekisteroi();
        allu1.taytaAlluTiedoilla();
        allu2.rekisteroi();
        allu2.taytaAlluTiedoilla();

        rekisteri.lisaa(allu1);
        rekisteri.lisaa(allu2);

        rekisteri.annaJoukkue(allu1.getJoukkue()).tulosta(System.out);
        Joukkue ence1 = new Joukkue();
        ence1.taytaJoukkueTiedoilla();
        rekisteri.lisaa(ence1);
        Joukkue ence2 = new Joukkue();
        ence2.taytaJoukkueTiedoilla();
        rekisteri.lisaa(ence2);

        System.out.println("testi");
        for (int i = 0; i < rekisteri.getProfiileja(); i++) {
            Profiili profiili = rekisteri.annaProfiili(i);
            System.out.println("Profiilin indeksi: " + i);
            profiili.tulosta(System.out);

        }
    }

}
