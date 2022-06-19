package org.andrea.commands;


import org.andrea.collection.MusicBandManager;
import org.andrea.exceptions.EmptyCollectionException;

public class ShowCommand extends CommandImpl {
    private final MusicBandManager collectionManager;

    public ShowCommand(MusicBandManager cm) {
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
