package fxPeliasetusrekisteri;

import javafx.application.Application;
import javafx.stage.Stage;
import peliasetusrekisteri.Rekisteri;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * Ohjelmassa pidetään kirjaa profiilien peliasetuksista
 * @author Sami
 * @version 7.6.2020
 */
public class PeliasetusrekisteriMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("PeliasetusrekisteriGUIView.fxml"));
            final Pane root = ldr.load();
            final PeliasetusrekisteriGUIController peliasetusrekisteriCtrl = (PeliasetusrekisteriGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("peliasetusrekisteri.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Peliasetusrekisteri");
            
            Rekisteri rekisteri = new Rekisteri();
            peliasetusrekisteriCtrl.setRekisteri(rekisteri);
            
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Käynnistää käyttöliittymän
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        launch(args);
    }
}