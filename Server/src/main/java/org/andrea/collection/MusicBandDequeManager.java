package org.andrea.collection;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import com.google.gson.reflect.TypeToken;
import org.andrea.data.MusicBand;
import org.andrea.exceptions.CannotAddException;
import org.andrea.exceptions.CollectionException;
import org.andrea.exceptions.EmptyCollectionException;
import org.andrea.exceptions.NoSuchIdException;
import org.andrea.json.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;


/**
 * Operates collection.
 */
public class MusicBandDequeManager implements MusicBandManager {
    private Deque<MusicBand> collection;
    private final java.time.LocalDateTime initDate;
    private final Set<Integer> uniqueIds;

    /**
     * Constructor, set start values
     */
    public MusicBandDequeManager() {
        uniqueIds = new ConcurrentSkipListSet<>();
        collection = new ConcurrentLinkedDeque<>();
        initDate = java.time.LocalDateTime.now();
    }

    public int generateNextId() {
        if (collection.isEmpty())
            return 1;
        else {
            int id = collection.element().getId() + 1;
            if (uniqueIds.contains(id)) {
                while (uniqueIds.contains(id)) id += 1;
            }
            return id;
        }
    }

    public void sort() {
        collection = collection.stream().sorted(new MusicBand.SortingComparator()).collect(Collectors.toCollection(ConcurrentLinkedDeque::new));
    }

    /**
     * Return collection
     *
     * @return Collection
     */
    public Deque<MusicBand> getCollection() {
        return collection;
    }

    /**
     * Add element to collection
     *
     * @param musicBand Element of collection
     */
    public void add(MusicBand musicBand) {
        int id = generateNextId();
        uniqueIds.add(id);
        musicBand.setId(id);
        collection.add(musicBand);
    }

    public MusicBand getByID(Integer id){
        assertNotEmpty();
        Optional<MusicBand> band = collection.stream()
                .filter(w -> w.getId() == id)
                .findFirst();
        if(!band.isPresent()){
            throw new NoSuchIdException(id);
        }
        return band.get();
    }
    protected void addWithoutIdGeneration(MusicBand musicBand){
        uniqueIds.add(musicBand.getId());
        collection.add(musicBand);
    }

    protected void removeAll(Collection<Integer> ids){
        Iterator<Integer> iterator = ids.iterator();
        while (iterator.hasNext()){
            Integer id = iterator.next();
            collection.removeIf(band -> band.getId()==id);
            iterator.remove();
        }
    }

    /**
     * Get information about collection
     *
     * @return Information
     */
    public String getInfo() {
        return "Database of MusicBand, size: " + collection.size() + ", initialization date: " + initDate.toString();
    }

    /**
     * Give info about is this ID used
     *
     * @param ID ID
     * @return is it used or not
     */
    public boolean checkID(Integer ID) {
        return uniqueIds.contains(ID);
    }

    public void assertNotEmpty(){
        if(collection.isEmpty()) throw new EmptyCollectionException();
    }
    /**
     * Delete element by ID
     *
     * @param id ID
     */

    public void removeByID(Integer id) {
        assertNotEmpty();
        Optional<MusicBand> band = collection.stream()
                .filter(b -> b.getId() == id)
                .findFirst();
        if(!band.isPresent()){
            throw new NoSuchIdException(id);
        }
        collection.remove(band.get());
        uniqueIds.remove(id);
    }

    /**
     * Delete element by ID
     *
     * @param id ID
     */
    public void updateByID(Integer id, MusicBand newMusicBand) {
        assertNotEmpty();
        Optional<MusicBand> band = collection.stream()
                .filter(w -> w.getId() == id)
                .findFirst();
        if (!band.isPresent()) {
            throw new NoSuchIdException(id);
        }
        collection.remove(band.get());
        newMusicBand.setId(id);
        collection.add(newMusicBand);
    }

    /**
     * Get size of collection
     *
     * @return Size of collection
     */
    public int getSize() {
        return collection.size();
    }


    public void clear() {
        collection.clear();
        uniqueIds.clear();
    }

    public void removeFirst() {
        assertNotEmpty();
        int id = collection.getFirst().getId();
        collection.removeFirst();
        uniqueIds.remove(id);
    }

    /**
     * Add if ID of element bigger than max in collection
     *
     * @param musicBand Element
     */
    public void addIfMax(MusicBand musicBand) {
        if (collection.stream()
                .max(MusicBand::compareTo)
                .filter(w -> w.compareTo(musicBand) == 1)
                .isPresent()) {
            throw new CannotAddException();
        }
        add(musicBand);
    }

    /**
     * Add if ID of element smaller than min in collection
     *
     * @param musicBand Element
     */
    public void addIfMin(MusicBand musicBand) {
        if (collection.stream()
                .min(MusicBand::compareTo)
                .filter(w -> w.compareTo(musicBand) < 0)
                .isPresent()) {
            throw new CannotAddException();
        }
        add(musicBand);
    }

    public List<MusicBand> filterStartsWithName(String start) {
        assertNotEmpty();
        return collection.stream()
                .filter(w -> w.getName().startsWith(start.trim()))
                .collect(Collectors.toList());
    }

    public Map<LocalDate, Integer> groupByEndDate() {
        assertNotEmpty();
        HashMap<LocalDate, Integer> map = new HashMap<>();
        collection.stream()
                .filter((worker -> worker.getBirthBandDate() != null))
                .forEach((worker) -> {
                    LocalDate endDate = worker.getBirthBandDate();
                    if (map.containsKey(endDate)) {
                        Integer q = map.get(endDate);
                        map.replace(endDate, q + 1);
                    } else {
                        map.put(endDate, 1);
                    }
                });
        return map;
    }

    public List<Long> getUniqueFollowers() {
        assertNotEmpty();
        List<Long> salaries = new LinkedList<>();
        salaries = collection.stream()
                .map(MusicBand::getFollowers)
                .distinct()
                .collect(Collectors.toList());
        return salaries;
    }

    public void deserializeCollection(String json) {
        try {
            if (json == null || json.equals("")) {
                collection = new ConcurrentLinkedDeque<>();
            } else {
                Type collectionType = new TypeToken<Queue<MusicBand>>() {
                }.getType();
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                        .registerTypeAdapter(Date.class, new DateDeserializer())
                        .registerTypeAdapter(collectionType, new CollectionDeserializer(uniqueIds))
                        .create();
                collection = gson.fromJson(json.trim(), collectionType);
            }
        } catch (JsonParseException e) {
            throw new CollectionException("cannot load");
        }
    }

    public String serializeCollection() {
        if (collection == null || collection.isEmpty()) return "";
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(Date.class, new DateSerializer())
                .setPrettyPrinting().create();
        return gson.toJson(collection);
    }
}