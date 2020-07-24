package fxPeliasetusrekisteri;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import peliasetusrekisteri.Profiili;
import peliasetusrekisteri.Rekisteri;
import peliasetusrekisteri.Joukkue;
import peliasetusrekisteri.SailoException;

import java.io.PrintStream;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.*;

/**
 * Luokka käyttöliittymän tapahtumien hoitamiseksi
 * @author Sami
 * @version 24.7.2020
 *
 */
public class PeliasetusrekisteriGUIController implements Initializable {
    
    @FXML private ListChooser<Profiili> chooserProfiilit;
    @FXML private ScrollPane panelProfiili;
    @FXML private ComboBoxChooser<String> cbKentat;
    @FXML private TextField hakuehto;
    @FXML private Label labelVirhe;
    

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
        Dialogs.showQuestionDialog("Profiilin poistaminen", "Poistetaanko profiili: allu", "Kyllä", "Ei");
    }
    
    
    /**
     * Käsitellään avunpyynnöt
     */
    @FXML void handleApua() {
        Dialogs.showMessageDialog("Apuva!");
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
    private TextArea areaProfiili = new TextArea();
    
    
    /**
     * Alustetaan käyttöliittymä
     */
    private void alusta() {
        panelProfiili.setContent(areaProfiili);
        areaProfiili.setFont(new Font("Courier New", 12));
        panelProfiili.setFitToHeight(true);
        
        chooserProfiilit.clear();
        chooserProfiilit.addSelectionListener(e -> naytaProfiili());
    }
    
    
    /**
     * Näytetään kohdalla olevan profiilin tiedot
     */
    private void naytaProfiili()  {
        profiiliKohdalla = chooserProfiilit.getSelectedObject();
        
        if (profiiliKohdalla == null) return;
        
        areaProfiili.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaProfiili)) {
            tulosta(os, profiiliKohdalla);
        }
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
        uusi.rekisteroi();
        uusi.taytaAlluTiedoilla(); // TODO: korvaa oikealla dialogilla
        try {
            rekisteri.lisaa(uusi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }
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
                profiili = rekisteri.annaProfiili(i);
                if (profiili.getTunnusNro() == pnro) index = i;
                chooserProfiilit.add(profiili.getNimimerkki(), profiili);
                i++;
            }
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Profiilin hakemisessa ongelmia! " + ex.getMessage());
        }
        
        chooserProfiilit.setSelectedIndex(index);
    }
    
    
    /**
     * Profiilin tietojen muokkaaminen
     */
    private void muokkaa() {
        ModalController.showModal(PeliasetusrekisteriGUIController.class.getResource("ProfiiliDialogView.fxml"), "Muokkaa profiilin tietoja", null, "");
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