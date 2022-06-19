package org.andrea.io;


import org.andrea.auth.User;
import org.andrea.connection.CommandMsg;
import org.andrea.data.*;
import org.andrea.exceptions.*;

import java.time.LocalDate;
import java.util.Scanner;

public interface InputManager {
    /**
     * reads name from input
     *
     * @return
     * @throws EmptyStringException
     */
    String readName() throws EmptyStringException;

    /**
     * reads fullName from input
     *
     * @return
     */
    String readFullName();

    /**
     * reads x from input
     *
     * @return
     * @throws InvalidNumberException
     */
    float readXCoord() throws InvalidNumberException;

    /**
     * reads y from input
     *
     * @return
     * @throws InvalidNumberException`
     */
    Long readYCoord() throws InvalidNumberException;

    /**
     * reads coordinates from input
     *
     * @return
     * @throws InvalidNumberException
     */
    Coordinates readCoords() throws InvalidNumberException;

    /**
     * reads followers from input
     *
     * @return
     * @throws InvalidNumberException
     */
    long readFollowers() throws InvalidNumberException;

    /**
     * reads endDate from input
     *
     * @return
     * @throws InvalidDateFormatException
     */
    LocalDate readEndDate() throws InvalidDateFormatException;

    /**
     * reads position from input
     *
     * @return
     * @throws InvalidEnumException
     */
    Genre readGenre() throws InvalidEnumException;

    /**
     * reads status from input
     *
     * @return
     * @throws InvalidEnumException
     */
    Status readStatus() throws InvalidEnumException;

    /**
     * reads organizationType from input
     *
     * @return
     * @throws InvalidEnumException
     */
    AlbumType readAlbumType() throws InvalidEnumException;

    /**
     * reads organization from input
     *
     * @return
     * @throws InvalidDataException
     */
    BestAlbum readAlbum() throws InvalidDataException;

    /**
     * reads MusicBand from input
     *
     * @return
     * @throws InvalidDataException
     */
    MusicBand readBand() throws InvalidDataException;

    /**
     * reads command-argument pair from input
     *
     * @return
     */
    CommandMsg readCommand();

    /**
     * gets input scanner
     *
     * @return
     */

    boolean hasNextLine();


    String readPassword() throws InvalidDataException;

    String readLogin() throws InvalidDataException;

    User readUser() throws InvalidDataException;

    Scanner getScanner();
}
