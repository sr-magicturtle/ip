public class ToDo extends Task {

    public ToDo(String userChoice) {
        super(userChoice.substring(5));
    }

    @Override
    public String toString() {
        return "[T] " + super.toString();
    }


}
