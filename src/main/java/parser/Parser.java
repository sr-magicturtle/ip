package parser;

import java.io.IOException;

import exceptions.UnknownCommandException;
import storage.Storage;
import tasks.TaskList;
import ui.Ui;

/**
 * Handles execution of user's input.
 */
public class Parser {
    private static CommandResult lastResult = CommandResult.CONTINUE;
    /**
     * Process user's command for CLI.
     * @param userChoice User input.
     * @param tasks      List of tasks.
     * @param ui         Handle user input and output to screen.
     * @param storage    Storage for saving data.
     * @return true if command is executed.
     * @throws UnknownCommandException Invalid commands.
     * @throws IOException Invalid inputs.
     * @throws IndexOutOfBoundsException Invalid inputs.
     */
    public static CommandResult parse(String userChoice, TaskList tasks, Ui ui, Storage storage)
            throws UnknownCommandException, IOException {
        CommandType command = CommandType.fromUserChoice(userChoice);
        if (command == null) {
            throw new UnknownCommandException("I don't understand that command!");
        }
        return command.execute(userChoice, tasks, ui, storage);
    }

    /**
     * Parses the user input and executes the command.
     * @param userChoice User input.
     * @param tasks User input.
     * @param storage Storage for saving data.
     * @return String based on Roberto's programmed response.
     * @throws UnknownCommandException Invalid inputs.
     * @throws IOException Invalid inputs.
     */
    public static String parseForGui(String userChoice, TaskList tasks, Storage storage)
            throws UnknownCommandException, IOException {
        CommandType command = CommandType.fromUserChoice(userChoice);
        if (command == null) {
            throw new UnknownCommandException("I don't understand that command!");
        }
        GuiResponse response = command.executeForGui(userChoice, tasks, storage);
        lastResult = response.commandResult();
        return response.message();
    }

    public static CommandResult getLastCommandResult() {
        return lastResult;
    }
}
