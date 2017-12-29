package org.esourcej.core.event.handler.provider;

import org.esourcej.core.event.handler.EventHandler;

public interface EventHandlerProvider {

    /**
     * Get event handler for given domain object
     * @param event domain object
     * @return Event handler for given object
     *
     * @todo USe Predicate here
     */
    EventHandler getEventHandler(Object event);
}
