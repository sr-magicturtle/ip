import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Subclass of Task.
 * An Event is a Task with a start date and end date.
 */
public class Event extends Task {
    private final String startDate;
    private final String endDate;

    /**
     * Constructs an Event task through user's input command.
     * Expects date to be entered in <yyyy-mm-dd> numerical format.
     * @param userChoice Represents user's input.
     */
    public Event(String userChoice) {
        super(userChoice.split("/")[0].substring(6));
        this.startDate = dateHandler(userChoice.split("/")[1].substring(5).trim());
        this.endDate = dateHandler(userChoice.split("/")[2].substring(3).trim());
    }

    private String dateHandler(String userInputDate) {
        LocalDate date = LocalDate.parse(userInputDate);
        return date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
    }

    @Override
    public String toString() {
        return "[E] " + super.toString() + " | "
                + this.startDate + " - " + this.endDate;
    }
}
