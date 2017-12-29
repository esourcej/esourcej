package org.esourcej.core.command.handler;

import org.esourcej.core.command.Command;
import org.esourcej.core.evenstore.EventStore;
import org.esourcej.core.evenstore.exception.VersionMismatchException;
import org.esourcej.core.event.Event;
import org.esourcej.core.event.factory.EventFactory;
import org.esourcej.core.event.serializer.EventSerializer;
import org.esourcej.core.lock.LockProvider;
import org.esourcej.core.state.State;
import org.esourcej.core.state.provider.StateProvider;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @todo move it to parent package (its, gatway)
 */
public class CommandGateway {
    private CommandHandlerProvider provider;
    private EventStore store;
    private EventFactory eventFactory;
    private StateProvider stateProvider;

    public CommandGateway(
            CommandHandlerProvider provider,
            EventStore store,
            EventFactory eventFactory,
            StateProvider stateProvider
    ) {
        this.provider = provider;
        this.store = store;
        this.eventFactory = eventFactory;
        this.stateProvider = stateProvider;
    }

    public void handle(Command command) throws Throwable {
        Object domainCommand = command.payload();

        State state = stateProvider.provide("root", command.aggregateId());

        Object payload = state == null ? null : state.payload();

        List<Object> domainEvents = provider.provide(domainCommand).handle(domainCommand, payload);

        List<Event> events = domainEvents.stream()
                .map(object -> eventFactory.create(object, command.aggregateId()))
                .collect(Collectors.toList());

        long expectedVersion = state != null ? state.version() : 0;

        store.push("root", command.aggregateId(), events, expectedVersion);
    }
}
