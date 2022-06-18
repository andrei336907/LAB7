package org.andrea.commands;

import org.andrea.client.Client;
import org.andrea.connection.AnswerMsg;
import org.andrea.connection.Request;
import org.andrea.connection.Response;
import org.andrea.exceptions.ConnectionException;
import org.andrea.exceptions.ConnectionTimeoutException;
import org.andrea.exceptions.InvalidDataException;

import static org.andrea.io.OutputManager.print;


/**
 * command manager for client
 */
public class ClientCommandManager extends CommandManager {
    private final Client client;

    public ClientCommandManager(Client c) {
        client = c;
        addCommand(new ExecuteScriptCommand(this));
        addCommand(new ExitCommand());
        addCommand(new HelpCommand());
    }

    public Client getClient() {
        return client;
    }

    @Override

    public AnswerMsg runCommand(Request msg) {
        AnswerMsg res = new AnswerMsg();
        if (hasCommand(msg)) {
            res = (AnswerMsg) super.runCommand(msg);
            if (res.getStatus() == Response.Status.EXIT) {
                res.info("shutting down...");
            }
        } else {
            try {
                if (client.getUser() != null && msg.getUser() == null) msg.setUser(client.getUser());
                client.send(msg);
                res = (AnswerMsg) client.receive();
                if (res.getStatus() == Response.Status.AUTH_SUCCESS) {
                    client.setUser(msg.getUser());
                }
            } catch (ConnectionTimeoutException e) {
                res.info("no attempts left, shutting down").setStatus(Response.Status.EXIT);
            } catch (InvalidDataException | ConnectionException e) {
                res.error(e.getMessage());
            }
        }
        print(res);
        return res;
    }
}
