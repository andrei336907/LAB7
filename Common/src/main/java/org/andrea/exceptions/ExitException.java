package org.andrea.exceptions;

/**
 * thrown when program is interrupted
 */
public class ExitException extends CommandException {
    public ExitException() {
        super("shutting down...");
    }
}
