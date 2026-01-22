public class Task {
    protected String description;
    protected boolean isDone;

    public Task() {
        this.description = "";
        this.isDone = false;
    }

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public boolean getStatus() {
        return isDone;
    }

    public void mark() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    public String toString() {
        char taskDone = this.isDone ? 'X' : ' ';
        return "[" + taskDone + "] " + this.description;
    }
}
