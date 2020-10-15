package ir.haji.nutro.dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Saeed on 6/9/2020.
 */
@JsonDeserialize(using = DataTypeObjectDeserializer.class)
public class DataTypeObject extends JsonSerializable.Base {
    HashMap<String, Object> objects = new HashMap<>();

    public <T> T get(String field) {
        Object val = objects.get(field);
        return (T) val;
    }

    public DataTypeObject set(Object[] objects, String... names) {
        if (objects.length != names.length)
            return this;
        int i = 0;
        for (Object object : objects) {
            set(names[i], object);
            i++;
        }
        return this;
    }

    public DataTypeObject set(String name, Object object) {
        objects.put(name, object);
        return this;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        for (String s : objects.keySet()) {
            gen.writeFieldName(s);
            gen.writeObject(objects.get(s));
        }
        gen.writeEndObject();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        throw new UnsupportedOperationException("Not supported.");
    }
}
