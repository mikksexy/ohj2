package peliasetusrekisteri;

import javafx.application.Platform;
import javafx.fxml.FXML;
import fi.jyu.mit.fxgui.*;

/**
 * Luokka käyttöliittymän tapahtumien hoitamiseksi
 * @author Sami
 * @version 6.6.2020
 * @version 7.6.2020
 *
 */
public class PeliasetusrekisteriGUIController {
    
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
        //Dialogs.showMessageDialog("Ei osata vielä lisätä");
    }
    
    
    /**
     * Käsitellään profiilin tietojen muokkaaminen
     */
    @FXML void handleMuokkaa() {
        muokkaa();
        //tallenna();
    }
    
    
    /**
     * Käsitellään profiilin tietojen muokkaaminen
     */
    @FXML void handlePoista() {
        Dialogs.showQuestionDialog("Profiilin poistaminen", "Poistetaanko profiili: allu", "Kyllä", "Ei");
    }
    
    
    /**
     * Käsitellään ohjelman tietojen näyttäminen
     */
    @FXML private void handleTietoja() {
        //Dialogs.showMessageDialog("Peliasetusrekisteri");
        ModalController.showModal(PeliasetusrekisteriGUIController.class.getResource("TietojaView.fxml"), "Tietoja", null, "");
    }
    
    
    /**
     * Tietojen tallennus
     */
    private void tallenna() {
        Dialogs.showMessageDialog("Tallennetaan, mutta ei toimi vielä...");
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