package org.andrea.commands;


import org.andrea.collection.MusicBandManager;
import org.andrea.exceptions.InvalidDataException;


public class InfoCommand extends CommandImpl {
    private final MusicBandManager collectionManager;

    public InfoCommand(MusicBandManager cm) {
        super("info", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() throws InvalidDataException {
        return collectionManager.getInfo();
    }

}
