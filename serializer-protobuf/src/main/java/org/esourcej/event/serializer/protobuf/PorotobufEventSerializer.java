package org.esourcej.event.serializer.protobuf;

import com.google.protobuf.GeneratedMessageV3;
import org.esourcej.core.event.Event;
import org.esourcej.core.event.serializer.EventPayloadSerializer;
import org.esourcej.core.event.serializer.EventSerializer;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

public class PorotobufEventSerializer implements EventPayloadSerializer {
    @Override
    public <P> byte[] serialize(P payload) {

        if (payload instanceof GeneratedMessageV3) {
            return ((GeneratedMessageV3) payload).toByteArray();
        }

        return new byte[0];
    }

    @Override
    public <P> P deserialize(byte[] serializedEvent, Class<P> klass) {

        boolean assignableFrom = GeneratedMessageV3.class.isAssignableFrom(klass);

        if (assignableFrom) {
            try {
                return (P) klass.getMethod("parseFrom", byte[].class).invoke(null, serializedEvent);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            }
        }

        return null;
    }
}
