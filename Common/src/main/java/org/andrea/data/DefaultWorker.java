package org.andrea.data;

import java.time.LocalDate;
import java.util.Date;

public class DefaultWorker extends Worker {
    public DefaultWorker(String name, Coordinates coordinates, Long salary, LocalDate endDate, Genre genre, Status status, Organization organization) {
        super(name, coordinates, salary, endDate, genre, status, organization);
        setCreationDate(new Date());
        setUserLogin("");
    }
}
