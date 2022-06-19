package org.andrea.commands;



import org.andrea.collection.MusicBandManager;
import org.andrea.data.MusicBand;
import org.andrea.exceptions.MissedCommandArgumentException;

import java.util.List;

public class FilterStartsWithNameCommand extends CommandImpl {
    private final MusicBandManager collectionManager;

    public FilterStartsWithNameCommand(MusicBandManager cm) {
        super("filter_bands_with_name", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() {
        if (!hasStringArg()) throw new MissedCommandArgumentException();
        String start = getStringArg();
        List<MusicBand> list = collectionManager.filterStartsWithName(getStringArg());
        if (list.isEmpty()) return "none of elements have name which starts with " + start;
        return list.stream()
                .sorted(new MusicBand.SortingComparator())
                .map(MusicBand::toString).reduce("", (a, b) -> a + b + "\n");
    }
}
