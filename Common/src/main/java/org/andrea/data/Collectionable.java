package org.andrea.data;

/**
 * interface for storable object
 */
public interface Collectionable extends Comparable<Collectionable>, Validatable {

    int getId();

    /**
     * sets id, useful for replacing object in collection
     *
     * @param ID
     */
    void setId(int ID);

    long getFollowers();

    String getName();

    /**
     * compairs two objects
     */
    int compareTo(Collectionable worker);

    boolean validate();
}
