package org.andrea.commands;


import org.andrea.collection.WorkerManager;

public class AddIfMaxCommand extends CommandImpl {
    private final WorkerManager collectionManager;

    public AddIfMaxCommand(WorkerManager cm) {
        super("add_if_max", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() {
        collectionManager.addIfMax(getWorkerArg());
        return ("Added element: " + getWorkerArg().toString());
    }
}
