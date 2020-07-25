package ir.haji.nutro.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by jalil on 7/19/2020.
 */
public class DataTypeObjectDeserializer extends JsonDeserializer<DataTypeObject> {

    @Override
    public DataTypeObject deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        DataTypeObject dto = new DataTypeObject();

        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);
        Iterator<String> iterator = node.fieldNames();
        while (iterator.hasNext()) {
            String fieldName = iterator.next();
            JsonNode value = node.get(fieldName);
            Object valueObj = null;
            if (value.isNumber()) {
                if (value.isDouble())
                    valueObj = value.doubleValue();
                else if (value.isLong())
                    valueObj = value.longValue();
                else
                    valueObj = (long) value.intValue();
            } else if (value.isBoolean()) {
                valueObj = value.booleanValue();
            } else {
                valueObj = value.asText();
            }
            dto.set(fieldName, valueObj);
        }
        return dto;
    }

}