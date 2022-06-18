package org.andrea.main;


import org.andrea.client.Client;
import org.andrea.connection.CommandMsg;
import org.andrea.exceptions.ConnectionException;

import java.util.LinkedList;
import java.util.List;


public class TestMultiThread {
    public static void main(String[] args) throws ConnectionException {
        List<Client> clients = new LinkedList<>();

        clients.add(new Client("localhost", 5432));
        clients.add(new Client("localhost", 5432));
        clients.add(new Client("localhost", 5432));
        clients.add(new Client("localhost", 5432));
        clients.add(new Client("localhost", 5432));

        for (Client c : clients) {
            c.send(new CommandMsg("Andrew", null, null));
        }

        new Client("localhost", 4445).start();

    }
}
