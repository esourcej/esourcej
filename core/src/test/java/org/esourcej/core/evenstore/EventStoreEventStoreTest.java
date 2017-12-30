package org.esourcej.core.evenstore;

import com.github.msemys.esjc.*;
import com.github.msemys.esjc.EventStore;
import org.esourcej.core.evenstore.exception.VersionMismatchException;
import org.esourcej.core.event.Event;
import org.esourcej.core.event.serializer.InMemoryEventSerializer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EventStoreEventStoreTest extends EventStoreTest {
    @Before
    public void setUpEventStore() throws Exception {
        EventStore esjcEventStore = EventStoreBuilder.newBuilder()
                .singleNodeAddress("eventstore", 1113)
                .userCredentials("admin", "changeit")
                .build();

        eventStore = new EventStoreEventStore(esjcEventStore, new InMemoryEventSerializer());
    }

}