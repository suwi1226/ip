package bucket;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 *
 * Each field marked FXML is filled in by the loader from the control carrying the
 * matching fx:id in MainWindow.fxml, so this class never builds any controls itself.
 */
public class MainWindow extends AnchorPane {
    /** How long the goodbye stays on screen before the window closes. */
    private static final Duration EXIT_DELAY = Duration.seconds(1.5);

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Bucket bucket;

    private final Image userImage =
            new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private final Image bucketImage =
            new Image(this.getClass().getResourceAsStream("/images/DaBucket.png"));

    /**
     * Keeps the newest message in view.
     * Called automatically once the FXML has finished loading, which is the
     * earliest point at which the controls above actually exist.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Hands over the chatbot to talk to, and shows its greeting.
     *
     * @param b Chatbot that answers the user's commands.
     */
    public void setBucket(Bucket b) {
        bucket = b;
        dialogContainer.getChildren().add(
                DialogBox.getBucketDialog(bucket.getWelcomeMessage(), bucketImage));
    }

    /**
     * Creates two dialog boxes, one echoing the user's input and the other holding
     * Bucket's reply, and appends them to the dialog container. Clears the user
     * input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();

        // Enter on an empty box would otherwise add two blank dialog boxes.
        if (input.isBlank()) {
            return;
        }

        String response = bucket.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getBucketDialog(response, bucketImage));
        userInput.clear();

        if (Bucket.isExitCommand(input)) {
            handleExit();
        }
    }

    /**
     * Closes the window a moment after the goodbye, so the user can read it.
     * The controls are disabled first, since anything typed during the pause
     * would be lost when the window goes.
     */
    private void handleExit() {
        userInput.setDisable(true);
        sendButton.setDisable(true);

        PauseTransition pause = new PauseTransition(EXIT_DELAY);
        pause.setOnFinished(event -> Platform.exit());
        pause.play();
    }
}
