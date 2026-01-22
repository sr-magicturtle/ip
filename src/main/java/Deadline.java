public class Deadline extends Task {

    private String startTime;
    private String endTime;

    public Deadline(String description, String endTime) {
        super(description);
        this.startTime = "";
        this.endTime = endTime;
    }

    public Deadline(String description, String startTime, String endTime) {
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        if (this.startTime.isEmpty()) {
            return "[D] " + super.toString() + " (by: " + this.endTime + ")";
        }
        return "[D] " + super.toString() + " (from: " + this.startTime + " to: " + this.endTime + ")";
    }

}
