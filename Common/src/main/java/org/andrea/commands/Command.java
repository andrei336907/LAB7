package org.andrea.commands;


import org.andrea.connection.Request;
import org.andrea.connection.Response;
import org.andrea.exceptions.*;

/**
 * Command callback interface
 */

public interface Command {
    Response run() throws InvalidDataException, CommandException, FileException, ConnectionException, CollectionException;

    String getName();

    CommandType getType();

    void setArgument(Request a);
}