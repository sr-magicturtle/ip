import java.io.IOException;


/**
 * Starting point for Roberto, a task manager
 * Add and delete tasks, as well as mark them when done
 */
public class Roberto {
    private static final String FILE_PATH = "Roberto.txt";
    private static Ui ui = new Ui();
    private static TaskList tasks;
    private static Storage storage = new Storage(FILE_PATH);

    public Roberto(String filePath) {
        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

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
     * Runs Roberto the task manager, until user enters the command "bye".
     * @param args not used.
     */
    public static void main(String[] args) {
        new Roberto(FILE_PATH).run();
    }
}
