package org.esourcej.core.command.handler;

import org.esourcej.core.command.DTOCommand;
import org.esourcej.core.evenstore.EventStore;
import org.esourcej.core.event.Event;
import org.esourcej.core.event.factory.EventFactory;
import org.esourcej.core.event.handler.exception.EventHandleRuntimeException;
import org.esourcej.core.state.provider.StateProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CommandGatewayTest {
    @Mock
    CommandHandlerProvider provider;

    @Mock
    CommandHandler handler;

    @Mock
    EventStore store;

    @Mock
    EventFactory eventFactory;

    @Mock
    Event event;

    @Mock
    StateProvider stateProvider;

    @InjectMocks
    CommandGateway commandGateway;

    Object domainCommand;
    Object domainEvent;

    @Before
    public void setup() throws EventHandleRuntimeException
    {
        when(stateProvider.provide(any(), any())).thenReturn(null);


        domainCommand = new Object();
        domainEvent = new Object();
    }

    @Test
    public void testStateless() throws Throwable
    {
        when(provider.provide(any())).thenReturn(handler);
        List<Object> events = Collections.singletonList(domainEvent);
        when(handler.handle(domainCommand, null)).thenReturn(events);
        when(eventFactory.create(any(), eq("1234"))).thenReturn(event);
        when(event.payload()).thenReturn(domainEvent);

        DTOCommand command = new DTOCommand(domainCommand, "1234");

        commandGateway.handle(command);

        ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);

        verify(store).push(eq("root"), eq("1234"), captor.capture(), eq(0L));

        List<Event> returnedEvents = captor.getValue();

        assertEquals(1, returnedEvents.size());
        assertEquals(domainEvent, returnedEvents.get(0).payload());
    }

    @Test
    public void statefulTest() throws Throwable
    {


    }
}