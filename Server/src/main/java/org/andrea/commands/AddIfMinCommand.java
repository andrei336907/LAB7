package org.andrea.commands;


import org.andrea.collection.MusicBandManager;

public class AddIfMinCommand extends CommandImpl {
    private final MusicBandManager collectionManager;

    public AddIfMinCommand(MusicBandManager cm) {
        super("add_if_min", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() {
        collectionManager.addIfMin(getBandArg());
        return ("Added element: " + getBandArg().toString());
    }

}
