package org.esourcej.core.event.serializer;

import org.esourcej.core.event.Event;

public interface EventPayloadSerializer {
    <P> byte[] serialize(P payload);
    <P> P deserialize(byte[] serializedEvent, Class<P> klass);
}
