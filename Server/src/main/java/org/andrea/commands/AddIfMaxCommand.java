package org.andrea.commands;


import org.andrea.collection.MusicBandManager;

public class AddIfMaxCommand extends CommandImpl {
    private final MusicBandManager collectionManager;

    public AddIfMaxCommand(MusicBandManager cm) {
        super("add_if_max", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() {
        collectionManager.addIfMax(getBandArg());
        return ("Added element: " + getBandArg().toString());
    }
}
