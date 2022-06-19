package org.andrea.data;

import java.time.LocalDate;
import java.util.Date;

public class DefaultMusicBand extends MusicBand {
    public DefaultMusicBand(String name, Coordinates coordinates, Long followers, LocalDate endDate, Genre genre, Status status, BestAlbum bestAlbum) {
        super(name, coordinates, followers, endDate, genre, status, bestAlbum);
        setCreationDate(new Date());
        setUserLogin("");
    }
}
