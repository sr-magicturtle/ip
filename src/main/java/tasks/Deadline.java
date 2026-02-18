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
        super(extractDescription(userChoice));
        String[] parts = userChoice.split("/by", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new IllegalArgumentException("Deadline must have a '/by' date!");
        }
        this.endDate = dateHandler(parts[1].trim());
    }

    private static String extractDescription(String userChoice) {
        String[] parts = userChoice.split("/by", 2);
        String description = parts[0].substring(9).trim();
        if (description.isEmpty()) {
            throw new IllegalArgumentException("Deadline description cannot be empty!");
        }
        return description;
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
