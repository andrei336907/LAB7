package org.andrea.commands;


import org.andrea.exceptions.CommandException;
import org.andrea.exceptions.FileException;
import org.andrea.exceptions.MissedCommandArgumentException;
import org.andrea.exceptions.RecursiveScriptExecuteException;

public class ExecuteScriptCommand extends CommandImpl {
    ClientCommandManager commandManager;

    public ExecuteScriptCommand(ClientCommandManager cm) {
        super("execute_script", CommandType.NORMAL);
        commandManager = cm;
    }

    @Override
    public String execute() {
        if (!hasStringArg()) throw new MissedCommandArgumentException();
        if (commandManager.getStack().contains(getStringArg())) throw new RecursiveScriptExecuteException();
        commandManager.getStack().add(getStringArg());
        ClientCommandManager process = new ClientCommandManager(commandManager.getClient());
        try {
            process.fileMode(getStringArg());
        } catch (FileException e) {
            throw new CommandException("cannot find script file");
        }
        commandManager.getStack().pop();
        return "script successfully executed";
    }
}

