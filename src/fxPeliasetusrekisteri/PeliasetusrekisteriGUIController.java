package fxPeliasetusrekisteri;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import peliasetusrekisteri.Profiili;
import peliasetusrekisteri.Rekisteri;
import peliasetusrekisteri.Joukkue;
import peliasetusrekisteri.SailoException;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.*;

/**
 * Luokka käyttöliittymän tapahtumien hoitamiseksi
 * @author Sami
 * @version 30.7.2020
 *
 */
public class PeliasetusrekisteriGUIController implements Initializable {
    
    @FXML private ListChooser<Profiili> chooserProfiilit;
    @FXML private ScrollPane panelProfiili;
    @FXML private ComboBoxChooser<String> cbKentat;
    @FXML private TextField hakuehto;
    @FXML private Label labelVirhe;
    @FXML private TextField editNimi;
    @FXML private TextField editJoukkue;
    @FXML private TextField editHerkkyys;
    @FXML private TextField editDPI;
    @FXML private TextField editTarkkuus;
    @FXML private TextField editKuvasuhde;
    @FXML private TextField editSkaalaus;
    @FXML private TextField editTaajuus;
    @FXML private TextField editEdpi;
    @FXML private TextField editEdpiKa;
    

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }
    
    
    /**
     * Käsitellään hakuehto
     */
    @FXML private void handleHakuehto() {
        if ( profiiliKohdalla != null )
            hae(profiiliKohdalla.getTunnusNro());
    }
    
    
    /**
     * Käsitellään tallennuskäsky
     */
    @FXML private void handleTallenna() {
        tallenna();
    }
    
    
    /**
     * Käsitellään lopetuskäsky
     */
    @FXML private void handleLopeta() {
        tallenna();
        Platform.exit();
    }
    
    
    /**
     * Käsitellään uuden profiilin lisääminen
     */
    @FXML void handleUusiProfiili() {
        uusiProfiili();
        uusiJoukkue();
    }
    
    
    /**
     * Käsitellään profiilin tietojen muokkaaminen
     */
    @FXML void handleMuokkaa() {
        muokkaa();
    }
    
    
    /**
     * Käsitellään profiilin tietojen muokkaaminen
     */
    @FXML void handlePoista() {
        poista();
    }
    
    
    /**
     * Käsitellään avunpyynnöt
     */
    @FXML void handleApua() {
        avustus();
    }
    
    
    /**
     * Käsitellään ohjelman tietojen näyttäminen
     */
    @FXML private void handleTietoja() {
        ModalController.showModal(PeliasetusrekisteriGUIController.class.getResource("TietojaView.fxml"), "Tietoja", null, "");
    }
    
    
    // ===========================================================================================================================
    // Ei-GUI liittyvät
    
    
    private Rekisteri rekisteri;
    private Profiili profiiliKohdalla;
    private TextField edits[];
    
    
    /**
     * Alustetaan käyttöliittymä
     */
    private void alusta() {
        
        chooserProfiilit.clear();
        chooserProfiilit.addSelectionListener(e -> naytaProfiili());
        
        edits = new TextField[] { editNimi, editJoukkue, editHerkkyys, editDPI, editTarkkuus, editKuvasuhde, editSkaalaus, editTaajuus, editEdpi, editEdpiKa };
    }
    
    
    /**
     * Näytetään kohdalla olevan profiilin tiedot
     */
    private void naytaProfiili()  {
        profiiliKohdalla = chooserProfiilit.getSelectedObject();
        
        if (profiiliKohdalla == null) return;
        profiiliKohdalla.setEdpi();
        edits[8].setText(String.format("%.1f", profiiliKohdalla.getEdpi()));
        edits[9].setText(String.format("%.1f",rekisteri.edpiKa()));
        
        ProfiiliDialogController.naytaProfiili(edits, profiiliKohdalla);
        
        
    }
    
    
    /**
     * Tulostaa labelille virheen
     * @param virhe Virhe joka tulostuu
     */
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }

    
    /**
     * Alustaa rekisterin lukemalla sen tiedostosta
     * @return null jos onnistuu, muuten virhe tekstinä
     */
    protected String lueTiedosto() {
        try {
            rekisteri.lueTiedostosta();
            hae(0);
            return null;
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage(); 
            if ( virhe != null ) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
     }
    
    
    /**
     * Tietojen tallennus
     * @return null jos onnistuu, muuten virhe tekstinä
     */
    private String tallenna() {
        try {
            rekisteri.tallenna();
            return null;
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
            return ex.getMessage();
        }
    }
    
    
    /**
     * Uuden profiilin luominen
     */
    private void uusiProfiili() {
        Profiili uusi = new Profiili();
        uusi = ProfiiliDialogController.kysyProfiili(null, uusi);
        if ( uusi == null ) return;
        uusi.rekisteroi();
        rekisteri.lisaa(uusi);
        hae(uusi.getTunnusNro());
    }
    
    
    /**
     * Uuden joukkueen luominen
     */
    public void uusiJoukkue() {
        Joukkue jou = new Joukkue();
        jou.rekisteroi();
        jou.taytaJoukkueTiedoilla();
        rekisteri.lisaa(jou);
        hae(profiiliKohdalla.getTunnusNro());
    }
    
    
    private void poista() {
        Profiili profiili = profiiliKohdalla;
        if ( profiili == null ) return;
        if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko profiili: " + profiili.getNimimerkki(), "Kyllä", "Ei") )
            return;
        rekisteri.poista(profiili);
        int index = chooserProfiilit.getSelectedIndex();
        hae(0);
        chooserProfiilit.setSelectedIndex(index);
    }
    
    
    /**
     * Haetaan profiili
     * @param pnro profiilinumero, jota haetaan
     */
    private void hae(int pnro) {
        int k = cbKentat.getSelectionModel().getSelectedIndex();
        String ehto = hakuehto.getText(); 
        if (k > 0 || ehto.length() > 0)
            naytaVirhe(String.format("Ei osata hakea (kenttä: %d, ehto: %s)", k, ehto));
        else
            naytaVirhe(null);
        
        chooserProfiilit.clear();
        
        int index = 0;
        Collection<Profiili> profiilit;
        try {
            profiilit = rekisteri.etsi(ehto, k);
            int i = 0;
            for (Profiili profiili : profiilit) {
                if (profiili.getTunnusNro() == pnro) index = i;
                chooserProfiilit.add(profiili.getNimimerkki(), profiili);
                i++;
            }
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Profiilin hakemisessa ongelmia! " + ex.getMessage());
        }
        
        chooserProfiilit.getSelectionModel().select(index);
    }
    
    
    /**
     * Profiilin tietojen muokkaaminen
     */
    private void muokkaa() {
        if ( profiiliKohdalla == null ) return; 
            try { 
                Profiili profiili; 
                profiili = ProfiiliDialogController.kysyProfiili(null, profiiliKohdalla.clone()); 
                if ( profiili == null ) return; 
                rekisteri.korvaaTaiLisaa(profiili); 
                hae(profiili.getTunnusNro()); 
            } catch (CloneNotSupportedException e) { 
                Dialogs.showMessageDialog(e.getMessage());
            } 
    }


    /**
     * Asetetaan kontrollerin rekisteriviite
     * @param rekisteri mihin viitataan
     */
    public void setRekisteri(Rekisteri rekisteri) {
        this.rekisteri = rekisteri;
        naytaProfiili();
    }
    
    
    /**
     * Tarkistetaan onko tallennus tehty
     * @return true jos saa sulkea sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }
    
    
    /**
     * Näytetään ohjelman suunnitelma erillisessä selaimessa.
     */
    private void avustus() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2020k/ht/mikksexy");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            naytaVirhe(e.getMessage());
            return;
        } catch (IOException e) {
            naytaVirhe(e.getMessage());
            return;
        }
    }
    
    
    /**
     * Tulostaa profiilin tiedot
     * @param os tietovirta johon tulostetaan
     * @param profiili joka halutaan tulostaa
     */
    public void tulosta(PrintStream os, final Profiili profiili) {
        profiili.tulosta(os);
        Joukkue joukkue = rekisteri.annaJoukkue(profiili);
        joukkue.tulosta(os);
    }
    
}