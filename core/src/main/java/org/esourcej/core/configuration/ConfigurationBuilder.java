package org.esourcej.core.configuration;

import org.esourcej.core.evenstore.EventStore;

public class

ConfigurationBuilder {
    private EventStore eventStore;

    ConfigurationBuilder useEventStore(EventStore eventStore) {
        this.eventStore = eventStore;

        return this;
    }

//    ConfigurationBuilder minimal() {
//        return this;
//    }

    Configuration build() {
        return null;
    }
}
