package tasks;

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
     * Expects date to be entered in <MMM dd yyyy> numerical format.
     * @param userChoice Represents user's input.
     */
    public Event(String userChoice) {
        super(extractDescription(userChoice));
        String[] parts = userChoice.split("/from", 2);
        if (parts.length < 2 || !parts[1].contains("/to")) {
            throw new IllegalArgumentException("Invalid event format! Expected: "
                    + "event <desc> /from <MMM dd yyyy> /to <MMM dd yyyy>");
        }
        String[] dateParts = parts[1].split("/to", 2);
        String rawStartDate = dateParts[0].trim();
        String rawEndDate = dateParts[1].trim();

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");

        LocalDate startDate;
        LocalDate endDate;

        try {
            startDate = LocalDate.parse(rawStartDate, dateFormat);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid start date format! Expected: MMM dd yyyy (e.g. Jan 01 2026)");
        }

        try {
            endDate = LocalDate.parse(rawEndDate, dateFormat);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid end date format! Expected: MMM dd yyyy (e.g. Jan 01 2026)");
        }

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException(
                    "End date must not be before start date!");
        }

        this.startDate = startDate.format(dateFormat);
        this.endDate = endDate.format(dateFormat);
    }

    private static String extractDescription(String userChoice) {
        String[] parts = userChoice.split("/from", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new IllegalArgumentException("Event must have a '/from' date!");
        }
        if (!parts[1].contains("/to")) {
            throw new IllegalArgumentException("Event must have a '/to' date!");
        }
        String description = parts[0].substring(6).trim();
        if (description.isEmpty()) {
            throw new IllegalArgumentException("Event description cannot be empty!");
        }
        return description;
    }

    /**
     * Format the date.
     * @param userInputDate Represents user's input.
     * @return Date in correct format.
     */
    public static String dateHandler(String userInputDate) {
        try {
            LocalDate date = LocalDate.parse(
                    userInputDate,
                    DateTimeFormatter.ofPattern("MMM dd yyyy"));
            return date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid date format! Expected: MMM dd yyyy (e.g. Jan 01 2026)");
        }
    }

    /**
     * Find out when the task starts for showing scheduled tasks on a day.
     * @return Start date of task.
     */
    @Override
    public LocalDate getDate() {
        return LocalDate.parse(startDate, DateTimeFormatter.ofPattern("MMM dd yyyy"));
    }

    @Override
    public String toString() {
        return "[E] " + super.toString() + " | "
                + this.startDate + " - " + this.endDate;
    }
}
