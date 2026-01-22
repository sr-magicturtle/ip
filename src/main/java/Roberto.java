import java.util.Scanner;
import java.util.List;

public class Roberto {
    private static Task[] listOfTasks = new Task[100];
    private static int numOfTasks = 0;

    public static void main(String[] args) {
        helloGreeting();

        Scanner scanner = new Scanner(System.in);
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
        System.out.println("Hello! I'm ROBERTO");
        System.out.println(logo);
        System.out.println("What can I do for you?");
    }

    private static void printListWithNumber(Task[] list, int listLength) {
        System.out.println("GET TO WORK!!");
        for (int i=0; i < listLength; i++) {
            System.out.println( (i+1) + ". " + list[i].toString());
        }
    }

    private static void handleInput(String userChoice) throws UnknownCommandException {
        if (userChoice.equals("list")) {
            printListWithNumber(listOfTasks, numOfTasks);
        }

        else if (userChoice.startsWith("todo")) {
            try {
                Task newTask = new ToDo(userChoice);
                listOfTasks[numOfTasks] = newTask;
                numOfTasks++;
                System.out.println("Added a todo task!\nYou now have " + numOfTasks + " tasks in your list");
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("You must add a task after typing todo!");
            }
        }

        else if (userChoice.startsWith("deadline")) {
            Task newTask = new Deadline(userChoice);
            listOfTasks[numOfTasks] = newTask;
            numOfTasks++;
            System.out.println("Added a deadline task!\nYou now have " + numOfTasks + " tasks in your list");
        }

        else if (userChoice.startsWith("event")) {
            String[] input = userChoice.split("/");
            input[0] = input[0].substring(6);
            input[1] = input[1].substring(5);
            input[2] = input[2].substring(3);
            listOfTasks[numOfTasks] = new Event(input[0], input[1], input[2]);
            numOfTasks++;
            System.out.println("Added an event task!\nYou now have " + numOfTasks + " tasks in your list");
        }
        else if (userChoice.startsWith("mark")) {
            int taskNumber = (userChoice.charAt(5) - '0') - 1;
            listOfTasks[taskNumber].mark();
            System.out.println("This task is marked done!\n" +
                    listOfTasks[taskNumber].toString()
            );
        }
        else if (userChoice.startsWith("unmark")) {
            int taskNumber = (userChoice.charAt(7) - '0') - 1;
            listOfTasks[taskNumber].unmark();
            System.out.println("this task is marked undone.\n" +
                    listOfTasks[taskNumber].toString()
            );
        }
        else {
            throw new UnknownCommandException("I dont understand that command");
        }
    }
}
