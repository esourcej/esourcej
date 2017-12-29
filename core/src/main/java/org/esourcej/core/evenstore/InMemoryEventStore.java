package org.esourcej.core.evenstore;

import org.esourcej.core.evenstore.exception.ExecutionException;
import org.esourcej.core.evenstore.exception.VersionMismatchException;
import org.esourcej.core.event.Event;
import org.esourcej.core.event.serializer.EventSerializer;

import java.util.*;

import static java.util.Arrays.asList;

public class InMemoryEventStore implements EventStore {

    Map<String, List<Event>> store;
    private EventSerializer serializer;

    public InMemoryEventStore(EventSerializer serializer) {
        store = new HashMap<>();

        this.serializer = serializer;
    }

    @Override
    public void push(String aggregateName, String aggregateId, List<Event> events, Long expectedVersion) throws VersionMismatchException, ExecutionException {
        String streamName = buildStreamName(aggregateName, aggregateId);

        List<Event> eventStore;

        if (store.containsKey(streamName)) {
            eventStore = store.get(streamName);
        } else {
            eventStore = new ArrayList<>();
            store.put(streamName, eventStore);
        }

        int size = eventStore.size();

        if (size != expectedVersion) {
            throw new VersionMismatchException(expectedVersion, (long) size);
        }

        eventStore.addAll(events);
    }

    @Override
    public Event[] getEvents(String aggregateName, String aggregateId, Long fromVersion) {
        String streamName = buildStreamName(aggregateName, aggregateId);

        Event[] events = new Event[0];

        if (store.containsKey(streamName)) {
            return store.get(streamName).toArray(events);
        }

        return events;
    }

    public List<Event> getEvents() {
        Collection<List<Event>> collections = store.values();
        List<Event> all = new ArrayList<>();

        collections.forEach(all::addAll);

        return all;
    }


    // @todo extract to make common
    private String buildStreamName(String aggregateName, String aggregateId) {
        return aggregateName + "_" + aggregateId;
    }
}
