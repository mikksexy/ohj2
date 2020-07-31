package fxPeliasetusrekisteri;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import peliasetusrekisteri.Joukkue;
import peliasetusrekisteri.Profiili;
import peliasetusrekisteri.Rekisteri;

/**
 * @author Sami
 * @version 31.7.2020
 *
 */
public class ProfiiliDialogController implements ModalControllerInterface<Profiili>,Initializable {

    @FXML private TextField editNimi;
    @FXML private TextField editJoukkue;
    @FXML private TextField editHerkkyys;
    @FXML private TextField editDPI;
    @FXML private TextField editTarkkuus;
    @FXML private TextField editKuvasuhde;
    @FXML private TextField editSkaalaus;
    @FXML private TextField editTaajuus;
    @FXML private Label labelVirhe;
    
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }

    
    @FXML private void handleCancel() {
        profiiliKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }
    

    @FXML private void handleTallenna() {
        if ( profiiliKohdalla != null && profiiliKohdalla.getNimimerkki().trim().equals("") ) {
            naytaVirhe("Nimi ei saa olla tyhj�!");
            return;
        }
        ModalController.closeStage(labelVirhe);
    }
    
// =================================================================================================
    private Profiili profiiliKohdalla;
    private TextField edits[];
    private Rekisteri rekisteri;
    private Joukkue joukkue;
    
    
    /**
     * Tekee tarvittavat muut alustukset. Laittaa mm. edit-kentist� tulevan tapahtuman
     * kasitteleMuutosProfiili-metodiin ja vie sille kent�nnumeron parametrina.
     */
    protected void alusta() {
        edits = new TextField[] { editNimi, editJoukkue, editHerkkyys, editDPI, editTarkkuus, editKuvasuhde, editSkaalaus, editTaajuus };
        int i = 0;
        for (TextField edit : edits) {
            final int k = ++i;
            edit.setOnKeyReleased(e -> kasitteleMuutosProfiiliin(k, (TextField)(e.getSource())));
        }
    }


    /**
     * TODO:
     * @param k
     * @param edit
     */
    private void kasitteleMuutosProfiiliin(int k, TextField edit) {
        if ( profiiliKohdalla == null ) return;
        String s = edit.getText();
        String virhe = null;
        switch (k) {
        case 1 : virhe = profiiliKohdalla.setNimi(s); break;
        case 2 : virhe = asetaJoukkue(s); break;
        case 3 : virhe = profiiliKohdalla.setHerkkyys(s); break;
        case 4 : virhe = profiiliKohdalla.setDPI(s); break;
        case 5 : virhe = profiiliKohdalla.setTarkkuus(s); break;
        case 6 : virhe = profiiliKohdalla.setKuvasuhde(s); break;
        case 7 : virhe = profiiliKohdalla.setSkaalaus(s); break;
        case 8 : virhe = profiiliKohdalla.setTaajuus(s); break;
        default:
        }
        if ( virhe == null ) {
            Dialogs.setToolTipText(edit, "");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit, virhe);
            edit.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        }
    }
    
    
    /**
     * Uuden joukkueen luominen
     * @param nimi
     
    private String uusiJoukkue(String nimi) {
        if ( nimi == null || nimi.equals("")) return nimi;
        int onko = rekisteri.onkoMuita2(nimi);
        int muita = rekisteri.onkoMuita(nimi);
        if ( onko != 0 ) return profiiliKohdalla.setJoukkue(muita);
        if ( onko < 1 ) rekisteri.poista(joukkue);

        joukkue = new Joukkue(nimi);
        joukkue.rekisteroi();
        return profiiliKohdalla.setJoukkue(rekisteri.korvaaTaiLisaa(joukkue));
        // joukkue = new Joukkue(nimi);
        // joukkue.rekisteroi();
        // if(profiiliKohdalla.getJoukkue() == null --> uusijoukkue() if (nimi equals onkoMuita(); setJoukkue(muita)
    }*/
    
    
    private String asetaJoukkue(String nimi) {
        if ( nimi == null || nimi.equals("")) return nimi;
        //if ( rekisteri.annaJoukkue(profiiliKohdalla.getJoukkue()).getNimi() )
        if ( profiiliKohdalla.getJoukkue() == 0 ) return uusiJoukkue(nimi);
        int onko = rekisteri.onkoMuita2(nimi);
        if ( onko < 1 ) rekisteri.annaJoukkue(profiiliKohdalla.getJoukkue()).setNimi(nimi);
        /*if ( onko == 1 &&  rekisteri.annaJoukkue(profiiliKohdalla.getJoukkue()).getNimi().equals(nimi) ) {
            //rekisteri.poista(rekisteri.annaJoukkue(profiiliKohdalla.getJoukkue()));
            return profiiliKohdalla.setJoukkue(rekisteri.onkoMuita(nimi));
        }
        if ( onko == 1 &&  !rekisteri.annaJoukkue(profiiliKohdalla.getJoukkue()).getNimi().equals(nimi) ) {
            //rekisteri.poista(rekisteri.annaJoukkue(profiiliKohdalla.getJoukkue()));
            return profiiliKohdalla.setJoukkue(rekisteri.onkoMuita(nimi));
        }*/
        /*if ( onko > 1 &&  rekisteri.annaJoukkue(profiiliKohdalla.getJoukkue()).getNimi().equals(nimi) ) {
            //rekisteri.poista(rekisteri.annaJoukkue(profiiliKohdalla.getJoukkue()));
            return profiiliKohdalla.setJoukkue(rekisteri.onkoMuita(nimi));
        }*/
        /*if ( onko > 1 &&  !rekisteri.annaJoukkue(profiiliKohdalla.getJoukkue()).getNimi().equals(nimi) ) {
            //rekisteri.poista(rekisteri.annaJoukkue(profiiliKohdalla.getJoukkue()));
            //return uusiJoukkue(nimi);
            return rekisteri.annaJoukkue(profiiliKohdalla.getJoukkue()).setNimi(nimi);
        }*/
        
        if ( onko >= 1 && rekisteri.onkoMuita2(rekisteri.annaJoukkue(profiiliKohdalla.getJoukkue()).getNimi()) != 0 ) {
            return profiiliKohdalla.setJoukkue(rekisteri.onkoMuita(nimi));
        }
        
        if ( onko < 1 && rekisteri.onkoMuita2(rekisteri.annaJoukkue(profiiliKohdalla.getJoukkue()).getNimi()) != 0 ) {
            return profiiliKohdalla.setJoukkue(rekisteri.onkoMuita(nimi));
        }
        
        if ( onko < 1 && rekisteri.onkoMuita2(rekisteri.annaJoukkue(profiiliKohdalla.getJoukkue()).getNimi()) == 0 ) {
            return rekisteri.annaJoukkue(profiiliKohdalla.getJoukkue()).setNimi(nimi);
        }
        return null;
    }
    
    
    private String uusiJoukkue(String nimi) {
        joukkue = new Joukkue(nimi);
        joukkue.rekisteroi();
        return profiiliKohdalla.setJoukkue(rekisteri.korvaaTaiLisaa(joukkue));
    }


    /**
     * TODO:
     * @param virhe
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
     * N�ytet��n profiilin tiedot tekstialueissa
     * @param edits lista tekstialueista, joissa tiedot n�kyv�t
     * @param profiili Profiili, joka n�ytet��n
     * @param joukkue Joukkue, joka n�ytet��n
     */
    public static void naytaProfiili(TextField[] edits, Profiili profiili, Joukkue joukkue) {
        if ( profiili == null ) return;
        edits[0].setText(profiili.getNimimerkki());
        edits[1].setText(joukkue.getNimi());
        edits[2].setText(Double.toString(profiili.getHerkkyys()));
        edits[3].setText(Integer.toString(profiili.getDPI()));
        edits[4].setText(profiili.getTarkkuus());
        edits[5].setText(profiili.getKuvasuhde());
        edits[6].setText(profiili.getSkaalaus());
        edits[7].setText(Integer.toString(profiili.getTaajuus()));
    }


    @Override
    public Profiili getResult() {
        return profiiliKohdalla;
    }

    
    @Override
    public void setDefault(Profiili oletus) {
        profiiliKohdalla = oletus;
    }
    
    
    /**
     * Asetetaan rekisteri, jota halutaan muokata
     * @param rekisteri
     */
    private void setRekisteri(Rekisteri rekisteri) {
        this.rekisteri = rekisteri;
    }
    
    
    /**
     * Asetetaan profiilin joukkue
     * @param joukkue Profiilin joukkue
     */
    private void setJoukkue(Joukkue joukkue) {
        this.joukkue = joukkue;
    }


    /**
     * Mit� tehd��n, kun dialogi on n�ytetty.
     */
    @Override
    public void handleShown() {
        editNimi.requestFocus();
        naytaProfiili(edits, profiiliKohdalla, joukkue);
    }


    /**
     * Luodaan profiilin kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mit� dataan n�ytet��n oletuksena
     * @param rekisteri luokka josta tarvitaan tietoa
     * @param joukkue Joukkue joka tuodaan muokattavaksi
     * @return null jos painetaan Cancel, muuten t�ytetty tietue
     */
    public static Profiili kysyProfiili(Stage modalityStage, Profiili oletus, Rekisteri rekisteri, Joukkue joukkue) {
        return ModalController.<Profiili, ProfiiliDialogController>showModal(
                    ProfiiliDialogController.class.getResource("ProfiiliDialogView.fxml"),
                    "Profiilin muokkaus",
                    modalityStage, oletus, 
                    ctrl -> { ctrl.setRekisteri(rekisteri); ctrl.setJoukkue(joukkue); }
                );
    }
}
