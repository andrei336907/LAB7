package org.andrea.commands;


import org.andrea.collection.MusicBandManager;
import org.andrea.exceptions.CommandException;
import org.andrea.exceptions.InvalidDataException;

public class AddCommand extends CommandImpl {
    private final MusicBandManager collectionManager;

    public AddCommand(MusicBandManager cm) {
        super("add", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() throws InvalidDataException, CommandException {
        collectionManager.add(getBandArg());
        return "Added element: " + getBandArg().toString();
    }
}
