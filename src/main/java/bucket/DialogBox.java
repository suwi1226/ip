package bucket;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 *
 * One of these is created per message, so the layout is loaded from DialogBox.fxml
 * with the fx:root construct: the box being built is set as both root and controller,
 * which lets the same FXML be reused for every message.
 */
public class DialogBox extends HBox {
    /** A dialog box is always a label beside a picture, which is what flip() reorders. */
    private static final int EXPECTED_CHILD_COUNT = 2;

    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Creates a dialog box showing one message beside its speaker's picture.
     *
     * @param text Message to show.
     * @param img Picture of whoever said it.
     */
    private DialogBox(String text, Image img) {
        assert text != null : "a dialog box needs text to show";
        assert img != null : "a dialog box needs a picture to show";

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // The IOException above is caught rather than rethrown, so a failed load lets
        // execution continue to the two lines below with both fields still null. These
        // assertions name the missing fx:id instead of leaving a bare NullPointerException.
        assert dialog != null : "fx:id \"dialog\" missing from DialogBox.fxml";
        assert displayPicture != null : "fx:id \"displayPicture\" missing from DialogBox.fxml";

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Returns a dialog box for something the user said, picture on the right.
     *
     * @param text Message the user typed.
     * @param img Picture of the user.
     * @return Dialog box to add to the conversation.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Returns a dialog box for something Bucket said, flipped so the picture is
     * on the left. The two speakers are told apart by which side they sit on.
     *
     * @param text Message Bucket replied with.
     * @param img Picture of Bucket.
     * @return Dialog box to add to the conversation.
     */
    public static DialogBox getBucketDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.flip();
        return db;
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        // Reversing only distinguishes the two speakers while there are exactly two
        // children. Adding a third to the FXML would silently change what flip means.
        assert getChildren().size() == EXPECTED_CHILD_COUNT
                : "a dialog box holds exactly a label and a picture";

        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }
}
