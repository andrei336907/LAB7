package org.andrea.exceptions;

public class ProhibitedPathException extends FileException {
    public ProhibitedPathException() {
        super("file path is prohibited");
    }
}
