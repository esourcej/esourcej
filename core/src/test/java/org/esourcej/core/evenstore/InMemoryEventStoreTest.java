package org.esourcej.core.evenstore;

import com.github.msemys.esjc.EventStore;
import com.github.msemys.esjc.EventStoreBuilder;
import org.esourcej.core.event.serializer.InMemoryEventSerializer;
import org.junit.Before;

public class InMemoryEventStoreTest extends EventStoreTest {
    @Before
    public void setUpEventStore() throws Exception {
        eventStore = new InMemoryEventStore(new InMemoryEventSerializer());
    }

}