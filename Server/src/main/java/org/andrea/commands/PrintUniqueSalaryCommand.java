package org.andrea.commands;




import org.andrea.collection.WorkerManager;
import org.andrea.exceptions.EmptyCollectionException;

import java.util.List;

public class PrintUniqueSalaryCommand extends CommandImpl {
    private final WorkerManager collectionManager;

    public PrintUniqueSalaryCommand(WorkerManager cm) {
        super("print_unique_salary", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() {
        if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
        List<Long> list = collectionManager.getUniqueSalaries();
        return list.stream().map(n -> Long.toString(n)).reduce("", (a, b) -> a + b + "\n");
    }
}

