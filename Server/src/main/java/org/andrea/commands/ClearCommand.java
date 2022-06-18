package org.andrea.commands;


import org.andrea.auth.User;
import org.andrea.collection.WorkerManager;
import org.andrea.database.WorkerDatabaseManager;
import org.andrea.exceptions.EmptyCollectionException;
import org.andrea.exceptions.InvalidDataException;

public class ClearCommand extends CommandImpl {
    private final WorkerDatabaseManager collectionManager;

    public ClearCommand(WorkerManager cm) {
        super("clear", CommandType.NORMAL);
        collectionManager = (WorkerDatabaseManager) cm;
    }

    @Override
    public String execute() throws InvalidDataException {
        if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
        User user = getArgument().getUser();
        collectionManager.clear(user);
        return "collection cleared";
    }

}
