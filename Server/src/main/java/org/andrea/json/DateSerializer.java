package org.andrea.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Date;

import static org.andrea.utils.DateConverter.dateToString;


/**
 * type adapter for json serialization
 */
public class DateSerializer implements JsonSerializer<Date> {
    @Override
    public JsonElement serialize(Date Date, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(dateToString(Date));
    }
}