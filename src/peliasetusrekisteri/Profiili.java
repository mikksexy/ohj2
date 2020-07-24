package peliasetusrekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * - tiet‰‰ profiilin kent‰t (nimimerkki, joukkue jne)
 * - osaa tarkistaa tietyn kent‰n oikeellisuuden
 * - osaa muuttaa 1|s1mple|..| -merkkijonon j‰senen tiedoiksi
 * - osaa antaa merkkijonona i:n kent‰n tiedot
 * - osaa laittaa merkkijonon i:neksi kent‰ksi
 * 
 * @author Sami
 * @version 24.7.2020
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
     * Apumetodi, jolla saadaan t‰ytetty‰ testiarvot profiilille
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
     * Arvotaan satunnainen kokonaisluku v‰luille [ala,yla]
     * @param ala arvonnan alaraja
     * @param yla arvonnan yl‰raja
     * @return satunnainen luku v‰lilt‰ [ala,yla]
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
     * Palauttaa j‰senen tunnusnumeron
     * @return profiilin tunnusnumero
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    
    /**
     * Asettaa tunnusnumeron ja samalla varmistaa ett‰
     * seuraava numero on aina suurempi kuin t‰h‰n menness‰ suurin.
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
     * Selvit‰‰ profiilin tiedot | erotellusta merkkijonosta
     * Pit‰‰ huolen ett‰ seuraavaNro on suurempi kuin tuleva tunnusNro.
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
     * @param args Ei k‰ytˆss‰
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
