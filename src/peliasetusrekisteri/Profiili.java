package peliasetusrekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * - tietää profiilin kentät (nimimerkki, joukkue jne)
 * - osaa tarkistaa tietyn kentän oikeellisuuden
 * - osaa muuttaa 1|s1mple|..| -merkkijonon jäsenen tiedoiksi
 * - osaa antaa merkkijonona i:n kentän tiedot
 * - osaa laittaa merkkijonon i:neksi kentäksi
 * 
 * @author Sami
 * @version 10.7.2020
 *
 */
public class Profiili {
    private int     tunnusNro;
    private int     joukkue             = 0;
    private String  nimimerkki          = "";
    private double  hiirenHerkkyys      = 0.0;
    private int     dpi                 = 0;
    private String  naytonTarkkuus      = "";
    private String  kuvasuhde           = "";
    private String  skaalaus            = "";
    private int     virkistystaajuus    = 0;
    
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
     * Palauttaa profiilin nimimerkin
     * @return profiilin nimimerkin
     */
    public String getNimimerkki() {
        return nimimerkki;
    }
    
    
    /**
     * Asetetaan profiilille joukkueen numero
     * @param jnro joukkueen tunnusnumero
     */
    public void asetaJoukkue(int jnro) {
        this.joukkue = jnro;
    }
    
    
    /**
     * Palauttaa joukkuenumeron
     * @return Palauttaa joukkuenumeron
     */
    public int getJoukkue() {
        return joukkue;
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
