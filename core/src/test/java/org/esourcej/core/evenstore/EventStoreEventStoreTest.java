package org.esourcej.core.evenstore;

import com.github.msemys.esjc.*;
import com.github.msemys.esjc.EventStore;
import org.esourcej.core.event.serializer.InMemoryEventSerializer;
import org.junit.Before;

public class EventStoreEventStoreTest extends EventStoreTest {
    private String EVENTSTORE_HOST = "ESOURCEJ_CORE_TEST_EVENTSTORE_TEST";

    @Before
    public void setUpEventStore() throws Exception {

        String host = getHost();

        EventStore esjcEventStore = EventStoreBuilder.newBuilder()
                .singleNodeAddress(host, 1113)
                .userCredentials("admin", "changeit")
                .build();

        eventStore = new EventStoreEventStore(esjcEventStore, new InMemoryEventSerializer());
    }

    private String getHost() {
        String host = System.getenv(EVENTSTORE_HOST);

        host = host == null ? "localhost" : host;

        return host;
    }

}