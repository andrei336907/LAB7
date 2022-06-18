package org.andrea.connection;


import org.andrea.auth.User;
import org.andrea.data.Worker;

import java.io.Serializable;

public interface Request extends Serializable {
    String getStringArg();

    Worker getWorker();

    String getCommandName();

    User getUser();

    void setUser(User usr);

    Status getStatus();

    void setStatus(Status s);

    enum Status {
        DEFAULT,
        SENT_FROM_CLIENT,
        RECEIVED_BY_SERVER
    }
}
