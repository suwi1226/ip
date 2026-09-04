package bucket;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Bucket using FXML.
 *
 * This class only builds the window. What the layout looks like is described in
 * MainWindow.fxml, and what the controls do is handled by MainWindow, so visual
 * tweaks do not mean touching Java code.
 */
public class Main extends Application {

    private final Bucket bucket = new Bucket();

    /**
     * Loads the main window and shows it.
     *
     * @param stage The window supplied by the JavaFX runtime.
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);

            stage.setScene(scene);
            stage.setTitle("Bucket");
            stage.setMinHeight(600.0);
            stage.setMinWidth(400.0);

            // The controller is only created while the FXML loads, so the chatbot
            // can only be handed over after that.
            fxmlLoader.<MainWindow>getController().setBucket(bucket);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
