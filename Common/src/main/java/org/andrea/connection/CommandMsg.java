package org.andrea.connection;


import org.andrea.auth.User;
import org.andrea.data.MusicBand;

/**
 * Message witch include command and arguments
 */
public class CommandMsg implements Request {
    private final String commandName;
    private final String commandStringArgument;
    private final MusicBand musicBand;
    private User user;
    private Status status;

    public CommandMsg(String commandNm, String commandSA, MusicBand w) {
        commandName = commandNm;
        commandStringArgument = commandSA;
        musicBand = w;
        user = null;
        status = Status.DEFAULT;
    }

    public CommandMsg(String commandNm, String commandSA, MusicBand w, User usr) {
        commandName = commandNm;
        commandStringArgument = commandSA;
        musicBand = w;
        user = usr;
        status = Status.DEFAULT;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status s) {
        status = s;
    }

    /**
     * @return Command name.
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * @return Command string argument.
     */
    public String getStringArg() {
        return commandStringArgument;
    }

    /**
     * @return Command object argument.
     */
    public MusicBand getBand() {
        return musicBand;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User usr) {
        user = usr;
    }
}