package org.esourcej.core.event.factory;

import org.esourcej.core.event.Event;

public interface EventFactory {
    public Event create(Object payload, String id);
}
