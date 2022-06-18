package org.andrea.main;


import org.andrea.client.Client;
import org.andrea.exceptions.ConnectionException;
import org.andrea.exceptions.EndOfInputException;
import org.andrea.exceptions.InvalidPortException;

import java.io.PrintStream;

import static org.andrea.io.OutputManager.print;
import static org.andrea.io.OutputManager.printErr;


public class Main {
    //public static Logger logger = LogManager.getLogger("logger");
    //static final Logger logger = LogManager.getRootLogger();
    public static void main(String[] args) throws Exception {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        print("Hello. It's worker manager. Let's start.");


        String address = "localhost";
        String strPort = "5432";
        int port = 0;
        try {
            if (args.length == 2) {
                address = args[0];
                strPort = args[1];
            }
            if (args.length == 1) {
                strPort = args[0];
                print("no address passed by arguments, setting default : " + address);
            }
            if (args.length == 0) {
                print("no port and no address passed by arguments, setting default :" + address + "/" + strPort);
            }
            try {
                port = Integer.parseInt(strPort);
            } catch (NumberFormatException e) {
                throw new InvalidPortException();
            }
            Client client = new Client(address, port);
            try {
                client.start();
            } catch (EndOfInputException e) {
                printErr(e.getMessage());
            }
        } catch (ConnectionException e) {
            print(e.getMessage());
        }
    }
}
