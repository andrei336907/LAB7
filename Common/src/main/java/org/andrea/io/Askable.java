package org.andrea.io;


import org.andrea.exceptions.InvalidDataException;

@FunctionalInterface
/**
 *user input callback
 */
public interface Askable<T> {
    T ask() throws InvalidDataException;
}