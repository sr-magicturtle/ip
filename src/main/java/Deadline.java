public class Deadline extends Task {
    private String endTime;

    public Deadline(String userChoice) {
        super(userChoice.split("/")[0].substring(9));
        this.endTime = userChoice.split("/")[1].substring(3);
    }

    @Override
    public String toString() {
        return "[D] " + super.toString() + " (by: " + this.endTime + ")";
    }

}
