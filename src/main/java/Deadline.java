import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private String endTime;

    public Deadline(String userChoice) {
        super(userChoice.split("/")[0].substring(9));
        this.endTime = dateHandler(userChoice.split("/")[1].substring(3).trim());
    }

    public String dateHandler(String userInputDate) {
        LocalDate date = LocalDate.parse(userInputDate);
        return date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
    }

    @Override
    public String toString() {
        return "[D] " +
                super.toString() +
                " | by: " +
                this.endTime;
    }

}
