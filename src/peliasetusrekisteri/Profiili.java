package peliasetusrekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * - tietää profiilin kentät (nimimerkki, joukkue jne)
 * - osaa tarkistaa tietyn kentän oikeellisuuden
 * - osaa muuttaa 1|s1mple|..| -merkkijonon jäsenen tiedoiksi
 * - osaa antaa merkkijonona i:n kentän tiedot
 * - osaa laittaa merkkijonon i:neksi kentäksi
 * 
 * @author Sami
 * @version 24.7.2020
 *
 */
public class Profiili implements Cloneable{
    private int     tunnusNro;
    private int     joukkue             = 0;
    private String  nimimerkki          = "";
    private double  hiirenHerkkyys      = 0.0;
    private int     dpi                 = 0;
    private String  naytonTarkkuus      = "";
    private String  kuvasuhde           = "";
    private String  skaalaus            = "";
    private int     virkistystaajuus    = 0;
    private double  edpi                = 0.0;
    
    private static int seuraavaNro      = 1;
    
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot profiilille
     * TODO: poista kun toimii
     */
    public void taytaAlluTiedoilla() {
        nimimerkki = "allu" + rand(1000, 9999);
        hiirenHerkkyys = 3.12;
        dpi = 400;
        naytonTarkkuus = "800x600";
        kuvasuhde = "4:3";
        skaalaus = "black bars";
        virkistystaajuus = 144;
        setEdpi();
    }
    
    
    /**
     * Laskee profiilin eDPI arvon hiiren herkkyydestä ja dpi:stä
     */
    public void setEdpi() {
        edpi = hiirenHerkkyys * dpi;
    }
    
    
    /**
     * Palauttaa eDPI arvon
     * @return Palauttaa eDPI arvon
     */
    public double getEdpi() {
        return edpi;
    }


    /**
     * Arvotaan satunnainen kokonaisluku väluille [ala,yla]
     * @param ala arvonnan alaraja
     * @param yla arvonnan yläraja
     * @return satunnainen luku väliltä [ala,yla]
     */
    public static int rand(int ala, int yla) {
        double n = (yla-ala)*Math.random() + ala;
        return (int)Math.round(n);
    }
    
    
    /**
     * Tulostetaan profiilin tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tunnusNro) + " nimimerkki: " + nimimerkki + " joukkue: " + joukkue);
        out.println("sens: " + hiirenHerkkyys + " dpi: " + dpi);
        out.println("resoluutio: " + naytonTarkkuus + " kuvasuhde: " + kuvasuhde + " " + skaalaus + " refresh rate: " + virkistystaajuus + " hz");
        out.println("eDPI:" + getEdpi());
    }
    
    
    /**
     * Tulostetaan profiilin tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Antaa profiilille seuraavan rekisterinumeron
     * @return  profiilin uusi tunnusNro
     * @example
     * <pre name="test">
     *  Profiili allu1 = new Profiili();
     *  allu1.getTunnusNro() === 0;
     *  allu1.rekisteroi();
     *  Profiili allu2 = new Profiili();
     *  allu2.rekisteroi();
     *  int n1 = allu1.getTunnusNro();
     *  int n2 = allu2.getTunnusNro();
     *  n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    
    
    /**
     * Palauttaa jäsenen tunnusnumeron
     * @return profiilin tunnusnumero
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    
    /**
     * Asettaa tunnusnumeron ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }

    
    
    /**
     * Palauttaa profiilin nimimerkin
     * @return profiilin nimimerkin
     */
    public String getNimimerkki() {
        return nimimerkki;
    }
    
    /**
     * Palauttaa hiiren herkkyyden
     * @return Palauttaa hiiren herkkyyden
     */
    public double getHerkkyys() {
        return hiirenHerkkyys;
    }


    /**
     * Palauttaa dpi arvon
     * @return Palauttaa dpi arvon
     */
    public int getDPI() {
        return dpi;
    }


    /**
     * Palauttaa näytöntarkkuuden merkkijonona
     * @return Palauttaa näytöntarkkuuden merkkijonona
     */
    public String getTarkkuus() {
        return naytonTarkkuus;
    }


    /**
     * Palauttaa kuvasuhteen merkkijonona
     * @return Palauttaa kuvasuhteen merkkijonona
     */
    public String getKuvasuhde() {
        return kuvasuhde;
    }


    /**
     * Palauttaa skaalauksen merkkijonona
     * @return Palauttaa skaalauksen merkkijonona
     */
    public String getSkaalaus() {
        return skaalaus;
    }
    
    
    /**
     * Palauttaa virkistystaajuuden kokonaislukuna
     * @return Palauttaa virkistystaajuuden kokonaislukuna
     */
    public int getTaajuus() {
        return virkistystaajuus;
    }
    
    
    /**
     * Asettaa profiilille uuden nimen
     * @param s merkkijono, joka asetetaan
     * @return null jos ei virhettä, muuten virhe merkkijonona
     */
    public String setNimi(String s) {
        nimimerkki = s;
        return null;
    }


    /**
     * Asettaa profiilille uuden hiirenherkkyyden
     * @param s merkkijono, joka asetetaan herkkyydeksi
     * @return null jos ei virhettä, muuten virhe merkkijonona
     */
    public String setHerkkyys(String s) {
        if ( !s.matches("^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?$") ) return "käytä erottimena \".\"";
        hiirenHerkkyys = Mjonot.erotaDouble(s, hiirenHerkkyys);
        setEdpi();
        return null;
        
    }


