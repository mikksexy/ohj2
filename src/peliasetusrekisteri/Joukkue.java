package peliasetusrekisteri;

import java.io.*;

/**
 * - tietää joukkueen kentät
 * - osaa tarkistaa tietyn kentän oikeellisuuden
 * - osaa muuttaa 1|Natus Vincere| -merkkijonon joukkueen tiedoiksi
 * - osaa antaa merkkijonona i:n kentän tiedot
 * - osaa laittaa merkkijonon i:neksi kentäksi
 * @author Sami
 * @version 4.7.2020
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
        taytaJoukkueTiedoilla();
        rekisteroi();
    }
    
    
    /**
     * Luodaan uusi joukkue, jolle annetaan parametrina nimi
     * @param joukkueNimi joukkueelle annettava nimi
     */
    public Joukkue(String joukkueNimi) {
        this.nimi = joukkueNimi;
        rekisteroi();
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
     *   Harrastus pitsi1 = new Harrastus();
     *   pitsi1.getTunnusNro() === 0;
     *   pitsi1.rekisteroi();
     *   Harrastus pitsi2 = new Harrastus();
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
     * Muutetaan Joukkue-olio merkkijonoksi
     */
    @Override
    public String toString() {
        return nimi;
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
