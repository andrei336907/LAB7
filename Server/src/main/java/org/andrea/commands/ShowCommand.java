package org.andrea.commands;


import org.andrea.collection.WorkerManager;
import org.andrea.exceptions.EmptyCollectionException;

public class ShowCommand extends CommandImpl {
    private final WorkerManager collectionManager;

    public ShowCommand(WorkerManager cm) {
        super("show", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() {
        if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
        collectionManager.sort();
        return collectionManager.serializeCollection();
    }

}
