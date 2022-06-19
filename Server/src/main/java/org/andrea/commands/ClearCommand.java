package org.andrea.commands;


import org.andrea.auth.User;
import org.andrea.collection.MusicBandManager;
import org.andrea.database.MusicBandDatabaseManager;
import org.andrea.exceptions.EmptyCollectionException;
import org.andrea.exceptions.InvalidDataException;

public class ClearCommand extends CommandImpl {
    private final MusicBandDatabaseManager collectionManager;

    public ClearCommand(MusicBandManager cm) {
        super("clear", CommandType.NORMAL);
        collectionManager = (MusicBandDatabaseManager) cm;
    }

    @Override
    public String execute() throws InvalidDataException {
        if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
        User user = getArgument().getUser();
        collectionManager.clear(user);
        return "collection cleared";
    }

}
