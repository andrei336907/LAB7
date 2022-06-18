package org.andrea.commands;


import org.andrea.collection.WorkerManager;

public class AddIfMinCommand extends CommandImpl {
    private final WorkerManager collectionManager;

    public AddIfMinCommand(WorkerManager cm) {
        super("add_if_min", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() {
        collectionManager.addIfMin(getWorkerArg());
        return ("Added element: " + getWorkerArg().toString());
    }

}
