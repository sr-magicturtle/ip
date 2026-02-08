package main;

import dialog.DialogBox;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.jpeg"));
    private Image robertoImage = new Image(this.getClass().getResourceAsStream("/images/Roberto.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        // Show welcome message
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
        String input = userInput.getText();
        String response = roberto.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getRobertoDialog(response, robertoImage)
        );
        userInput.clear();
    }
}
