package org.andrea.io;


import org.andrea.exceptions.FileException;
import org.andrea.file.FileManager;

import java.util.Scanner;

/**
 * Operates input
 */
public class FileInputManager extends InputManagerImpl {
    public FileInputManager(String path) throws FileException {
        super(new Scanner(new FileManager(path).read()));
        getScanner().useDelimiter("\n");
    }
}
