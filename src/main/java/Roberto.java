import java.util.Scanner;

public class Roberto {
    public static void main(String[] args) {
        helloGreeting();

        Task[] listOfTasks = new Task[100];
        int numOfTasks = 0;

        Scanner scanner = new Scanner(System.in);
        String userChoice = scanner.nextLine();

        while (!userChoice.equals("bye")) {
            if (userChoice.equals("list")) {
                printListWithNumber(listOfTasks, numOfTasks);
            }
            else if (userChoice.startsWith("mark")) {
                int taskNumber = (userChoice.charAt(5) - '0') - 1;
                listOfTasks[taskNumber].mark();
                System.out.println("This task is marked done!\n" +
                        "[X] " + listOfTasks[taskNumber].toString()
                );
            }
            else if (userChoice.startsWith("unmark")) {
                int taskNumber = (userChoice.charAt(7) - '0') - 1;
                listOfTasks[taskNumber].unmark();
                System.out.println("this task is marked undone.\n" +
                        "[ ] " + listOfTasks[taskNumber].toString()
                );
            }
            else {
                System.out.println("added: " + userChoice);
                listOfTasks[numOfTasks] = new Task(userChoice);
                numOfTasks++;
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
            char taskDone = list[i].getStatus() ? 'X' : ' ';
            System.out.println( (i+1) + ". [" + taskDone + "] " + list[i]);
        }
    }
}
