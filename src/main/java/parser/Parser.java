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

public class Parser {

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
}
