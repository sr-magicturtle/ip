import java.util.Scanner;

public class Roberto {
    public static void main(String[] args) {
        helloGreeting();

        String[] listTasks = new String[100];
        int numOfListElements = 0;

        Scanner scanner = new Scanner(System.in);
        String userChoice = scanner.nextLine();

        while (!userChoice.equals("bye")) {
            if (userChoice.equals("list")) {
                printListWithNumber(listTasks, numOfListElements);
                userChoice = scanner.nextLine();
            }
            else {
                System.out.println("added: " + userChoice);
                listTasks[numOfListElements] = userChoice;
                numOfListElements++;
                userChoice = scanner.nextLine();
            }
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

    private static void printListWithNumber(String[] list, int listLength) {
        for (int i=0; i < listLength; i++) {
            System.out.println( (i+1) + ". " + list[i]);
        }
    }
}
