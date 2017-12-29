package org.esourcej.core.event.handler.reflection;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.esourcej.core.command.handler.annotated.fixture.Account;
import org.esourcej.core.event.handler.EventHandler;
import org.esourcej.core.event.handler.provider.EventHandlerProvider;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.Method;
import java.util.Set;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class ReflectionEventHandlerProviderTest {
    EventHandlerProvider eventHandlerProvider = new ReflectionEventHandlerProvider(
            "org.esourcej.core",
            org.esourcej.core.annotation.EventHandler.class
    );

    @Test
    @JsonProperty
    public void getEventHandler() throws Exception {
        Account.Created event = new Account.Created();

        event.balance = 245L;
        event.ownerName = "Eddie Barrett";

        EventHandler eventHandler = eventHandlerProvider.getEventHandler(event);
        Object state = eventHandler.handle(event, null);

        assertThat(state, instanceOf(Account.State.class));

        Account.State accountState = (Account.State) state;

        assertEquals(accountState.balance, event.balance);

    }
}