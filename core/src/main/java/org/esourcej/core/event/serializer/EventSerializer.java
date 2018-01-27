package org.esourcej.core.event.serializer;

import org.esourcej.core.event.Event;

public interface EventSerializer {
    byte[] serialize(Event event);
    Event deserialize(byte[] serializedEvent);
}
