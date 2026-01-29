/**
 * Represents an exception when user enters a command that is not supported.
 */
public class UnknownCommandException extends Exception {
    /**
     * Creates a {@code UnknownCommandException} with the given message.
     * @param message Description of the error that will be displayed.
     */
    public UnknownCommandException(String message) {
        super(message);
    }
}
