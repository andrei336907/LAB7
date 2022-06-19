package org.andrea.json;

import com.google.gson.*;
import org.andrea.data.MusicBand;
import org.andrea.log.Log;


import java.lang.reflect.Type;
import java.util.Deque;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;


/**
 * type adapter for json deserialization
 */
public class CollectionDeserializer implements JsonDeserializer<Deque<MusicBand>> {
    private final Set<Integer> uniqueIds;

    /**
     * constructor
     *
     * @param uniqueIds set of ids. useful for generating new id
     */
    public CollectionDeserializer(Set<Integer> uniqueIds) {
        this.uniqueIds = uniqueIds;
    }

    @Override
    public Deque<MusicBand> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        Deque<MusicBand> collection = new ConcurrentLinkedDeque<>();
        JsonArray workers = json.getAsJsonArray();
        int damagedElements = 0;
        for (JsonElement jsonWorker : workers) {
            MusicBand musicBand = null;
            try {
                if (jsonWorker.getAsJsonObject().entrySet().isEmpty()) {
                    Log.logger.error("empty musicBand found");
                    throw new JsonParseException("empty musicBand");
                }
                if (!jsonWorker.getAsJsonObject().has("id")) {
                    Log.logger.error("found musicBand without id");
                    throw new JsonParseException("no id");
                }
                musicBand = context.deserialize(jsonWorker, MusicBand.class);

                Integer id = musicBand.getId();

                if (uniqueIds.contains(id)) {
                    Log.logger.error("database already contains musicBand with id #" + id);
                    throw new JsonParseException("id isn't unique");
                }
                if (!musicBand.validate()) {
                    Log.logger.error("musicBand #" + id + " doesnt match specific conditions");
                    throw new JsonParseException("invalid musicBand data");
                }
                uniqueIds.add(id);
                collection.add(musicBand);
            } catch (JsonParseException e) {
                damagedElements += 1;
            }
        }
        if (collection.size() == 0) {
            if (damagedElements == 0) Log.logger.error("database is empty");
            else Log.logger.error("all elements in database are damaged");
            throw new JsonParseException("no data");
        }
        if (damagedElements != 0)
            Log.logger.error(damagedElements + " elements in database are damaged");
        return collection;
    }
}
