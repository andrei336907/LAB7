package org.andrea.commands;




import org.andrea.collection.MusicBandManager;
import org.andrea.exceptions.EmptyCollectionException;

import java.util.List;

public class PrintUniqueFollowersCommand extends CommandImpl {
    private final MusicBandManager collectionManager;

    public PrintUniqueFollowersCommand(MusicBandManager cm) {
        super("print_unique_followers", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() {
        if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
        List<Long> list = collectionManager.getUniqueFollowers();
        return list.stream().map(n -> Long.toString(n)).reduce("", (a, b) -> a + b + "\n");
    }
}

