package org.andrea.commands;


import org.andrea.collection.WorkerManager;
import org.andrea.exceptions.CommandException;
import org.andrea.exceptions.InvalidDataException;

public class AddCommand extends CommandImpl {
    private final WorkerManager collectionManager;

    public AddCommand(WorkerManager cm) {
        super("add", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() throws InvalidDataException, CommandException {
        collectionManager.add(getWorkerArg());
        return "Added element: " + getWorkerArg().toString();
    }
}
