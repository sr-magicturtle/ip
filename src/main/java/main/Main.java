package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Roberto.
 */
public class Main extends Application {
    private final Roberto roberto = new Roberto("Roberto.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Roberto Task Manager");
            stage.setResizable(true);
            stage.setMinWidth(400.0);
            stage.setMinHeight(600.0);
            fxmlLoader.<MainWindow>getController().setRoberto(roberto);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

