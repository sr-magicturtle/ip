/**
 * Represent's a Roberto-specific exception.
 */
public class RobertoException extends Exception {
    /**
     * Creates a {@code RobertoException} with given error message.
     * @param errorMessage Description of the error that will be displayed.
     */
    public RobertoException(String errorMessage) {
        super(errorMessage);
    }
}
