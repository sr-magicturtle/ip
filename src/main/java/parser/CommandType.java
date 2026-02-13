package parser;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;

import exceptions.UnknownCommandException;
import storage.Storage;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.TaskList;
import tasks.ToDo;
import ui.Ui;

/**
 * Represents valid user commands
 */
public enum CommandType {
    BYE("bye") {
        @Override
        public boolean execute(String userChoice, TaskList tasks, Ui ui, Storage storage) {
            ui.sayBye();
            return true;
        }
        @Override
        public String executeForGui(String userChoice, TaskList tasks, Storage storage) {
            return "NOOOOO DON'T GO.... okay see u soon!";
        }
    },
    LIST("list") {
        @Override
        public boolean execute(String userChoice, TaskList tasks, Ui ui, Storage storage) {
            ui.giveTaskList(tasks);
            return false;
        }
        @Override
        public String executeForGui(String userChoice, TaskList tasks, Storage storage) {
            StringBuilder response = new StringBuilder("GET TO WORK!!\n");
            for (int i = 0; i < tasks.size(); i++) {
                response.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
            }
            return response.toString();
        }
    },
    TODO("todo") {
        @Override
        public boolean execute(String userChoice, TaskList tasks, Ui ui, Storage storage)
                throws IOException {
            try {
                addTaskAndSave(new ToDo(userChoice), tasks, storage, ui);
            } catch (StringIndexOutOfBoundsException e) {
                ui.giveError("You must add a task after typing todo!");
            }
            return false;
        }
        @Override
        public String executeForGui(String userChoice, TaskList tasks, Storage storage)
                throws IOException {
            try {
                Task newTask = new ToDo(userChoice);
                return CommandType.addTaskAndSaveAndDisplayOnGui(newTask, tasks, storage, tasks.size());
            } catch (StringIndexOutOfBoundsException e) {
                return "OOPS!!! You must add a task after typing 'todo'!";
            }
        }
    },
    DEADLINE("deadline") {
        @Override
        public boolean execute(String userChoice, TaskList tasks, Ui ui, Storage storage)
                throws IOException {
            addTaskAndSave(new Deadline(userChoice), tasks, storage, ui);
            return false;
        }
        @Override
        public String executeForGui(String userChoice, TaskList tasks, Storage storage)
                throws IOException {
            try {
                Task newTask = new Deadline(userChoice);
                return CommandType.addTaskAndSaveAndDisplayOnGui(newTask, tasks, storage, tasks.size());
            } catch (StringIndexOutOfBoundsException e) {
                return "OOPS!!! You must add a task after typing 'delete'!";
            }
        }
    },
    EVENT("event") {
        @Override
        public boolean execute(String userChoice, TaskList tasks, Ui ui, Storage storage) {
            try {
                addTaskAndSave(new Event(userChoice), tasks, storage, ui);
            } catch (IOException e) {
                ui.giveError("Unable to write to file");
            }
            return false;
        }
        @Override
        public String executeForGui(String userChoice, TaskList tasks, Storage storage)
                throws IOException {
            try {
                Task newTask = new Event(userChoice);
                return CommandType.addTaskAndSaveAndDisplayOnGui(newTask, tasks, storage, tasks.size());
            } catch (StringIndexOutOfBoundsException e) {
                return "OOPS!!! You must add a task after typing 'event'!";
            }
        }
    },
    MARK("mark") {
        @Override
        public boolean execute(String userChoice, TaskList tasks, Ui ui, Storage storage) {
            int taskNumber = parseTaskIndex(userChoice, 4);
            try {
                tasks.mark(taskNumber);
                storage.save(tasks.getTasks());
                ui.taskMarked(tasks.get(taskNumber));
            } catch (IndexOutOfBoundsException e) {
                ui.giveError("Indexing issue");
            } catch (IOException e) {
                ui.giveError("Unable to mark task");
            }
            return false;
        }
        @Override
        public String executeForGui(String userChoice, TaskList tasks, Storage storage)
                throws IOException {
            try {
                int taskNumber = parseTaskIndex(userChoice, 4);
                tasks.mark(taskNumber);
                storage.save(tasks.getTasks());
                return "Nice! I've marked this task as done:\n  " + tasks.get(taskNumber);
            } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                return "Invalid task number!";
            }
        }
    },
    UNMARK("unmark") {
        @Override
        public boolean execute(String userChoice, TaskList tasks, Ui ui, Storage storage) {
            int taskNumber = parseTaskIndex(userChoice, 6);

            try {
                tasks.unmark(taskNumber);
                storage.save(tasks.getTasks());
                ui.taskUnmarked(tasks.get(taskNumber));
            } catch (IndexOutOfBoundsException e) {
                ui.giveError("Indexing issue");
            } catch (IOException e) {
                ui.giveError("Unable to mark task");
            }
            return false;
        }
        @Override
        public String executeForGui(String userChoice, TaskList tasks, Storage storage)
                throws IOException {
            try {
                int taskNumber = parseTaskIndex(userChoice, 6);
                tasks.unmark(taskNumber);
                storage.save(tasks.getTasks());
                return "OK, I've marked this task as not done yet:\n  " + tasks.get(taskNumber);
            } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                return "Invalid task number!";
            }
        }
    },
    DELETE("delete") {
        @Override
        public boolean execute(String userChoice, TaskList tasks, Ui ui, Storage storage) {
            try {
                int taskNumber = parseTaskIndex(userChoice, 6);
                Task deletedTask = tasks.delete(taskNumber);
                storage.save(tasks.getTasks());
                ui.taskRemoved(deletedTask, tasks.size());
            } catch (IOException e) {
                ui.giveError("Unable to delete task");
            }
            return false;
        }
        @Override
        public String executeForGui(String userChoice, TaskList tasks, Storage storage)
                throws IOException {
            try {
                int taskNumber = parseTaskIndex(userChoice, 6);
                Task deletedTask = tasks.delete(taskNumber);
                storage.save(tasks.getTasks());
                return String.format("Noted. I've removed this task:\n  %s\nNow you have %d tasks in the list.",
                        deletedTask, tasks.size());
            } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                return "Invalid task number!";
            }
        }
    },
    FIND("find") {
        @Override
        public boolean execute(String userChoice, TaskList tasks, Ui ui, Storage storage) {
            try {
                String keyword = userChoice.substring(5).trim();
                ArrayList<Task> tasksWithKeyword = storage.findInFile(keyword);
                ui.showSpecificTasks(tasksWithKeyword);
            } catch (IOException e) {
                ui.giveError("Unable to find keyword in file");
            }
            return false;
        }

        @Override
        public String executeForGui(String userChoice, TaskList tasks, Storage storage)
                throws IOException {
            String keyword = userChoice.substring(5).trim().toLowerCase();
            ArrayList<Task> matchingTasks = storage.findInFile(keyword);
            StringBuilder response = new StringBuilder("Here are the matching tasks in your list:\n");
            if (matchingTasks.isEmpty()) {
                response.append("No matches found!");
            } else {
                for (int i = 0; i < matchingTasks.size(); i++) {
                    response.append(i + 1).append(". ").append(matchingTasks.get(i)).append("\n");
                }
            }
            return response.toString();
        }
    },
    SCHEDULE("schedule") {
        @Override
        public boolean execute(String userChoice, TaskList tasks, Ui ui, Storage storage) {
            try {
                String dateString = userChoice.substring(9).trim();
                LocalDate date = LocalDate.parse(dateString);
                ArrayList<Task> tasksOnDate = tasks.getTasksOnDate(date);
                ui.showSchedule(date, tasksOnDate);
            } catch (DateTimeParseException e) {
                ui.giveError("Invalid date format! Use YYYY-MM-DD (e.g., 2026-02-15)");
            } catch (StringIndexOutOfBoundsException e) {
                ui.giveError("Please specify a date: schedule YYYY-MM-DD");
            }
            return false;
        }
        @Override
        public String executeForGui(String userChoice, TaskList tasks, Storage storage) {
            try {
                String dateStr = userChoice.substring(9).trim();
                LocalDate targetDate = LocalDate.parse(dateStr);

                ArrayList<Task> scheduledTasks = tasks.getTasksOnDate(targetDate);
                StringBuilder response = new StringBuilder();
                response.append("Schedule for ")
                        .append(targetDate.format(
                        DateTimeFormatter.ofPattern("MMM dd yyyy"))).append(":\n");

                if (scheduledTasks.isEmpty()) {
                    response.append("No tasks scheduled for this date!");
                } else {
                    for (int i = 0; i < scheduledTasks.size(); i++) {
                        response.append(i + 1)
                                .append(". ")
                                .append(scheduledTasks.get(i))
                                .append("\n");
                    }
                }
                return response.toString();
            } catch (DateTimeParseException e) {
                return "Invalid date format! Use YYYY-MM-DD (e.g., 2026-02-15)";
            } catch (StringIndexOutOfBoundsException e) {
                return "Please specify a date: schedule YYYY-MM-DD";
            }
        }
    };

    private final String keyword;

    CommandType(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public abstract boolean execute(String userChoice, TaskList tasks, Ui ui, Storage storage)
            throws UnknownCommandException, IOException, IndexOutOfBoundsException;

    public abstract String executeForGui(String userChoice, TaskList tasks, Storage storage)
            throws UnknownCommandException, IOException, IndexOutOfBoundsException;

    /**
     * Create a CommandType object.
     * @param userChoice User's input.
     * @return User's intended command if valid, otherwise return null.
     */
    public static CommandType fromUserChoice(String userChoice) {
        if (userChoice == null || userChoice.trim().isEmpty()) {
            return null;
        }
        String commandWord = userChoice.split(" ")[0].trim();
        return Arrays.stream(values())
                .filter(cmd -> cmd.keyword.equals(commandWord))
                .findFirst()
                .orElse(null);
    }


    // helper method for adding todo, deadline, event on CLI
    private static void addTaskAndSave(Task newTask, TaskList tasks, Storage storage, Ui ui)
            throws IOException {
        tasks.add(newTask);
        storage.save(tasks.getTasks());
        ui.taskAdded(newTask, tasks.size());
    }

    // helper method for adding todo, deadline, event on GUI
    private static String addTaskAndSaveAndDisplayOnGui(Task newTask, TaskList tasks, Storage storage, int tasklistSize)
            throws IOException {
        tasks.add(newTask);
        storage.save(tasks.getTasks());
        return String.format("You have added this task:\n  %s\nNow you have %d tasks in the list.",
                newTask, tasklistSize + 1);
    }

    // helper method for parsing index
    private static int parseTaskIndex(String userChoice, int prefixLength) {
        try {
            return Integer.parseInt(userChoice.substring(prefixLength).trim()) - 1;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid task number");
        }
    }
}
