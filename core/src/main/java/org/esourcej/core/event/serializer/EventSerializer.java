package org.esourcej.core.event.serializer;

import org.esourcej.core.event.Event;

public interface EventSerializer {
    String serialize(Event event);
    Event deserialize(String serializedEvent);
}
