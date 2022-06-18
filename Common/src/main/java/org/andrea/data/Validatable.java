package org.andrea.data;

public interface Validatable {
    /**
     * validates all fields after json deserialization
     *
     * @return
     */
    boolean validate();
}
