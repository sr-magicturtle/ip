import java.util.Scanner;

public class Roberto {
    public static void main(String[] args) {
        helloGreeting();

        Scanner scanner = new Scanner(System.in);
        String userChoice = scanner.nextLine();
        while (!userChoice.equals("bye")) {
            System.out.println(userChoice);
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
}
