package org.andrea.commands;


import org.andrea.connection.AnswerMsg;
import org.andrea.connection.Request;
import org.andrea.connection.Response;
import org.andrea.data.MusicBand;
import org.andrea.exceptions.*;

/**
 * basic command implementation
 */
public abstract class CommandImpl implements Command {
    private final CommandType type;
    private final String name;
    private Request arg;

    public CommandImpl(String n, CommandType t) {
        name = n;
        type = t;
    }

    public CommandType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    /**
     * custom execute command
     *
     * @return String
     * @throws InvalidDataException
     * @throws CommandException
     * @throws FileException
     * @throws ConnectionException
     */
    public String execute() throws InvalidDataException, CommandException, FileException, ConnectionException, CollectionException {
        return "";
    }

    /**
     * wraps execute into response
     *
     * @return response
     */
    @Override
    public Response run() throws InvalidDataException, CommandException, FileException, ConnectionException, CollectionException {
        AnswerMsg res = new AnswerMsg();
        res.info(execute());
        return res;
    }

    public Request getArgument() {
        return arg;
    }

    public void setArgument(Request req) {
        arg = req;
    }

    public boolean hasStringArg() {
        return arg != null && arg.getStringArg() != null && !arg.getStringArg().equals("");
    }

    public boolean hasBandArg() {
        return arg != null && arg.getBand() != null;
    }

    public boolean hasUserArg() {
        return arg != null && arg.getUser() != null && arg.getUser().getLogin() != null;
    }

    public String getStringArg() {
        return getArgument().getStringArg();
    }

    public MusicBand getBandArg() {
        return getArgument().getBand();
    }
}
