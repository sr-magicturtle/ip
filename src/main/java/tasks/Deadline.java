package tasks;

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
     * Expects date to be entered in <MMM dd yyyy> numerical format.
     * @param userChoice Represents user's input.
     */
    public Deadline(String userChoice) {
        super(userChoice
                .split("/")[0]
                .substring(9));
        this.endDate = dateHandler(userChoice
                .split("/")[1]
                .substring(3).trim());
    }

    /**
     * Standardises the date format.
     * @param userInputDate Represents user's input.
     * @return Date in correct format.
     */
    public String dateHandler(String userInputDate) {
        LocalDate date = LocalDate.parse(
                userInputDate,
                DateTimeFormatter.ofPattern("MMM dd yyyy"));
        return date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
    }

    /**
     * Find out when the task ends for showing scheduled tasks on a day.
     * @return End date of task.
     */
    @Override
    public LocalDate getDate() {
        return LocalDate.parse(endDate, DateTimeFormatter.ofPattern("MMM dd yyyy"));
    }

    @Override
    public String toString() {
        return "[D] " + super.toString()
                + " | " + this.endDate;
    }

}
