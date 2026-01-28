import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private final String startTime;
    private final String endTime;

    public Event(String userChoice) {
        super(userChoice.split("/")[0].substring(6));
        this.startTime = dateHandler(userChoice.split("/")[1].substring(5).trim());
        this.endTime = dateHandler(userChoice.split("/")[2].substring(3).trim());
    }

    public String dateHandler(String userInputDate) {
        LocalDate date = LocalDate.parse(userInputDate);
        return date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
    }

    @Override
    public String toString() {
        return "[E] " +
                super.toString() +
                " | " +
                this.startTime +
                " - " +
                this.endTime;
    }
}
