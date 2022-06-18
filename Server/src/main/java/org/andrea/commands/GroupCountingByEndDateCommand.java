package org.andrea.commands;



import org.andrea.collection.WorkerManager;

import java.time.LocalDate;
import java.util.Map;

import static org.andrea.utils.DateConverter.dateToString;


public class GroupCountingByEndDateCommand extends CommandImpl {
    private final WorkerManager collectionManager;

    public GroupCountingByEndDateCommand(WorkerManager cm) {
        super("group_counting_by_end_date", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() {
        Map<LocalDate, Integer> map = collectionManager.groupByEndDate();
        if (map.isEmpty()) return "none of the elements have endDate field";

        String res = "";
        for (Map.Entry<LocalDate, Integer> pair : map.entrySet()) {
            LocalDate endDate = pair.getKey();
            int quantity = map.get(endDate);
            res += dateToString(endDate) + " : " + quantity + "\n";
        }

        return res;
    }
}
