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
 * Luokka k�ytt�liittym�n tapahtumien hoitamiseksi
 * @author Sami
 * @version 31.7.2020
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
     * K�sitell��n hakuehto
     */
    @FXML private void handleHakuehto() {
        hae(0);
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
    }
    
    
    /**
     * K�sitell��n profiilin tietojen muokkaaminen
     */
    @FXML void handleMuokkaa() {
        muokkaa();
    }
    
    
    /**
     * K�sitell��n profiilin tietojen muokkaaminen
     */
    @FXML void handlePoista() {
        poista();
    }
    
    
    /**
     * K�sitell��n avunpyynn�t
     */
    @FXML void handleApua() {
        avustus();
    }
    
    
    /**
     * K�sitell��n ohjelman tietojen n�ytt�minen
     */
    @FXML private void handleTietoja() {
        ModalController.showModal(PeliasetusrekisteriGUIController.class.getResource("TietojaView.fxml"), "Tietoja", null, "");
    }
    
    
    // ===========================================================================================================================
    // Ei-GUI liittyv�t
    
    
    private Rekisteri rekisteri;
    private Profiili profiiliKohdalla;
    private TextField edits[];
    
    
    /**
     * Alustetaan k�ytt�liittym�
     */
    private void alusta() {
        
        chooserProfiilit.clear();
        chooserProfiilit.addSelectionListener(e -> naytaProfiili());
        
        edits = new TextField[] { editNimi, editJoukkue, editHerkkyys, editDPI, editTarkkuus, editKuvasuhde, editSkaalaus, editTaajuus, editEdpi, editEdpiKa };
    }
    
    
    /**
     * N�ytet��n kohdalla olevan profiilin tiedot
     */
    private void naytaProfiili()  {
        profiiliKohdalla = chooserProfiilit.getSelectedObject();
        
        if (profiiliKohdalla == null) return;
        profiiliKohdalla.setEdpi();
        edits[8].setText(String.format("%.1f", profiiliKohdalla.getEdpi()));
        edits[9].setText(String.format("%.1f",rekisteri.edpiKa()));
        
        ProfiiliDialogController.naytaProfiili(edits, profiiliKohdalla, rekisteri.annaJoukkue(profiiliKohdalla.getJoukkue()));
        
        
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
     * @return null jos onnistuu, muuten virhe tekstin�
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
     * @return null jos onnistuu, muuten virhe tekstin�
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
        uusi = ProfiiliDialogController.kysyProfiili(null, uusi, rekisteri, new Joukkue());
        if ( uusi == null ) return;
        uusi.rekisteroi();
        rekisteri.lisaa(uusi);
        hae(uusi.getTunnusNro());
    }
    
    
    private void poista() {
        Profiili profiili = profiiliKohdalla;
        if ( profiili == null ) return;
        if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko profiili: " + profiili.getNimimerkki(), "Kyll�", "Ei") )
            return;
        rekisteri.poista(profiili);
        int index = chooserProfiilit.getSelectedIndex();
        hae(0);
        chooserProfiilit.setSelectedIndex(index);
    }
    
    
    /**
     * Haetaan profiili
     * @param pnr profiilinumero, jota haetaan
     */
    private void hae(int pnr) {
        int pnro = pnr;
        if ( pnro <= 0 ) {
            Profiili kohdalla = profiiliKohdalla;
            if ( kohdalla != null ) pnro = kohdalla.getTunnusNro();
        }
        
        int k = cbKentat.getSelectionModel().getSelectedIndex();
        String ehto = hakuehto.getText(); 
        if (ehto.indexOf('*') < 0) ehto = "*" + ehto + "*";
        
        chooserProfiilit.clear();
        
        int index = 0;
        Collection<Profiili> profiilit;
        try {
            if ( k == 1 ) profiilit = rekisteri.etsi(ehto);
            else profiilit = rekisteri.etsi(ehto, k);
            int i = 0;
            for (Profiili profiili : profiilit) {
                if (profiili.getTunnusNro() == pnro) index = i;
                chooserProfiilit.add(profiili.getNimimerkki(), profiili);
                i++;
            }
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Profiilin hakemisessa ongelmia! " + ex.getMessage());
        }
        
        chooserProfiilit.setSelectedIndex(index);
        //chooserProfiilit.getSelectionModel().select(index);
    }
    
    
    /**
     * Profiilin tietojen muokkaaminen
     */
    private void muokkaa() {
        if ( profiiliKohdalla == null ) return; 
            try { 
                Profiili profiili; 
                profiili = ProfiiliDialogController.kysyProfiili(null, profiiliKohdalla.clone(), rekisteri, rekisteri.annaJoukkue(profiiliKohdalla.getJoukkue())); 
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
     * N�ytet��n ohjelman suunnitelma erillisess� selaimessa.
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
        Joukkue joukkue = rekisteri.annaJoukkue(profiili.getJoukkue());
        joukkue.tulosta(os);
    }
    
}