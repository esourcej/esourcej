package org.esourcej.core.event.serializer;

import org.esourcej.core.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InMemoryEventSerializer implements EventSerializer {
    List<Event> events;

    public InMemoryEventSerializer() {
        this.events = new ArrayList<>();
    }

    @Override
    public byte[] serialize(Event event) {
        events.add(event);

        return String.valueOf(events.size() - 1).getBytes();
    }

    @Override
    public Event deserialize(byte[] serializedEvent) {
        return events.get(Integer.parseInt(new String(serializedEvent)));
    }
}
