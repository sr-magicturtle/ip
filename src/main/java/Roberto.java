import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Starting point for Roberto, a task manager
 * Add and delete tasks, as well as mark them when done
 */
public class Roberto {
    private static final String FILE_PATH = "Roberto.txt";
    private static Ui ui = new Ui();
    private static TaskList tasks;
    private static Storage storage = new Storage(FILE_PATH);  
    /**
     * Runs Roberto the task manager, until user enters the command "bye".
     * @param args not used.
     */
    public static void main(String[] args) {
        
        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }

        ui.showWelcome();
        
        // userChoice must follow one of three formats:
        // todo read book
        // deadline read book /yyyy-mm-dd in numerals
        // event read book /from yyyy-mm-dd /to yyyy-mm-dd in numerals
        String userChoice = ui.readCommand();

        while (!userChoice.equals("bye")) {
            try {
                handleInput(userChoice);
            } catch (UnknownCommandException e) {
                ui.giveError(e.getMessage());
            }
            userChoice = ui.readCommand();
        }
        ui.sayBye();
    }

    private static void handleInput(String userChoice) throws UnknownCommandException {
        if (userChoice.equals("list")) {
            ui.giveTaskList(tasks);
        } else if (userChoice.startsWith("todo")) {
            try {
                Task newTask = new ToDo(userChoice);
                tasks.add(newTask);
                storage.save(tasks.getTasks());
                ui.taskAdded(newTask, tasks.size());

            } catch (StringIndexOutOfBoundsException e) {
                ui.giveError("You must add a task after typing todo!");
            } catch (IOException e) {
                ui.giveError("Unable to write to file");
            }
        } else if (userChoice.startsWith("deadline")) {
            try {
                Task newTask = new Deadline(userChoice);
                tasks.add(newTask);
                storage.save(tasks.getTasks());
                ui.taskAdded(newTask, tasks.size());

            } catch (IOException e) {
                ui.giveError("Unable to write to file");
            }
        } else if (userChoice.startsWith("event")) {
            try {
                Task newTask = new Event(userChoice);
                tasks.add(newTask);
                storage.save(tasks.getTasks());
                ui.taskAdded(newTask, tasks.size());
            } catch (IOException e) {
                ui.giveError("Unable to write to file");
            }
        } else if (userChoice.startsWith("mark")) {
            int taskNumber = (userChoice.charAt(5) - '0') - 1;

            try {
                tasks.mark(taskNumber);
                storage.save(tasks.getTasks());
                ui.taskMarked(tasks.get(taskNumber));
            } catch (IndexOutOfBoundsException e) {
                ui.giveError("Indexing issue");

            } catch (IOException e) {
                ui.giveError("Unable to mark task");
            }
        } else if (userChoice.startsWith("unmark")) {
            int taskNumber = (userChoice.charAt(7) - '0') - 1;

            try {
                tasks.unmark(taskNumber);
                storage.save(tasks.getTasks());
                ui.taskUnmarked(tasks.get(taskNumber));
            } catch (IndexOutOfBoundsException e) {
                ui.giveError("Indexing issue");
            } catch (IOException e) {
                ui.giveError("Unable to mark task");
            }
        } else if (userChoice.startsWith("delete")) {
            try {
                int taskNumber = (userChoice.charAt(7) - '0') - 1;

                Task deletedTask = tasks.delete(taskNumber);
                storage.save(tasks.getTasks());
                ui.taskRemoved(deletedTask, tasks.size());
            } catch (IOException e) {
                ui.giveError("Unable to delete task");
            }
        } else {
            throw new UnknownCommandException("I dont understand that command");
        }
    }
    
}
