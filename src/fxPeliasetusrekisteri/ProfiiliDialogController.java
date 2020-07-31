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
import peliasetusrekisteri.Profiili;

/**
 * @author Sami
 * @version 30.7.2020
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
            naytaVirhe("Nimi ei saa olla tyhj‰!");
            return;
        }
        ModalController.closeStage(labelVirhe);
    }
    
// =====================================================================
    private Profiili profiiliKohdalla;
    private TextField edits[];
    
    
    /**
     * Tekee tarvittavat muut alustukset. Laittaa mm. edit-kentist‰ tulevan tapahtuman
     * kasitteleMuutosProfiili-metodiin ja vie sille kent‰nnumeron parametrina.
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
        case 2 : virhe = ""; break;//profiiliKohdalla.setJoukkue(s); break;
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
     * TODO:
     * @param edits
     * @param profiili
     */
    public static void naytaProfiili(TextField[] edits, Profiili profiili) {
        if ( profiili == null ) return;
        edits[0].setText(profiili.getNimimerkki());
        edits[1].setText("");//joukkueet.annaJoukkue(profiili.getJoukkue()));
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


    /**
     * Mit‰ tehd‰‰n, kun dialogi on n‰ytetty.
     */
    @Override
    public void handleShown() {
        editNimi.requestFocus();
        
    }


    @Override
    public void setDefault(Profiili oletus) {
        profiiliKohdalla = oletus;
        naytaProfiili(edits, profiiliKohdalla);
        
    }


    /**
     * Luodaan j‰senen kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * TODO: korjattava toimimaan
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mit‰ dataan n‰ytet‰‰n oletuksena
     * @return null jos painetaan Cancel, muuten t‰ytetty tietue
     */
    public static Profiili kysyProfiili(Stage modalityStage, Profiili oletus) {
        return ModalController.<Profiili, ProfiiliDialogController>showModal(
                    ProfiiliDialogController.class.getResource("ProfiiliDialogView.fxml"),
                    "Profiilin muokkaus",
                    modalityStage, oletus, null 
                );
    }

}
