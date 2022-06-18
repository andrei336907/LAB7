package org.andrea.exceptions;

/**
 * thrown  when user doesn't enter required command argument
 */
public class MissedCommandArgumentException extends InvalidCommandArgumentException {
    public MissedCommandArgumentException() {
        super("missed command argument");
    }
}
