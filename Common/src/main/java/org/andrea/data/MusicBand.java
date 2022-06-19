package org.andrea.data;


import org.andrea.auth.User;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

/**
 * MusicBand class
 */
public class MusicBand implements Collectionable, Serializable {
    private final Coordinates coordinates; //Поле не может быть null
    private final Genre genre; //Поле может быть null
    private final BestAlbum bestAlbum; //Поле может быть null
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long followers; //Значение поля должно быть больше 0
    private LocalDate BirthBandDate; //Поле может быть null
    private Status status; //Поле не может быть null
    private String userLogin;

    /**
     * constructor, just set fields
     *
     * @param name
     * @param coordinates
     * @param followers
     * @param BirthBandDate
     * @param genre
     * @param status
     * @param bestAlbum
     */
    public MusicBand(String name, Coordinates coordinates, Long followers, LocalDate BirthBandDate, Genre genre, Status status
            , BestAlbum bestAlbum) {


        this.name = name;
        this.coordinates = coordinates;
        this.followers = followers;
        this.BirthBandDate = BirthBandDate;
        this.genre = genre;
        this.status = status;
        this.bestAlbum = bestAlbum;
    }

    /**
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * sets ID, usefull for replacing elements in collection
     *
     * @param ID
     */
    public void setId(int ID) {
        id = ID;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date date) {
        creationDate = date;
    }

    /**
     * @return String
     */
    public String getName() {
        return name;
    }

    public void setName(String s) {
        name = s;
    }

    /**
     * @return long
     */
    public long getFollowers() {
        return followers;
    }

    public void setFollowers(long s) {
        followers = s;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public BestAlbum getBestAlbum() {
        return bestAlbum;
    }

    public Genre getGenre() {
        return genre;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String login) {
        userLogin = login;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status s) {
        status = s;
    }

    /**
     * @return LocalDate
     */
    public LocalDate getBirthBandDate() {
        return BirthBandDate;
    }

    public void setBirthBandDate(LocalDate date) {
        BirthBandDate = date;
    }


    /**
     * @return String
     */
    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strCreationDate = dateFormat.format(creationDate);
        String strEndDate = "";
        if (BirthBandDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            strEndDate = BirthBandDate.format(formatter);
        }
        String s = "";
        s += "{\n";
        s += "  \"id\" : " + id + ",\n";
        s += "  \"name\" : " + "\"" + name + "\"" + ",\n";
        s += "  \"coordinates\" : " + coordinates.toString() + ",\n";
        s += "  \"creationDate\" : " + "\"" + strCreationDate + "\"" + ",\n";
        s += "  \"followers\" : " + followers + ",\n";
        if (BirthBandDate != null) s += "  \"BirthBandDate\" : " + "\"" + strEndDate + "\"" + ",\n";
        if (genre != null) s += "  \"genre\" : " + "\"" + genre + "\"" + ",\n";
        s += "  \"status\" : " + "\"" + status.toString() + "\"" + ",\n";
        s += "  \"bestAlbum\" : " + bestAlbum.toString() + "\n";
        if (userLogin != null) s += "  \"userLogin\" : " + userLogin + "\n";
        s += "}";
        return s;
    }

    /**
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) return false;
        MusicBand another = (MusicBand) obj;
        return this.getId() == another.getId();
    }


    /**
     * @param worker
     * @return int
     */
    public int compareTo(Collectionable worker) {
        return Long.compare(this.followers, worker.getFollowers());
    }

    public void setUser(User usr) {
        userLogin = usr.getLogin();
    }

    /**
     * @return boolean
     */
    public boolean validate() {
        return (
                coordinates != null && coordinates.validate() &&
                        (bestAlbum == null || bestAlbum.validate()) &&
                        (followers > 0) && (id > 0) &&
                        name != null && !name.equals("") &&
                        status != null &&
                        creationDate != null
        );

    }

    /**
     * comparator for sorting
     */
    public static class SortingComparator implements Comparator<MusicBand> {
        public int compare(MusicBand first, MusicBand second) {
            int result = Double.compare(first.getCoordinates().getX(), second.getCoordinates().getX());
            if (result == 0) {
                // both X are equal -> compare Y too
                result = Double.compare(first.getCoordinates().getY(), second.getCoordinates().getY());
            }
            return result;
        }
    }


}
