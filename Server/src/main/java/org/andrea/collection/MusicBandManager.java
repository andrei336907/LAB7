package org.andrea.collection;



import org.andrea.data.MusicBand;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * interface for storing elements
 *
 */
public interface MusicBandManager {

    /**
     * sorts collection
     */
    void sort();

    Collection<MusicBand> getCollection();

    /**
     * adds new element
     *
     * @param element
     */
    void add(MusicBand element);

    /**
     * get information about collection
     *
     * @return info
     */
    String getInfo();

    /**
     * checks if collection contains element with particular id
     *
     * @param ID
     * @return flag
     */
    boolean checkID(Integer ID);

    /**
     * removes element by id
     *
     * @param id
     */
    void removeByID(Integer id);

    /**
     * updates element by id
     *
     * @param id
     * @param newElement
     */
    void updateByID(Integer id, MusicBand newElement);


    void clear();

    public MusicBand getByID(Integer id);

    void removeFirst();

    /**
     * adds element if it is greater than max
     *
     * @param element
     */
    void addIfMax(MusicBand element);

    /**
     * adds element if it is smaller than min
     *
     * @param element
     */
    void addIfMin(MusicBand element);

    /**
     * print all elements which name starts with substring
     *
     * @param start
     */
    List<MusicBand> filterStartsWithName(String start);

    /**
     * @return map of dates with quantity
     */
    Map<LocalDate, Integer> groupByEndDate();

    /**
     * print all unique values of followers field
     */
    List<Long> getUniqueFollowers();

    /**
     * convert collection to json
     *
     * @param data
     */
    void deserializeCollection(String data);

    /**
     * parse collection from json
     *
     * @return serialized collection
     */
    String serializeCollection();

}
