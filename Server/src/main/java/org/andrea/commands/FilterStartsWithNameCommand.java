package org.andrea.commands;



import org.andrea.collection.WorkerManager;
import org.andrea.data.Worker;
import org.andrea.exceptions.MissedCommandArgumentException;

import java.util.List;

public class FilterStartsWithNameCommand extends CommandImpl {
    private final WorkerManager collectionManager;

    public FilterStartsWithNameCommand(WorkerManager cm) {
        super("filter_starts_with_name", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() {
        if (!hasStringArg()) throw new MissedCommandArgumentException();
        String start = getStringArg();
        List<Worker> list = collectionManager.filterStartsWithName(getStringArg());
        if (list.isEmpty()) return "none of elements have name which starts with " + start;
        return list.stream()
                .sorted(new Worker.SortingComparator())
                .map(Worker::toString).reduce("", (a, b) -> a + b + "\n");
    }
}
