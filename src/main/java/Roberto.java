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
    private static ArrayList<String> listOfTasksInFile = new ArrayList<>();
    private static ArrayList<Task> listOfTasks = new ArrayList<>();
    private static int numOfTasks = 0;
    private static Ui ui = new Ui();

    /**
     * Runs Roberto the task manager, until user enters the command "bye".
     * @param args not used.
     */
    public static void main(String[] args) {
        ui.showWelcome();

        listOfTasksInFile = readFile();
        numOfTasks = listOfTasksInFile.size();

        // userChoice must follow one of three formats:
        // todo read book
        // deadline read book /by yyyy-mm-dd in numerals
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
            listOfTasksInFile = readFile();
            ui.giveTaskList(listOfTasksInFile);
        } else if (userChoice.startsWith("todo")) {
            try {
                Task newTask = new ToDo(userChoice);
                // listOfTasks.add(newTask);
                String taskDescription = newTask.toString();
                writeToFile(taskDescription);
                numOfTasks++;

                ui.taskAdded(taskDescription, listOfTasksInFile.size());

            } catch (StringIndexOutOfBoundsException e) {
                ui.giveError("You must add a task after typing todo!");
            } catch (IOException e) {
                ui.giveError("Unable to write to file");
            }
        } else if (userChoice.startsWith("deadline")) {
            try {
                Task newTask = new Deadline(userChoice);
                // listOfTasks.add(newTask);
                String taskDescription = newTask.toString();
                writeToFile(taskDescription);
                numOfTasks++;

                ui.taskAdded(taskDescription, listOfTasksInFile.size());

            } catch (IOException e) {
                ui.giveError("Unable to write to file");
            }
        } else if (userChoice.startsWith("event")) {
            try {
                Task newTask = new Event(userChoice);
                //listOfTasks.add(newTask);
                String taskDescription = newTask.toString();
                writeToFile(taskDescription);
                numOfTasks++;

                ui.taskAdded(taskDescription, listOfTasksInFile.size());

            } catch (IOException e) {
                ui.giveError("Unable to write to file");
            }
        } else if (userChoice.startsWith("mark")) {
            int taskNumber = (userChoice.charAt(5) - '0') - 1;

            try {
                markTaskInFile(taskNumber);
                // listOfTasks.get(taskNumber).mark();
                ui.taskMarked(listOfTasksInFile.get(taskNumber));
            } catch (IndexOutOfBoundsException e) {
                ui.giveError("Indexing issue");

            } catch (IOException e) {
                ui.giveError("Unable to mark task");
            }
        } else if (userChoice.startsWith("unmark")) {
            int taskNumber = (userChoice.charAt(7) - '0') - 1;

            try {
                unmarkTaskInFile(taskNumber);
                // listOfTasksInFile.get(taskNumber).mark();
                ui.taskMarked(listOfTasksInFile.get(taskNumber));
            } catch (IndexOutOfBoundsException e) {
                ui.giveError("Indexing issue");

            } catch (IOException e) {
                ui.giveError("Unable to mark task");
            }
        } else if (userChoice.startsWith("delete")) {
            int taskNumber = (userChoice.charAt(7) - '0') - 1;
            String removedTask = listOfTasksInFile.get(taskNumber);
            // listOfTasks.remove(taskNumber);
            
            removeLineInFile(taskNumber);
            // numOfTasks--;
            
            ui.taskRemoved(removedTask, listOfTasksInFile.size());
        } else {
            throw new UnknownCommandException("I dont understand that command");
        }
    }

    private static ArrayList<String> readFile() {
        ArrayList<String> linesOfTasks = new ArrayList<>();
        File file = new File(FILE_PATH);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                linesOfTasks.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            ui.showLoadingError();
        }
        return linesOfTasks;
    }

    private static void writeToFile(String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(FILE_PATH, true);
        fw.write(textToAdd);
        fw.write(System.lineSeparator());
        fw.close();
    }

    private static void markTaskInFile(int taskNumber) throws IOException{
        Path path = Paths.get(FILE_PATH);

        ArrayList<String> allLines = readFile();
        String targetLine = allLines.get(taskNumber);

        StringBuilder sb = new StringBuilder(targetLine);
        sb.setCharAt(5, '1');
        allLines.set(taskNumber, sb.toString());

        Files.write(path, allLines);
    }

    private static void unmarkTaskInFile(int taskNumber) throws IOException{
        Path path = Paths.get(FILE_PATH);

        ArrayList<String> allLines = readFile();
        String targetLine = allLines.get(taskNumber);

        StringBuilder sb = new StringBuilder(targetLine);
        sb.setCharAt(5, '0');
        allLines.set(taskNumber, sb.toString());

        Files.write(path, allLines);
    }

    private static void removeLineInFile(int taskNumber) {
        Path path = Paths.get(FILE_PATH);

        try {
            ArrayList<String> allLines = readFile();
            allLines.remove(taskNumber);
            Files.write(path,allLines);
        } catch (IOException e) {
            ui.giveError("Unable to show line");
        }

    }
}
