package org.andrea.exceptions;

public class CannotAddException extends CollectionException {
    public CannotAddException() {
        super("unable to add");
    }
}
