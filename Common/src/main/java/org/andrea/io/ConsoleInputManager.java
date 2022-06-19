package org.andrea.io;


import org.andrea.auth.User;
import org.andrea.data.*;

import java.time.LocalDate;
import java.util.Scanner;

import static org.andrea.io.OutputManager.print;


public class ConsoleInputManager extends InputManagerImpl {

    public ConsoleInputManager() {
        super(new Scanner(System.in));
        getScanner().useDelimiter("\n");
    }

    @Override
    public String readName() {
        return new Question<String>("enter name:", super::readName).getAnswer();
    }

    @Override
    public String readFullName() {
        return new Question<String>("enter name:", super::readFullName).getAnswer();
    }

    @Override
    public float readXCoord() {
        return new Question<Float>("enter x:", super::readXCoord).getAnswer();
    }

    @Override
    public Long readYCoord() {
        return new Question<Long>("enter y:", super::readYCoord).getAnswer();
    }

    @Override
    public Coordinates readCoords() {
        print("enter coordinates");
        float x = readXCoord();
        Long y = readYCoord();
        Coordinates coord = new Coordinates(x, y);
        return coord;
    }

    @Override
    public long readFollowers() {
        return new Question<Long>("enter followers:", super::readFollowers).getAnswer();
    }

    @Override
    public LocalDate readEndDate() {
        return new Question<LocalDate>("enter birth band Date:", super::readEndDate).getAnswer();
    }

    @Override
    public Genre readGenre() {
        return new Question<Genre>("enter genre(RAP, POP, JAZZ, ROCK):", super::readGenre).getAnswer();
    }

    @Override
    public Status readStatus() {
        return new Question<Status>("enter status(RECOMMENDED, REGULAR):", super::readStatus).getAnswer();
    }

    @Override
    public AlbumType readAlbumType() {
        return new Question<AlbumType>("enter Album Type(SOLO, COLLABORATION):", super::readAlbumType).getAnswer();
    }

    @Override
    public BestAlbum readAlbum() {
        print("enter Best Album");
        String fullName = readFullName();
        AlbumType orgType = readAlbumType();
        return new BestAlbum(fullName, orgType);
    }

    @Override
    public String readLogin() {
        return new Question<String>("enter login:", super::readLogin).getAnswer();
    }

    @Override
    public String readPassword() {
        return new Question<String>("enter password:", super::readPassword).getAnswer();
    }

    @Override
    public User readUser() {
        String login = readLogin();
        String password = readPassword();
        return new User(login, password);
    }

}
