package ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import tasks.Task;
import tasks.TaskList;

/**
 * Handles user inputs and displays outputs and errors.
 */
public class Ui {
    private Scanner scanner;

    /**
     * Constructor for UI.
     * Reads user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Display welcome message.
     */
    public void showWelcome() {
        String logo = "__________ ________ __________________________________________________   \n"
                + "\\______   \\\\_____  \\\\______   \\_   _____/\\______   \\__    ___/\\_____  \\  \n"
                + " |       _/ /   |   \\|    |  _/|    __)_  |       _/ |    |    /   |   \\ \n"
                + " |    |   \\/    |    \\    |   \\|        \\ |    |   \\ |    |   /    |    \\\n"
                + " |____|_  /\\_______  /______  /_______  / |____|_  / |____|   \\_______  /\n"
                + "        \\/         \\/       \\/        \\/         \\/                   \\/ ";
        System.out.println("Hello! I'm ROBERTO\n"
                + logo
                + "\nWhat can I do for you?");
    }

    /**
     * Read next line of user input.
     * @return user input.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Display error message.
     * @param message The error message.
     */
    public void giveError(String message) {
        System.out.println("OOPS!!! " + message);
    }

    /**
     * Display error message regarding a missing file.
     */
    public void showLoadingError() {
        System.out.println("Error loading file. Starting with an empty list.");
    }

    /**
     * Displays byebye when the app terminates.
     */
    public void sayBye() {
        System.out.println("NOOOOO DONT GO.... okay see u soon!");
    }

    /**
     * Displays list of tasks.
     * Includes task numbers.
     * @param list TaskList containing Tasks to be displayed.
     */
    public void giveTaskList(TaskList list) {
        System.out.println("GET TO WORK!!");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
    }

    /**
     * Displays that a task has been added.
     * Displays total number of tasks.
     * @param task Task to be added.
     * @param size Current number of tasks.
     */
    public void taskAdded(Task task, int size) {
        System.out.println("You have added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
    }

    /**
     * Displays that a task has been removed.
     * Displays total number of tasks.
     * @param task Task to be removed.
     * @param size Current number of tasks.
     */
    public void taskRemoved(Task task, int size) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
    }

    /**
     * Displays that a task has been marked.
     * @param task Task to be marked.
     */
    public void taskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    /**
     * Displays that a task has been unmarked.
     * @param task Task to be unmarked.
     */
    public void taskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    public void showSpecificTasks(ArrayList<Task> tasks) {
        System.out.println("Here are the matching tasks in your list: ");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    /**
     * Displays schedule for a specific date.
     * @param date Target date.
     * @param tasks Tasks on that date.
     */
    public void showSchedule(LocalDate date, ArrayList<Task> tasks) {
        String formattedDate = date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        System.out.println("Schedule for " + formattedDate + ":");
        if (tasks.isEmpty()) {
            System.out.println("No tasks scheduled for this date!");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

}
