package org.esourcej.core.event.handler;

import org.esourcej.core.event.handler.exception.EventHandleRuntimeException;

public interface EventHandler {
    /**
     * Returns new state
     *
     * @param event Domain event
     * @param state Aggregate stete
     * @return new state
     */
    Object handle(Object event, Object state) throws EventHandleRuntimeException;

    Object handle(Object event) throws EventHandleRuntimeException;
}
