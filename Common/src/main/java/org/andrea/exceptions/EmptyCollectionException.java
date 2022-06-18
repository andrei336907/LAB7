package org.andrea.exceptions;

/**
 * thrown when collection is empty
 */
public class EmptyCollectionException extends CollectionException {
    public EmptyCollectionException() {
        super("collection is empty");
    }
}
