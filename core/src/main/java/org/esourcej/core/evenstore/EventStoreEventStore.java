package org.esourcej.core.evenstore;

import com.github.msemys.esjc.EventData;
import com.github.msemys.esjc.ExpectedVersion;
import com.github.msemys.esjc.Position;
import com.github.msemys.esjc.operation.WrongExpectedVersionException;
import org.esourcej.core.evenstore.exception.VersionMismatchException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.esourcej.core.evenstore.exception.ExecutionException;
import org.esourcej.core.event.Event;
import org.esourcej.core.event.serializer.EventSerializer;

import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class EventStoreEventStore implements EventStore {
    private com.github.msemys.esjc.EventStore eventStore;
    private EventSerializer serializer;

    public EventStoreEventStore(
            com.github.msemys.esjc.EventStore eventStore,
            EventSerializer serializer
    ) {
        this.eventStore = eventStore;
        this.serializer = serializer;
    }

    // @todo transactional
    @Override
    public void push(String aggregateName, String aggregateId, List<Event> events, Long expectedVersion) throws VersionMismatchException, ExecutionException {
        Function<Event, EventData> mapper = e -> EventData
                .newBuilder()
                .type("DomainEvent")
                .jsonData(serializer.serialize(e))
                .build();

        List<EventData> data = events.stream().map(mapper).collect(Collectors.toList());

        try {
            String stream = buildStreamName(aggregateName, aggregateId);

            eventStore.
                    appendToStream(stream, expectedVersion - 1, data)
                    .thenAccept(r -> System.out.println(r.logPosition)).get();
        } catch (InterruptedException e) {
            throw new ExecutionException(e);
        } catch (java.util.concurrent.ExecutionException e) {

            if (e.getCause() instanceof WrongExpectedVersionException) {
                WrongExpectedVersionException wrongVersionException = (WrongExpectedVersionException) e.getCause();

                throw new VersionMismatchException(wrongVersionException.expectedVersion, wrongVersionException.currentVersion);
            }
            throw new ExecutionException(e);
        }
    }

    @Override
    public Event[] getEvents(String aggregateName, String aggregateId, Long fromVersion) {
        String streamName = buildStreamName(aggregateName, aggregateId);

        List<Event> events = eventStore.streamEventsForward(streamName, fromVersion, 5, false)
                .map(resolvedEvent -> resolvedEvent.originalEvent().data)
                .map(eventData -> serializer.deserialize(eventData))
                .collect(Collectors.toList());

        return events.toArray(new Event[0]);
    }

    private String buildStreamName(String aggregateName, String aggregateId) {
        return aggregateName + "_" + aggregateId;
    }
}
