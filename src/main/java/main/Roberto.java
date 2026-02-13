package main;

import java.io.IOException;

import exceptions.UnknownCommandException;
import parser.Parser;
import storage.Storage;
import tasks.TaskList;
import ui.Ui;

/**
 * Represents Roberto Task Manager.
 */
public class Roberto {
    private static final String FILEPATH = "Roberto.txt";
    private Ui ui = new Ui();
    private TaskList tasks;
    private Storage storage;

    /**
     * Creates a Roberto object.
     * @param filePath Indicate filepath to create Roberto.txt.
     */
    public Roberto(String filePath) {
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Generates a response for user's input.
     */
    public String getResponse(String input) {
        try {
            return Parser.parseForGui(input, tasks, storage);
        } catch (UnknownCommandException e) {
            return "OOPS!!! " + e.getMessage();
        } catch (IOException e) {
            return "Unable to save to file: " + e.getMessage();
        }
    }

    /**
     * Keep the run() method for CLI mode
     */
    public void run() {
        ui.showWelcome();
        boolean isFinish = false;
        while (!isFinish) {
            try {
                String userChoice = ui.readCommand();
                isFinish = Parser.parse(userChoice, tasks, ui, storage);
            } catch (UnknownCommandException e) {
                ui.giveError(e.getMessage());
            } catch (IOException e) {
                ui.giveError("Unable to save to file: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Roberto(FILEPATH).run();
    }
}
