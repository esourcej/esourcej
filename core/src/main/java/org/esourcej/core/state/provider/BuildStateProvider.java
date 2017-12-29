package org.esourcej.core.state.provider;

import org.esourcej.core.evenstore.EventStore;
import org.esourcej.core.event.Event;
import org.esourcej.core.event.handler.exception.EventHandleRuntimeException;
import org.esourcej.core.event.handler.provider.EventHandlerProvider;
import org.esourcej.core.state.SimpleState;
import org.esourcej.core.state.State;

public class BuildStateProvider implements StateProvider {
    private EventHandlerProvider eventHandlerProvider;
    private EventStore eventStore;

    public BuildStateProvider(EventHandlerProvider eventHandlerProvider, EventStore eventStore) {
        this.eventHandlerProvider = eventHandlerProvider;
        this.eventStore = eventStore;

    }

    @Override
    public State provide(String aggregateName, String aggregateId) throws EventHandleRuntimeException {
        return get(aggregateName, aggregateId);
    }

    @Override
    public State get(String aggregateName, String aggregateId) throws EventHandleRuntimeException {
        SimpleState state = new SimpleState();
        Event[] events = eventStore.getEvents(aggregateName, aggregateId, 0L);

        Object domainState = null;

        for (Event event : events) {
            Object payload = event.payload();

            domainState = eventHandlerProvider.getEventHandler(payload).handle(payload, domainState);

            state.setVersion(state.version() + 1);
        }

        state.setPayload(domainState);

        return state;
    }
}
