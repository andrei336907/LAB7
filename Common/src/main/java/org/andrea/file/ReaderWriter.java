package org.andrea.file;


import org.andrea.exceptions.FileException;

public interface ReaderWriter {
    /**
     * sets path to file
     *
     * @param pth
     */
    void setPath(String pth);

    /**
     * reads data
     *
     * @return
     */
    String read() throws FileException;

    /**
     * saves data
     *
     * @param data
     */
    void write(String data) throws FileException;
}
