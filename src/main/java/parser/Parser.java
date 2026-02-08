package parser;

import storage.Storage;
import ui.Ui;
import tasks.TaskList;
import tasks.Task;
import tasks.ToDo;
import tasks.Deadline;
import tasks.Event;
import exceptions.UnknownCommandException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Handles execution of user's input.
 */
public class Parser {

    /**
     * Parses the user input and executes the command.
     *
     * @param userChoice User input.
     * @param tasks      List of tasks.
     * @param ui         UI handling user interaction.
     * @param storage    Storage for saving data.
     * @return true if the program exits, else false.
     * @throws UnknownCommandException If the command is not recognised.
     */
    public static boolean parse(String userChoice, TaskList tasks, Ui ui, Storage storage) throws UnknownCommandException, IOException {
        if (userChoice.equals("bye")) {
            ui.sayBye();
            return true;
        } else if (userChoice.equals("list")) {
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
        } else if (userChoice.startsWith("find")) {
            String keyword = userChoice
                    .substring(5)
                    .trim();
            ArrayList<Task> tasksWithKeyword = storage.findInFile(keyword);
            ui.showSpecificTasks(tasksWithKeyword);
        } else {
            throw new UnknownCommandException("I dont understand that command");
        }
        return false;
    }

    /**
     * Parses the user input and executes the command.
     *
     * @param userChoice User input.
     * @param tasks      List of tasks.
     * @param storage    Storage for saving data.
     * @return String based on Roberto's programmed response.
     */
    public static String parseForGui(String userChoice, TaskList tasks, Storage storage)
            throws UnknownCommandException, IOException {
        StringBuilder response = new StringBuilder();

        if (userChoice.equals("bye")) {
            return "NOOOOO DON'T GO.... okay see u soon!";

        } else if (userChoice.equals("list")) {
            response.append("GET TO WORK!!\n");
            for (int i = 0; i < tasks.size(); i++) {
                response.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
            }
            return response.toString();

        } else if (userChoice.startsWith("todo ")) {
            try {
                Task newTask = new ToDo(userChoice);
                tasks.add(newTask);
                storage.save(tasks.getTasks());
                response.append("You have added this task:\n");
                response.append("  ").append(newTask).append("\n");
                response.append("Now you have ").append(tasks.size()).append(" tasks in the list.");
                return response.toString();
            } catch (StringIndexOutOfBoundsException e) {
                return "OOPS!!! You must add a task after typing 'todo'!";
            }

        } else if (userChoice.startsWith("deadline ")) {
            Task newTask = new Deadline(userChoice);
            tasks.add(newTask);
            storage.save(tasks.getTasks());
            response.append("You have added this task:\n");
            response.append("  ").append(newTask).append("\n");
            response.append("Now you have ").append(tasks.size()).append(" tasks in the list.");
            return response.toString();

        } else if (userChoice.startsWith("event ")) {
            Task newTask = new Event(userChoice);
            tasks.add(newTask);
            storage.save(tasks.getTasks());
            response.append("You have added this task:\n");
            response.append("  ").append(newTask).append("\n");
            response.append("Now you have ").append(tasks.size()).append(" tasks in the list.");
            return response.toString();

        } else if (userChoice.startsWith("mark ")) {
            int taskNumber = Character.getNumericValue(userChoice.charAt(5)) - 1;
            tasks.mark(taskNumber);
            storage.save(tasks.getTasks());
            response.append("Nice! I've marked this task as done:\n");
            response.append("  ").append(tasks.get(taskNumber));
            return response.toString();

        } else if (userChoice.startsWith("unmark ")) {
            int taskNumber = Character.getNumericValue(userChoice.charAt(7)) - 1;
            tasks.unmark(taskNumber);
            storage.save(tasks.getTasks());
            response.append("OK, I've marked this task as not done yet:\n");
            response.append("  ").append(tasks.get(taskNumber));
            return response.toString();

        } else if (userChoice.startsWith("delete ")) {
            int taskNumber = Character.getNumericValue(userChoice.charAt(7)) - 1;
            Task deletedTask = tasks.delete(taskNumber);
            storage.save(tasks.getTasks());
            response.append("Noted. I've removed this task:\n");
            response.append("  ").append(deletedTask).append("\n");
            response.append("Now you have ").append(tasks.size()).append(" tasks in the list.");
            return response.toString();

        } else if (userChoice.startsWith("find ")) {
            String keyword = userChoice.substring(5).trim().toLowerCase();
            ArrayList<Task> matchingTasks = storage.findInFile(keyword);
            response.append("Here are the matching tasks in your list:\n");
            if (matchingTasks.isEmpty()) {
                response.append("No matches found!");
            } else {
                for (int i = 0; i < matchingTasks.size(); i++) {
                    response.append((i + 1)).append(". ").append(matchingTasks.get(i)).append("\n");
                }
            }
            return response.toString();

        } else {
            throw new UnknownCommandException("I don't understand that command");
        }
    }

}
