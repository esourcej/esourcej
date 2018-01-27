package org.esourcej.core.event.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.esourcej.core.event.Event;
import jdk.nashorn.internal.parser.JSONParser;

import java.io.IOException;

public class JacksonEventSerializer implements EventSerializer {
    ObjectMapper mapper;

    private static final byte[] EMPTY_DATA = {};

    private Class eventClass;

    public JacksonEventSerializer(ObjectMapper mapper, Class eventClass) {
        this.mapper = mapper;
        this.eventClass = eventClass;
    }

    @Override
    public byte[] serialize(Event event) {
        try {
            return mapper.writeValueAsBytes(event);
        } catch (JsonProcessingException e) {
            e.printStackTrace();

            return EMPTY_DATA;
        }
    }

    @Override
    public Event deserialize(byte[] serializedEvent) {
        try {
            return (Event) mapper.readValue(serializedEvent, eventClass);
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }
}
