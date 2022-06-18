package org.andrea.commands;


import org.andrea.collection.WorkerManager;
import org.andrea.exceptions.InvalidDataException;


public class InfoCommand extends CommandImpl {
    private final WorkerManager collectionManager;

    public InfoCommand(WorkerManager cm) {
        super("info", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() throws InvalidDataException {
        return collectionManager.getInfo();
    }

}
