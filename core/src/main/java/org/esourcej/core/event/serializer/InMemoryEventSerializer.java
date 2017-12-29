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
    public String serialize(Event event) {
        events.add(event);

        String serializedEvent = String.valueOf(events.size() - 1);

        return serializedEvent;
    }

    @Override
    public Event deserialize(String serializedEvent) {
        return events.get(Integer.parseInt(serializedEvent));
    }
}
