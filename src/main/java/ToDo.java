/**
 * Subclass of Task.
 * A ToDo is a Task, with more specific naming.
 */
public class ToDo extends Task {

    /**
     * Constructs a ToDo through user's input command.
     * @param userChoice Represents user's input.
     */
    public ToDo(String userChoice) {
        super(userChoice.substring(5));
    }

    @Override
    public String toString() {
        return "[T] " + super.toString();
    }


}
