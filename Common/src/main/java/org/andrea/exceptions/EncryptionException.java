package org.andrea.exceptions;

public class EncryptionException extends Exception {
    public EncryptionException() {
        super("cannot encrypt");
    }
}
