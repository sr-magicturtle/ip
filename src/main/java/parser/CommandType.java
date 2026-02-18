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
        public CommandResult execute(String userChoice, TaskList tasks, Ui ui, Storage storage) {
            ui.sayBye();
            return CommandResult.EXIT;
        }

        @Override
        public String executeForGui(String userChoice, TaskList tasks, Storage storage) {
            return "NOOOOO DON'T GO.... okay see u soon!";
        }
    },
    LIST("list") {
        @Override
        public CommandResult execute(String userChoice, TaskList tasks, Ui ui, Storage storage) {
            ui.giveTaskList(tasks);
            return CommandResult.CONTINUE;
        }

        @Override
        public String executeForGui(String userChoice, TaskList tasks, Storage storage) {
            return buildTaskListResponse(tasks);
        }
    },
    ODO("todo") {
        @Override
        public CommandResult execute(String userChoice, TaskList tasks, Ui ui, Storage storage)
                throws IOException {
            try {
                addTaskAndSave(new ToDo(userChoice), tasks, storage, ui);
            } catch (IllegalArgumentException e) {
                ui.giveError(e.getMessage());
            }
            return CommandResult.CONTINUE;
        }

        @Override
        public String executeForGui(String userChoice, TaskList tasks, Storage storage)
                throws IOException {
            try {
                return addTaskAndSaveForGui(new ToDo(userChoice), tasks, storage);
            } catch (IllegalArgumentException e) {
                return "OOPS!!! " + e.getMessage();
            }
        }
    },

    DEADLINE("deadline") {
        @Override
        public CommandResult execute(String userChoice, TaskList tasks, Ui ui, Storage storage)
                throws IOException {
            try {
                addTaskAndSave(new Deadline(userChoice), tasks, storage, ui);
            } catch (IllegalArgumentException e) {
                ui.giveError(e.getMessage());
            }
            return CommandResult.CONTINUE;
        }

        @Override
        public String executeForGui(String userChoice, TaskList tasks, Storage storage)
                throws IOException {
            try {
                return addTaskAndSaveForGui(new Deadline(userChoice), tasks, storage);
            } catch (IllegalArgumentException e) {
                return "OOPS!!! " + e.getMessage();
            }
        }
    },

    EVENT("event") {
        @Override
        public CommandResult execute(String userChoice, TaskList tasks, Ui ui, Storage storage)
                throws IOException {
            try {
                addTaskAndSave(new Event(userChoice), tasks, storage, ui);
            } catch (IllegalArgumentException e) {
                ui.giveError(e.getMessage());
            }
            return CommandResult.CONTINUE;
        }

        @Override
        public String executeForGui(String userChoice, TaskList tasks, Storage storage)
                throws IOException {
            try {
                return addTaskAndSaveForGui(new Event(userChoice), tasks, storage);
            } catch (IllegalArgumentException e) {
                return "OOPS!!! " + e.getMessage();
            }
        }
    },

    MARK("mark") {
        @Override
        public CommandResult execute(String userChoice, TaskList tasks, Ui ui, Storage storage) {
            try {
                int index = parseTaskIndex(userChoice, 4);
                markAndSave(index, tasks, storage);
                ui.taskMarked(tasks.get(index));
            } catch (IndexOutOfBoundsException | IllegalArgumentException | IOException e) {
                ui.giveError("Invalid task number!");
            }
            return CommandResult.CONTINUE;
        }

        @Override
        public String executeForGui(String userChoice, TaskList tasks, Storage storage)
                throws IOException {
            try {
                int index = parseTaskIndex(userChoice, 4);
                markAndSave(index, tasks, storage);
                return "Nice! I've marked this task as done:\n  " + tasks.get(index);
            } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                return "Invalid task number!";
            }
        }
    },

    UNMARK("unmark") {
        @Override
        public CommandResult execute(String userChoice, TaskList tasks, Ui ui, Storage storage) {
            try {
                int index = parseTaskIndex(userChoice, 6);
                unmarkAndSave(index, tasks, storage);
                ui.taskUnmarked(tasks.get(index));
            } catch (IndexOutOfBoundsException | IllegalArgumentException | IOException e) {
                ui.giveError("Invalid task number!");
            }
            return CommandResult.CONTINUE;
        }

        @Override
        public String executeForGui(String userChoice, TaskList tasks, Storage storage)
                throws IOException {
            try {
                int index = parseTaskIndex(userChoice, 6);
                unmarkAndSave(index, tasks, storage);
                return "OK, I've marked this task as not done yet:\n  " + tasks.get(index);
            } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                return "Invalid task number!";
            }
        }
    },

    DELETE("delete") {
        @Override
        public CommandResult execute(String userChoice, TaskList tasks, Ui ui, Storage storage) {
            try {
                int index = parseTaskIndex(userChoice, 6);
                Task deleted = deleteAndSave(index, tasks, storage);
                ui.taskRemoved(deleted, tasks.size());
            } catch (IndexOutOfBoundsException | IllegalArgumentException | IOException e) {
                ui.giveError("Invalid task number!");
            }
            return CommandResult.CONTINUE;
        }

        @Override
        public String executeForGui(String userChoice, TaskList tasks, Storage storage)
                throws IOException {
            try {
                int index = parseTaskIndex(userChoice, 6);
                Task deleted = deleteAndSave(index, tasks, storage);
                return buildDeleteResponse(deleted, tasks.size());
            } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                return "Invalid task number!";
            }
        }
    },

    FIND("find") {
        @Override
        public CommandResult execute(String userChoice, TaskList tasks, Ui ui, Storage storage) {
            try {
                String keyword = extractKeyword(userChoice);
                ui.showSpecificTasks(storage.findInFile(keyword));
            } catch (IOException e) {
                ui.giveError("Unable to find keyword in file");
            }
            return CommandResult.CONTINUE;
        }

        @Override
        public String executeForGui(String userChoice, TaskList tasks, Storage storage)
                throws IOException {
            String keyword = extractKeyword(userChoice).toLowerCase();
            ArrayList<Task> matches = storage.findInFile(keyword);
            return buildFindResponse(matches);
        }
    },

    SCHEDULE("schedule") {
        @Override
        public CommandResult execute(String userChoice, TaskList tasks, Ui ui, Storage storage) {
            try {
                LocalDate date = parseDate(userChoice);
                ui.showSchedule(date, tasks.getTasksOnDate(date));
            } catch (DateTimeParseException e) {
                ui.giveError("Invalid date format! Use YYYY-MM-DD (e.g., 2026-02-15)");
            } catch (StringIndexOutOfBoundsException e) {
                ui.giveError("Please specify a date: schedule YYYY-MM-DD");
            }
            return CommandResult.CONTINUE;
        }

        @Override
        public String executeForGui(String userChoice, TaskList tasks, Storage storage) {
            try {
                LocalDate date = parseDate(userChoice);
                return buildScheduleResponse(date, tasks.getTasksOnDate(date));
            } catch (DateTimeParseException e) {
                return "Invalid date format! Use YYYY-MM-DD (e.g., 2026-02-15)";
            } catch (StringIndexOutOfBoundsException e) {
                return "Please specify a date: schedule YYYY-MM-DD";
            }
        }
    };

    private static final int FIND_MESSAGE_PREFIX = 5;
    private static final int SCHEDULE_MESSAGE_PREFIX = 9;
    private final String keyword;

    CommandType(String keyword) {
        this.keyword = keyword;
    }

    public abstract CommandResult execute(String userChoice, TaskList tasks, Ui ui, Storage storage)
            throws UnknownCommandException, IOException, IndexOutOfBoundsException;

    public abstract String executeForGui(String userChoice, TaskList tasks, Storage storage)
            throws UnknownCommandException, IOException, IndexOutOfBoundsException;

    /**
     * Create a CommandType object.
     *
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
    private static void addTaskAndSave(Task task, TaskList tasks, Storage storage, Ui ui)
            throws IOException {
        tasks.add(task);
        storage.save(tasks.getTasks());
        ui.taskAdded(task, tasks.size());
    }

    // helper method for adding todo, deadline, event on GUI
    private static String addTaskAndSaveForGui(Task task, TaskList tasks, Storage storage)
            throws IOException {
        tasks.add(task);
        storage.save(tasks.getTasks());
        return String.format("You have added this task:\n  %s\nNow you have %d tasks in the list.",
                task, tasks.size());
    }

    // helper method for parsing index
    private static int parseTaskIndex(String userChoice, int prefixLength) {
        try {
            return Integer.parseInt(userChoice.substring(prefixLength).trim()) - 1;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid task number");
        }
    }

    private static void markAndSave(int index, TaskList tasks, Storage storage)
            throws IOException {
        tasks.mark(index);
        storage.save(tasks.getTasks());
    }

    private static void unmarkAndSave(int index, TaskList tasks, Storage storage)
            throws IOException {
        tasks.unmark(index);
        storage.save(tasks.getTasks());
    }

    private static String buildTaskListResponse(TaskList tasks) {
        StringBuilder sb = new StringBuilder("GET TO WORK!!\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString();
    }

    private static String buildDeleteResponse(Task deleted, int remaining) {
        return String.format("Noted. I've removed this task:\n  %s\nNow you have %d tasks in the list.",
                deleted, remaining);
    }

    private static String buildFindResponse(ArrayList<Task> matches) {
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        if (matches.isEmpty()) {
            sb.append("No matches found!");
        } else {
            for (int i = 0; i < matches.size(); i++) {
                sb.append(i + 1).append(". ").append(matches.get(i)).append("\n");
            }
        }
        return sb.toString();
    }

    private static String buildScheduleResponse(LocalDate date, ArrayList<Task> scheduled) {
        StringBuilder sb = new StringBuilder("Schedule for ")
                .append(date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")))
                .append(":\n");
        if (scheduled.isEmpty()) {
            sb.append("No tasks scheduled for this date!");
        } else {
            for (int i = 0; i < scheduled.size(); i++) {
                sb.append(i + 1).append(". ").append(scheduled.get(i)).append("\n");
            }
        }
        return sb.toString();
    }

    private static Task deleteAndSave(int index, TaskList tasks, Storage storage)
            throws IOException {
        Task deleted = tasks.delete(index);
        storage.save(tasks.getTasks());
        return deleted;
    }

    private static String extractKeyword(String userChoice) {
        return userChoice.substring(FIND_MESSAGE_PREFIX).trim();
    }

    private static LocalDate parseDate(String userChoice) {
        return LocalDate.parse(userChoice.substring(SCHEDULE_MESSAGE_PREFIX).trim());
    }
}
