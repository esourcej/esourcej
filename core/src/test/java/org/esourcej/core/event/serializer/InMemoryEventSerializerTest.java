package org.esourcej.core.event.serializer;

import org.esourcej.core.event.Event;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class InMemoryEventSerializerTest {
    EventSerializer serializer;

    @Before
    public void setUp() throws Exception {
        serializer = new InMemoryEventSerializer();
    }

    @Test
    public void serializeAndDeserialize() throws Exception {
        Event event = new Event(){};

        byte[] serializedEvent = serializer.serialize(event);

        Event deserializedEvent = serializer.deserialize(serializedEvent);

        assertThat(deserializedEvent, equalTo(event));
    }
}