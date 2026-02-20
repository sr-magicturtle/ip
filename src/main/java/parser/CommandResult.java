package parser;

/**
 * Determines whether the program should EXIT or CONTINUE
 * Based on the result derived.
 */
public enum CommandResult {
    EXIT,
    CONTINUE;

    public boolean shouldExit() {
        return this == EXIT;
    }
}
