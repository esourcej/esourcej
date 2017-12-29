package org.esourcej.core.state.provider;

import org.esourcej.core.command.handler.annotated.fixture.Account;
import org.esourcej.core.evenstore.EventStore;
import org.esourcej.core.evenstore.InMemoryEventStore;
import org.esourcej.core.event.Event;
import org.esourcej.core.event.handler.provider.EventHandlerProvider;
import org.esourcej.core.event.handler.reflection.ReflectionEventHandlerProvider;
import org.esourcej.core.event.serializer.InMemoryEventSerializer;
import org.esourcej.core.state.State;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

//@Category(IntegrationTest.class)
public class BuildStateProviderTest {
    EventStore store;

    EventHandlerProvider handlerProvider = new ReflectionEventHandlerProvider(
            "org.esourcej.core",
            org.esourcej.core.annotation.EventHandler.class
    );

    BuildStateProvider buildStateProvider;

    @Before
    public void setUp() throws Exception {
        store = new InMemoryEventStore(new InMemoryEventSerializer());
        buildStateProvider = new BuildStateProvider(handlerProvider, store);
    }

    @Test
    public void buildState() throws Exception {

        Event event1 = new Event(){};
        Account.Created payload1 = new Account.Created();
        event1.setPayload(payload1);

        payload1.ownerName = "Andre Woods";
        payload1.balance = 1000L;

        event1.setPayload(payload1);

        Event event2 = new Event(){};
        Account.MoneyWithdrawn payload2 = new Account.MoneyWithdrawn();
        event2.setPayload(payload2);

        payload2.amount = 300L;

        Event event3 = new Event(){};
        Account.MoneyWithdrawn payload3 = new Account.MoneyWithdrawn();
        event3.setPayload(payload3);

        payload3.amount = 250L;

        store.push("Account", "uuid-123", Arrays.asList(event1, event2, event3), 0L);

        State state = buildStateProvider.provide("Account", "uuid-123");

        assertThat(state.payload(), instanceOf(Account.State.class));
        assertThat(state.version(), equalTo(3L));

        Account.State accState = (Account.State) state.payload();

        assertEquals(accState.ownerName, "Andre Woods");
        assertEquals((long) accState.balance, 450);
    }
}