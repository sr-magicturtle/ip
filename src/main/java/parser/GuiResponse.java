package parser;

/**
 * Contains response from GUI and CommandResult.
 */
public record GuiResponse(String message, CommandResult commandResult) {

    public static GuiResponse of(String message) {
        return new GuiResponse(message, CommandResult.CONTINUE);
    }

    public static GuiResponse exit(String message) {
        return new GuiResponse(message, CommandResult.EXIT);
    }
}
