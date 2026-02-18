package dialog;

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
import main.MainWindow;

/**
 * Represents a dialog box with an ImageView for profile pic and Label for text.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load DialogBox.fxml", e);
        }


        dialog.setText(text);
        displayPicture.setImage(img);
    }

    private void setUserStyle() {
        dialog.setStyle("-fx-background-color: rgba(173, 216, 230, 0.85);"
                + "-fx-background-radius: 10;"
                + "-fx-border-color: rgba(0, 0, 0, 0.1);"
                + "-fx-border-radius: 10;"
                + "-fx-padding: 8 12 8 12;"
                + "-fx-text-fill: black;"
        );
    }

    private void setRobertoStyle() {
        dialog.setStyle("-fx-background-color: rgba(255, 255, 255, 0.85);"
                + "-fx-background-radius: 10;"
                + "-fx-border-color: rgba(0, 0, 0, 0.1);"
                + "-fx-border-radius: 10;"
                + "-fx-padding: 8 12 8 12;"
                + "-fx-text-fill: black;"
        );
    }

    /**
     * Flips so that ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    public static DialogBox getUserDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.setUserStyle();
        db.setAlignment(Pos.CENTER_RIGHT);
        return db;
    }

    public static DialogBox getRobertoDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        db.setRobertoStyle();
        db.setAlignment(Pos.CENTER_LEFT);
        return db;
    }
}
