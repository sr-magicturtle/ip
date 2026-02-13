package tasks;

import java.util.ArrayList;

/**
 * Manage list of tasks.
 * Methods to add, delete, get, and change status of tasks.
 * Keeps an internal list within application.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs empty list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs list with existing tasks.
     * @param tasks Existing tasks to be added to list.
     */
    public TaskList(ArrayList<Task> tasks) {
        assert tasks != null : "Tasklist cannot be null";
        this.tasks = tasks;
    }

    /**
     * Add task to internal list.
     * @param task Task to be added.
     */
    public void add(Task task) {
        assert task != null : "Cannot add null task";
        this.tasks.add(task);
    }

    /**
     * Remove task from list.
     * @param index Task number to be removed.
     * @return Task that was removed.
     */
    public Task delete(int index) {
        assert index >= 0 && index < size() : "Invalid index";
        return this.tasks.remove(index);
    }

    /**
     * Get task at index.
     * @param index Task number to be retrieved.
     * @return Task at index.
     */
    public Task get(int index) {
        assert index >= 0 && index < size() : "Invalid index";
        return this.tasks.get(index);
    }

    /**
     * Return number of tasks in list.
     * @return Number of tasks.
     */
    public int size() {
        return this.tasks.size();
    }

    /**
     * Retrieve tasks as ArrayList.
     * @return ArrayList of tasks.
     */
    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Marks task as completed.
     * @param index Task number to be marked.
     */
    public void mark(int index) {
        assert index >= 0 && index < size() : "Invalid index";
        this.tasks.get(index).mark();
    }

    /**
     * Marks task as uncompleted.
     * @param index Task number to be unmarked.
     */
    public void unmark(int index) {
        assert index >= 0 && index < size() : "Invalid index";
        this.tasks.get(index).unmark();
    }

}
