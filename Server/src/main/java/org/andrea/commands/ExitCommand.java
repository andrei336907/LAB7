package org.andrea.commands;


import org.andrea.exceptions.ExitException;

public class ExitCommand extends CommandImpl {
    public ExitCommand() {
        super("exit", CommandType.SPECIAL);
    }

    @Override
    public String execute() {
        throw new ExitException();
    }
}
