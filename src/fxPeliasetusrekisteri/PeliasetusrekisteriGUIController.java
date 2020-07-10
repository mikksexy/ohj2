package fxPeliasetusrekisteri;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import peliasetusrekisteri.Profiili;
import peliasetusrekisteri.Rekisteri;
import peliasetusrekisteri.Joukkue;
import peliasetusrekisteri.SailoException;

import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.*;

/**
 * Luokka k�ytt�liittym�n tapahtumien hoitamiseksi
 * @author Sami
 * @version 10.7.2020
 *
 */
public class PeliasetusrekisteriGUIController implements Initializable {
    
    @FXML ListChooser<Profiili> chooserProfiilit;
    @FXML private ScrollPane panelProfiili;
    
    

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }
    
    
    /**
     * K�sitell��n tallennusk�sky
     */
    @FXML private void handleTallenna() {
        tallenna();
    }
    
    
    /**
     * K�sitell��n lopetusk�sky
     */
    @FXML private void handleLopeta() {
        tallenna();
        Platform.exit();
    }
    
    
    /**
     * K�sitell��n uuden profiilin lis��minen
     */
    @FXML void handleUusiProfiili() {
        uusiProfiili();
        uusiJoukkue();
        //Dialogs.showMessageDialog("Ei osata viel� lis�t�");
    }
    
    
    /**
     * K�sitell��n profiilin tietojen muokkaaminen
     */
    @FXML void handleMuokkaa() {
        muokkaa();
        //tallenna();
    }
    
    
    /**
     * K�sitell��n profiilin tietojen muokkaaminen
     */
    @FXML void handlePoista() {
        Dialogs.showQuestionDialog("Profiilin poistaminen", "Poistetaanko profiili: allu", "Kyll�", "Ei");
    }
    
    
    /**
     * K�sitell��n avunpyynn�t
     */
    @FXML void handleApua() {
        Dialogs.showMessageDialog("Apuva!");
    }
    
    
    /**
     * K�sitell��n ohjelman tietojen n�ytt�minen
     */
    @FXML private void handleTietoja() {
        //Dialogs.showMessageDialog("Peliasetusrekisteri");
        ModalController.showModal(PeliasetusrekisteriGUIController.class.getResource("TietojaView.fxml"), "Tietoja", null, "");
    }
    
    
    // ===========================================================================================================================
    // Ei-GUI liittyv�t
    
    
    private Rekisteri rekisteri;
    private Profiili profiiliKohdalla;
    private TextArea areaProfiili = new TextArea();
    
    
    /**
     * Alustetaan k�ytt�liittym�
     */
    private void alusta() {
        panelProfiili.setContent(areaProfiili);
        areaProfiili.setFont(new Font("Courier New", 12));
        panelProfiili.setFitToHeight(true);
        
        chooserProfiilit.clear();
        chooserProfiilit.addSelectionListener(e -> naytaProfiili());
    }
    
    
    /**
     * N�ytet��n kohdalla olevan profiilin tiedot
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
     * Tietojen tallennus
     */
    private void tallenna() {
        Dialogs.showMessageDialog("Tallennetaan, mutta ei toimi viel�...");
    }
    
    
    /**
     * Uuden profiilin luominen
     */
    private void uusiProfiili() {
        //ModalController.showModal(PeliasetusrekisteriGUIController.class.getResource("ProfiiliDialogView.fxml"), "Uusi profiili", null, "");
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
        chooserProfiilit.clear();
        
        int index = 0;
        for (int i = 0; i < rekisteri.getProfiileja(); i++) {
            Profiili profiili = rekisteri.annaProfiili(i);
            if (profiili.getTunnusNro() == pnro) index = i;
            chooserProfiilit.add(profiili.getNimimerkki(), profiili);
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