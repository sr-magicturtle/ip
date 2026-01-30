package main;

import parser.Parser;
import tasks.TaskList;
import storage.Storage;
import ui.Ui;
import exceptions.UnknownCommandException;
import java.io.IOException;


/**
 * Starting point for Roberto, a task manager
 * Add and delete tasks, as well as mark them when done
 */
public class Roberto {
    private static final String FILE_PATH = "Roberto.txt";
    private static final Ui ui = new Ui();
    private static TaskList tasks;
    private static final Storage storage = new Storage(FILE_PATH);

    /**
     * Constructs Roberto app.
     * <p>
     * Loads a file containing the task list from hard drive.
     * If file is not found, show a loading error and create new list.
     * </p>
     */
    public Roberto() {
        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Start the app.
     * <p>
     * Displays welcome message and reads user commands.
     * </p>
     */
    public void run() {
        ui.showWelcome();
        boolean isFinish = false;

        // userChoice must follow one of three formats:
        // todo read book
        // deadline read book /yyyy-mm-dd in numerals
        // event read book /from yyyy-mm-dd /to yyyy-mm-dd in numerals

        while (!isFinish) {
            try {
                String userChoice = ui.readCommand();
                isFinish = Parser.parse(userChoice, tasks, ui, storage);
            } catch (UnknownCommandException e) {
                ui.giveError(e.getMessage());
            } catch (IOException e) {
                ui.giveError("Unable to save to file" + e.getMessage());
            }
        }
    }

    /**
     * Entry point of the app.
     * @param args not used.
     */
    public static void main(String[] args) {
        new Roberto().run();
    }
}
