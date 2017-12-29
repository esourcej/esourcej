package org.esourcej.core.evenstore;

import org.esourcej.core.evenstore.exception.ExecutionException;
import org.esourcej.core.evenstore.exception.VersionMismatchException;
import org.esourcej.core.event.Event;

import java.util.List;

public interface EventStore {
    /**
     * @param aggregateName Aggregate name
     * @param aggregateId Aggregate ID, usually a  GUID
     * @param events Events array
     * @param expectedVersion Expected version of last event posted (fencing)
     *
     * @throws VersionMismatchException In case ov version mismatch
     */
    void push(String aggregateName, String aggregateId, List<Event> events, Long expectedVersion)
            throws VersionMismatchException, ExecutionException;

    /**
     * Get events
     *
     * @param aggregateName
     * @param aggregateId
     * @param fromVersion
     * @return
     */
    Event[] getEvents(String aggregateName, String aggregateId, Long fromVersion);
}
