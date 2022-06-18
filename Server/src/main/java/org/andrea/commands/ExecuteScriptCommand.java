package org.andrea.commands;


import org.andrea.exceptions.FileException;
import org.andrea.exceptions.MissedCommandArgumentException;
import org.andrea.exceptions.RecursiveScriptExecuteException;

public class ExecuteScriptCommand extends CommandImpl {
    ServerCommandManager commandManager;

    public ExecuteScriptCommand(ServerCommandManager cm) {
        super("execute_script", CommandType.SPECIAL);
        commandManager = cm;
    }

    @Override
    public String execute() throws FileException {
        if (!hasStringArg()) throw new MissedCommandArgumentException();
        if (commandManager.getStack().contains(getStringArg())) throw new RecursiveScriptExecuteException();
        commandManager.getStack().add(getStringArg());
        ServerCommandManager process = new ServerCommandManager(commandManager.getServer());
        process.fileMode(getStringArg());

        commandManager.getStack().pop();
        return "script successfully executed";
    }
}
