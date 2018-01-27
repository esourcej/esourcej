package org.esourcej.event.serializer.protobuf;

import com.google.protobuf.ByteString;
import org.esourcej.core.event.Event;
import org.esourcej.core.event.serializer.EventPayloadSerializer;
import org.esourcej.core.event.serializer.EventSerializer;
import org.esourcej.event.serializer.protobuf.fixture.Protocool;

import java.util.logging.Logger;

import static org.junit.Assert.*;

public class PorotobufEventSerializerTest {
    @org.junit.Test
    public void serialize() throws Exception {
        Protocool.Product product = Protocool.Product.newBuilder()
                .setPrice(1000)
                .setName("iPhone X")
                .build();

        EventPayloadSerializer serializer = new PorotobufEventSerializer();

        byte[] serializedProduct = serializer.serialize(product);

        Protocool.Product deserializedProduct = serializer.deserialize(serializedProduct, Protocool.Product.class);

        assertEquals(deserializedProduct, product);
    }
}