package storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tasks.Task;
import tasks.ToDo;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StorageTest {

    @TempDir
    Path tempDir;

    @Test
    public void save() throws IOException {
        Path tempPath = tempDir.resolve("tempTasks.txt");
        String tempPathString = tempPath.toString();

        Storage storage = new Storage(tempPathString);

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("todo read"));
        tasks.add(new ToDo("todo write"));

        // Mark the first task
        tasks.get(0).mark();
        storage.save(tasks);
        ArrayList<Task> loadedTasks = storage.load();

        // Check that there is 2 files
        assertEquals(2, loadedTasks.size());

        assertTrue(loadedTasks.get(0).toString().contains("read"));
        assertTrue(loadedTasks.get(0).toString().contains("[X]"));
        assertTrue(loadedTasks.get(1).toString().contains("write"));
    }

    @Test
    public void loadNoFileFound() throws IOException {
        Path tempFilePath = tempDir.resolve("non_existent_file.txt");
        Storage storage = new Storage(tempFilePath.toString());
        ArrayList<Task> loadedTasks = storage.load();
        assertEquals(0, loadedTasks.size());
        File file = new File(tempFilePath.toString());
        assertTrue(file.exists());
    }
}
