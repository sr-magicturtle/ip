package main;

import dialog.DialogBox;
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
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Roberto roberto;

    private final Image userImage = loadImage("/images/User.jpeg");
    private final Image robertoImage = loadImage("/images/Roberto.jpg");

    /**
     * Shows the first message from Roberto.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.prefWidthProperty().bind(scrollPane.widthProperty());
        String welcomeMessage = "Hello! I'm ROBERTO\nWhat can I do for you?";
        dialogContainer.getChildren().add(
                DialogBox.getRobertoDialog(welcomeMessage, robertoImage)
        );
    }

    /** Injects the Roberto instance */
    public void setRoberto(Roberto r) {
        roberto = r;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Roberto's reply
     * and then appends them to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (input.isEmpty()) {
            return;
        }

        String response = roberto.getResponse(input);

        if (userImage != null && robertoImage != null) {
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getRobertoDialog(response, robertoImage)
            );
        }

        userInput.clear();

        if (roberto.getLastResult().shouldExit()) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
    }

    private Image loadImage(String filePath) {
        var image = new Image(this.getClass().getResourceAsStream(filePath));
        if (image == null) {
            System.out.println("Image is not found at " + filePath);
            return null;
        }
        return image;
    }
}
