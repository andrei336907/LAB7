package org.andrea.connection;

import java.io.Closeable;

/**
 * interface for sending and receiving
 */
public interface SenderReceiver extends Closeable {
    int BUFFER_SIZE = 4096;
}
