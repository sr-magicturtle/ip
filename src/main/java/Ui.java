import java.util.Scanner;
import java.util.ArrayList;

public class Ui {
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        String logo = "__________ ________ __________________________________________________   \n" +
                "\\______   \\\\_____  \\\\______   \\_   _____/\\______   \\__    ___/\\_____  \\  \n" +
                " |       _/ /   |   \\|    |  _/|    __)_  |       _/ |    |    /   |   \\ \n" +
                " |    |   \\/    |    \\    |   \\|        \\ |    |   \\ |    |   /    |    \\\n" +
                " |____|_  /\\_______  /______  /_______  / |____|_  / |____|   \\_______  /\n" +
                "        \\/         \\/       \\/        \\/         \\/                   \\/ ";
        System.out.println("Hello! I'm ROBERTO\n" + logo + "\nWhat can I do for you?");
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void giveError(String message) {
        System.out.println("OOPS!!! " + message);
    }

    public void showLoadingError() {
        System.out.println("Error loading file. Starting with an empty list.");
    }

    public void sayBye() {
        System.out.println("NOOOOO DONT GO.... okay see u soon!");
    }

    public void giveTaskList(TaskList list) {
        System.out.println("GET TO WORK!!");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
    }

    public void taskAdded(Task task, int size) {
        System.out.println("You have added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
    }

    public void taskRemoved(Task task, int size) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
    }

    public void taskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    public void taskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }
}
