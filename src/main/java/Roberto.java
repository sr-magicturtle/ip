import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Roberto {
    private static ArrayList<String> listOfTasksInFile = new ArrayList<>();
    private static ArrayList<Task> listOfTasks = new ArrayList<>();
    private static int numOfTasks = 0;
    private static final String FILE_PATH = "Roberto.txt";

    public static void main(String[] args) {
        helloGreeting();

        listOfTasksInFile = readFile();
        numOfTasks = listOfTasksInFile.size();

        Scanner scanner = new Scanner(System.in);

        // userChoice must follow one of three formats:
        // todo read book
        // deadline read book /by Aug 6th
        // event read book /from Aug 6th 12pm /to Aug 6th 4pm
        String userChoice = scanner.nextLine();

        while (!userChoice.equals("bye")) {
            try {
                handleInput(userChoice);
            } catch (UnknownCommandException e) {
                System.out.println(e.getMessage());
            }
            userChoice = scanner.nextLine();
        }

        System.out.println("NOOOOO DONT GO.... okay see u soon!");
    }

    private static void helloGreeting() {
        String logo = "__________ ________ __________________________________________________   \n" +
                "\\______   \\\\_____  \\\\______   \\_   _____/\\______   \\__    ___/\\_____  \\  \n" +
                " |       _/ /   |   \\|    |  _/|    __)_  |       _/ |    |    /   |   \\ \n" +
                " |    |   \\/    |    \\    |   \\|        \\ |    |   \\ |    |   /    |    \\\n" +
                " |____|_  /\\_______  /______  /_______  / |____|_  / |____|   \\_______  /\n" +
                "        \\/         \\/       \\/        \\/         \\/                   \\/ ";
        System.out.println("Hello! I'm ROBERTO\n" +
                logo +
                "\nWhat can I do for you?"
        );
    }

    private static void printListWithNumber(ArrayList<String> list) {
        System.out.println("GET TO WORK!!");
        for (int i=0; i < list.size(); i++) {
            System.out.println( (i+1) + ". " + list.get(i));
        }
    }

    private static void handleInput(String userChoice) throws UnknownCommandException {
        File file = new File(FILE_PATH);

        if (userChoice.equals("list")) {
            listOfTasksInFile = readFile();
            printListWithNumber(listOfTasksInFile);
        }

        else if (userChoice.startsWith("todo")) {
            try {
                Task newTask = new ToDo(userChoice);
                listOfTasks.add(newTask);

                String taskDescription = newTask.toString() + "\n";
                writeToFile(taskDescription);
                numOfTasks++;

                System.out.println("Added a todo task!\nYou now have " + numOfTasks + " tasks in your list");

            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("You must add a task after typing todo!");
            } catch (IOException e) {
                System.out.println("Unable to write to file");
            }
        }

        else if (userChoice.startsWith("deadline")) {
            try {
                Task newTask = new Deadline(userChoice);
                listOfTasks.add(newTask);

                String taskDescription = newTask.toString() + "\n";
                writeToFile(taskDescription);
                numOfTasks++;

                System.out.println("Added a deadline task!\nYou now have " + numOfTasks + " tasks in your list");

            } catch (IOException e) {
                System.out.println("Unable to write to file");
            }
        }

        else if (userChoice.startsWith("event")) {
            try {
                Task newTask = new Event(userChoice);
                listOfTasks.add(newTask);

                String taskDescription = newTask.toString() + "\n";
                writeToFile(taskDescription);
                numOfTasks++;

                System.out.println("Added an event task!\nYou now have " + numOfTasks + " tasks in your list");
                
            } catch (IOException e) {
                System.out.println("Unable to write to file");
            }
        }

        else if (userChoice.startsWith("mark")) {
            int taskNumber = (userChoice.charAt(5) - '0') - 1;

            try {
                markTaskInFile(taskNumber);
                // listOfTasks.get(taskNumber).mark();
                System.out.println("This task is marked done!\n" +
                        listOfTasksInFile.get(taskNumber)
                );
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Indexing issue");

            } catch (IOException e) {
                System.out.println("Unable to mark task");
            }
        }

        else if (userChoice.startsWith("unmark")) {
            int taskNumber = (userChoice.charAt(7) - '0') - 1;

            try {
                unmarkTaskInFile(taskNumber);
                // listOfTasksInFile.get(taskNumber).mark();
                System.out.println("this task is marked undone.\n" +
                        listOfTasksInFile.get(taskNumber)
                );
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Indexing issue");

            } catch (IOException e) {
                System.out.println("Unable to mark task");
            }
        }

        else if (userChoice.startsWith("delete")) {
            int taskNumber = (userChoice.charAt(7) - '0') - 1;
            String removedTask = listOfTasksInFile.get(taskNumber);
            listOfTasks.remove(taskNumber);
            
            removeLineInFile(taskNumber);
            numOfTasks--;
            
            System.out.println("this task is removed.\n" +
                    removedTask +
                    "\nYou have " + 
                    numOfTasks + 
                    " tasks left in the list"
            );
        }
        else {
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
            System.out.println(e.getMessage());
        }
        return linesOfTasks;
    }

    private static void writeToFile(String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(FILE_PATH, true);
        fw.write(textToAdd);
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

    private static void removeLineInFile(int lineNum) {
        Path path = Paths.get(FILE_PATH);

        try {
            ArrayList<String> allLines = readFile();
            allLines.remove(lineNum - 1);
            Files.write(path,allLines);
        } catch (IOException e) {
            System.out.println("Unable to remove line");
        }

    }
}
