package tasks;

/**
 * A basic †ask with a description and a completed status.
 * Subclasses can extend from this class to have more detailed functionalities.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Construct a Task with a description, and set to not completed.
     * @param description Represents user's task title.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Gets status of task
     * @return Whether the task is completed.
     */
    public boolean getStatus() {
        return isDone;
    }

    /**
     * Mark task as completed.
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Marks task as uncompleted.
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Returns the string representation of a task.
     * @return Formatted string, with an indicator of whether it is completed.
     */
    public String toString() {
        char taskDone = this.isDone ? 'X' : ' ';
        return "[" + taskDone + "] " + this.description;
    }
}
