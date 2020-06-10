package peliasetusrekisteri;

import javafx.application.Platform;
import javafx.fxml.FXML;
import fi.jyu.mit.fxgui.*;

/**
 * Luokka k�ytt�liittym�n tapahtumien hoitamiseksi
 * @author Sami
 * @version 7.6.2020
 *
 */
public class PeliasetusrekisteriGUIController {
    
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
        ModalController.showModal(PeliasetusrekisteriGUIController.class.getResource("ProfiiliDialogView.fxml"), "Uusi profiili", null, "");
    }
    
    
    /**
     * Profiilin tietojen muokkaaminen
     */
    private void muokkaa() {
        ModalController.showModal(PeliasetusrekisteriGUIController.class.getResource("ProfiiliDialogView.fxml"), "Muokkaa profiilin tietoja", null, "");
    }
}