    /**
     * Asettaa profiilille uuden dpi:n
     * @param s merkkijono, joka asetetaan dpi:ksi
     * @return null jos ei virhettä, muuten virhe merkkijonona
     */
    public String setDPI(String s) {
        if ( !s.matches("[0-9]*") ) return "dpi:n oltava numeerinen";
        dpi = Mjonot.erotaInt(s, dpi);
        setEdpi();
        return null;
    }


    /**
     * Asettaa profiilille uuden näytöntarkkuuden
     * @param s merkkijono, joka asetetaan herkkyydeksi
     * @return null jos ei virhettä, muuten virhe merkkijonona
     */
    public String setTarkkuus(String s) {
        if ( !s.matches("^([1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])x([1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]|)$") ) return "näytön tarkkuuden oltava muotoa 1920x1080";
        naytonTarkkuus = s;
        return null;
    }


    /**
     * Asettaa profiilille uuden kuvasuhteen
     * @param s merkkijono, joka asetetaan kuvasuhteeksi
     * @return null jos ei virhettä, muuten virhe merkkijonona
     */
    public String setKuvasuhde(String s) {
        if ( !s.matches("4:3") && !s.matches("16:9") && !s.matches("16:10") ) return "kuvasuhteen oltava muotoa 16:9 tms.";
        kuvasuhde = s;
        return null;
    }


    /**
     * Asettaa profiilille uuden skaalauksen
     * @param s merkkijono, joka asetetaan skaalaukseksi
     * @return null jos ei virhettä, muuten virhe merkkijonona
     */
    public String setSkaalaus(String s) {
        skaalaus = s;
        return null;
    }


    /**
     * Asettaa profiilille uuden virkistystaajuuden
     * @param s merkkijono, joka asetetaan virkistystaajuudeksi
     * @return null jos ei virhettä, muuten virhe merkkijonona
     */
    public String setTaajuus(String s) {
        if ( !s.matches("[0-9]*") ) return "virkistystaajuuden oltava numeerinen";
        virkistystaajuus = Mjonot.erotaInt(s, virkistystaajuus);
        return null;
    }
    
    
    /**
     * Asetetaan profiilille joukkueen numero
     * @param jnro joukkueen tunnusnumero
     * @return null jos ei virhettä, muuten virhe merkkijonona
     */
    public String setJoukkue(int jnro) {
        joukkue = jnro;
        return null;
    }
    
    
    /**
     * Palauttaa joukkuenumeron
     * @return Palauttaa joukkuenumeron
     */
    public int getJoukkue() {
        return joukkue;
    }
    
    
    /**
     * Palauttaa profiilin tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return profiili tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Profiili profiili = new Profiili();
     *   profiili.parse("   2  |  allu | 2");
     *   profiili.toString().startsWith("2|allu|2|") === true;
     * </pre>  
     */
    @Override
    public String toString() {
        return "" +
                getTunnusNro() + "|" +
                getNimimerkki() + "|" +
                getJoukkue() + "|" +
                hiirenHerkkyys + "|" +
                dpi + "|" +
                naytonTarkkuus + "|" +
                kuvasuhde + "|" +
                skaalaus + "|" +
                virkistystaajuus;
    }
    
    
    /**
     * Selvitää profiilin tiedot | erotellusta merkkijonosta
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param rivi josta profiilin tiedot otetaan
     * @example
     * <pre name="test">
     *   Profiili profiili = new Profiili();
     *   profiili.parse("   2  |  allu | 2");
     *   profiili.getTunnusNro() === 2;
     *   profiili.toString().startsWith("2|allu|2|") === true;
     *
     *   profiili.rekisteroi();
     *   int n = profiili.getTunnusNro();
     *   profiili.parse(""+(n+20));
     *   profiili.rekisteroi();
     *   profiili.getTunnusNro() === n+20+1;  
     * </pre>
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        nimimerkki = Mjonot.erota(sb, '|', getNimimerkki());
        joukkue = Mjonot.erota(sb, '|', getJoukkue());
        hiirenHerkkyys = Mjonot.erota(sb, '|', hiirenHerkkyys);
        dpi = Mjonot.erota(sb, '|', dpi);
        naytonTarkkuus = Mjonot.erota(sb, '|', naytonTarkkuus);
        kuvasuhde = Mjonot.erota(sb, '|', kuvasuhde);
        skaalaus = Mjonot.erota(sb, '|', skaalaus);
        virkistystaajuus = Mjonot.erota(sb,  '|', virkistystaajuus);
    }
    
    
    /**
     * Tehdään identtinen klooni jäsenestä
     * @return Object kloonattu jäsen
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException
     *   Profiili profiili = new Profiili();
     *   profiili.parse("   3  |  allu  | 123");
     *   Profiili kopio = profiili.clone();
     *   kopio.toString() === profiili.toString();
     *   profiili.parse("   4  |  allu2   | 123");
     *   kopio.toString().equals(profiili.toString()) === false;
     * </pre>
     */
    @Override
    public Profiili clone() throws CloneNotSupportedException {
        Profiili uusi;
        uusi = (Profiili) super.clone();
        return uusi;
    }
    
    
    @Override
    public boolean equals(Object profiili) {
        if ( profiili == null ) return false;
        return this.toString().equals(profiili.toString());
    }


    @Override
    public int hashCode() {
        return tunnusNro;
    }
    
    
    /**
     * Testataan luokan aliohjelmia
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        Profiili allu = new Profiili();
        Profiili allu2 = new Profiili();
        
        allu.rekisteroi();
        allu2.rekisteroi();
        
        allu.tulosta(System.out);
        allu.taytaAlluTiedoilla();
        allu.tulosta(System.out);

        allu2.tulosta(System.out);
        allu2.taytaAlluTiedoilla();
        allu2.tulosta(System.out);
    }
}
