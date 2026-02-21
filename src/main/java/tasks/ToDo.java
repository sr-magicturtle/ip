package tasks;

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

    private static String extractDescription(String userChoice) {
        String description = userChoice.substring(5).trim(); // strip "todo "
        if (description.isEmpty()) {
            throw new IllegalArgumentException(
                    "ToDo description cannot be empty! Usage: todo <description>"
            );
        }
        return description;
    }

    @Override
    public String toString() {
        return "[T] " + super.toString();
    }


}
