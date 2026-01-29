import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Subclass of Task.
 * A Deadline is a Task with a start date and end date.
 */
public class Deadline extends Task {
    private final String endDate;

    /**
     * Constructs a Deadline task through user's input command.
     * Expects date to be entered in <yyyy-mm-dd> numerical format.
     * @param userChoice Represents user's input.
     */
    public Deadline(String userChoice) {
        super(userChoice.split("/")[0].substring(9));
        this.endDate = dateHandler(userChoice.split("/")[1].substring(3).trim());
    }

    public String dateHandler(String userInputDate) {
        LocalDate date = LocalDate.parse(userInputDate);
        return date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
    }

    @Override
    public String toString() {
        return "[D] " + super.toString()
                + " | " + this.endDate;
    }

}
