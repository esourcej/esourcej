package org.esourcej.core.state.provider;

import org.esourcej.core.event.handler.exception.EventHandleRuntimeException;
import org.esourcej.core.state.State;

public interface StateProvider {
    /**
     * @deprecated use get() instead
     *
     * @see StateProvider::get()
     */
    State provide(String aggregateName, String aggregateId) throws EventHandleRuntimeException;

    /**
     * @param aggregateName Name of aggregate
     * @param aggregateId Aggregate id
     *
     * @return Built or fetched state
     */
    State get(String aggregateName, String aggregateId) throws EventHandleRuntimeException;
}
