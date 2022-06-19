package org.andrea.connection;


import org.andrea.auth.User;
import org.andrea.data.MusicBand;

import java.io.Serializable;

public interface Request extends Serializable {
    String getStringArg();

    MusicBand getBand();

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
