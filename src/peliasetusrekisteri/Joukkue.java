package peliasetusrekisteri;

import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * - tietää joukkueen kentät
 * - osaa tarkistaa tietyn kentän oikeellisuuden
 * - osaa muuttaa 1|Natus Vincere| -merkkijonon joukkueen tiedoiksi
 * - osaa antaa merkkijonona i:n kentän tiedot
 * - osaa laittaa merkkijonon i:neksi kentäksi
 * @author Sami
 * @version 31.7.2020
 *
 */
public class Joukkue {
    private int tunnusNro;
    private String nimi;
    private static int seuraavaNro = 1;
    
    
    /**
     * Alustetaan joukkue
     */
    public Joukkue() {
        // taytaJoukkueTiedoilla();
    }
    
    
    /**
     * Luodaan uusi joukkue, jolle annetaan parametrina nimi
     * @param joukkueNimi joukkueelle annettava nimi
     */
    public Joukkue(String joukkueNimi) {
        this.nimi = joukkueNimi;
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
     * Täyttää joukkueen nimellä ja yksilöivällä luvulla
     */
    public void taytaJoukkueTiedoilla() {
        nimi = "Ence" + rand(1000, 2000);
    }
    
    
    /**
     * Tulostetaan joukkueen tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(nimi);
    }

    
    /**
     * Tulostetaan joukkueen tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }

    /**
     * Antaa harrastukselle seuraavan rekisterinumeron.
     * @return harrastuksen uusi tunnus_nro
     * @example
     * <pre name="test">
     *   Joukkue pitsi1 = new Joukkue();
     *   pitsi1.getTunnusNro() === 0;
     *   pitsi1.rekisteroi();
     *   Joukkue pitsi2 = new Joukkue();
     *   pitsi2.rekisteroi();
     *   int n1 = pitsi1.getTunnusNro();
     *   int n2 = pitsi2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }

    
    /**
     * Palautetaan joukkueen oma id
     * @return joukkueen id
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    
    /**
     * Palautetaan joukkueen nimi
     * @return Palauttaa joukkueen nimen
     */
    public String getNimi() {
        return nimi;
    }
    
    
    /**
     * TODO:;
     * @param nimi
     * @return Palauttaa nimen
     */
    public String setNimi(String nimi) {
        this.nimi = nimi;
        return nimi;
    }
    
    
    /**
     * Asettaa tunnusnumeron ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if ( tunnusNro >= seuraavaNro ) seuraavaNro = tunnusNro + 1;
    }


    
    /**
     * Palauttaa joukkueen tiedot merkkijonona, jonka voi tallentaa tiedostoon.
     * @return joukkue tolppaeroteltuna merkkijonona
     * @example
     * <pre name="test">
     * Joukkue joukkue = new Joukkue();
     * joukkue.parse("  1  |  Natus Vincere  ");
     * joukkue.toString() === "1|Natus Vincere";
     * </pre>
     */
    @Override
    public String toString() {
        return "" + getTunnusNro() + "|" + nimi;
    }
    
    
    /**
     * Selvittää joukkueen tiedot | erotellusta merkkijonosta.
     * Pitää huolen, että seuraavaNro on suurempi kuin tuleva tunnusnumero.
     * @param rivi josta joukkueen tiedot otetaan
     * @example
     * <pre name="test">
     * Joukkue joukkue = new Joukkue();
     * joukkue.parse("  1  |  Natus Vincere  ");
     * joukkue.toString() === "1|Natus Vincere";
     * 
     * joukkue.rekisteroi();
     * int n = joukkue.getTunnusNro();
     * joukkue.parse(""+(n+20));
     * joukkue.rekisteroi();
     * joukkue.getTunnusNro() === n+20+1;
     * joukkue.toString() === "" + (n+20+1) + "|Natus Vincere";
     * </pre>
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb,  '|', getTunnusNro()));
        nimi = Mjonot.erota(sb, '|', nimi);
    }
    
    
    /**
     * Testiohjelma joukkueelle
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        Joukkue jou = new Joukkue();
        jou.taytaJoukkueTiedoilla();
        jou.tulosta(System.out);

    }

}
