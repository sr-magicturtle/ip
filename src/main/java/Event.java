public class Event extends Task {
    private final String startTime;
    private final String endTime;

    public Event(String userChoice) {
        super(userChoice.split("/")[0].substring(6));
        this.startTime = userChoice.split("/")[1].substring(5);
        this.endTime = userChoice.split("/")[2].substring(3);
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
