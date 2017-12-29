package org.esourcej.core.evenstore;

import org.esourcej.core.evenstore.exception.VersionMismatchException;
import org.esourcej.core.event.Event;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

abstract public class EventStoreTest {

    class EventImpl extends Event {}

    protected EventStore eventStore;
    protected String guid = java.util.UUID.randomUUID().toString();

    @Before
    public void setUp() throws Exception {
        guid = java.util.UUID.randomUUID().toString();
    }

    @Test
    public void push() throws Exception {
        List<Event> events = Arrays.asList(new EventImpl());

        eventStore.push(
                "Account",
                guid,
                events,
                0L
        );
    }

    @Test(expected = VersionMismatchException.class)
    public void pushMismatch() throws Exception {
        List<Event> events = Arrays.asList(new EventImpl());

        eventStore.push("Account",
                guid,
                events,
                0L
        );

        eventStore.push(
                "Account",
                guid,
                events,
                0L
        );
    }

    @Test
    public void getEvents() throws Exception {
        List<Event> events = Arrays.asList(new EventImpl());

        eventStore.push("Account",
                guid,
                events,
                0L
        );

        Event[] gotEvents = eventStore.getEvents("Account", guid, 0L);

        assertEquals(1, gotEvents.length);
        assertThat(gotEvents[0], equalTo(events.get(0)));
    }


}