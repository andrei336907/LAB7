package org.andrea.commands;


import org.andrea.exceptions.ExitException;

public class ExitCommand extends CommandImpl {
    public ExitCommand() {
        super("exit", CommandType.NORMAL);
    }

    @Override
    public String execute() {
        throw new ExitException();
    }
}
