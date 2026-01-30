package storage;

import tasks.Task;
import tasks.ToDo;
import tasks.Deadline;
import tasks.Event;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles the loading and saving of tasks to a file.
 */
public class Storage {
    private String filePath;

    /**
     * Construct Storage object.
     * @param filePath The relative or absolute path to the file where tasks are stored.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Load tasks from file.
     *
     * @return ArrayList of Tasks loaded from file.
     * @throws IOException If an error occurs during file creation or reading.
     */
    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(this.filePath);

        if (!file.exists()) {
            file.createNewFile();
            return tasks;
        }

        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Task task = parseLineToTask(line);
            tasks.add(task);
        }

        return tasks;
    }

    /**
     * Saves list of tasks to file.
     *
     * @param tasks List of tasks to save.
     * @throws IOException If an error occurs during writing.
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            fw.write(task.toString() + System.lineSeparator());
        }
        fw.close();
    }


    private Task parseLineToTask(String line) {
        Task result;

        boolean isDone = line.contains("[X]");

        if (line.contains("[T]")) {
            result = new ToDo("todo " + line.substring(8));
        } else if (line.contains("[D]")) {
            line = line.substring(8);
            String description = line.split("\\|")[0];
            String startDate = line.split("\\|")[1];
            String originalInput = "deadline " + description + "/by " + startDate;
            result = new Deadline(originalInput);
        } else if (line.contains("[E]")) {
            line = line.substring(8);
            String description = line.split("\\|")[0];

            String startAndEndDates = line.split("\\|")[1];
            String startDate = startAndEndDates.split("-")[0].trim();
            String endDate = startAndEndDates.split("-")[1].trim();
            String originalInput = "event " + description + "/from " + startDate + "/by " + endDate;
            result = new Event(originalInput);
        } else {
            result = null;
        }

        if (isDone) {
            result.mark();
        }
        return result;
    }


}
