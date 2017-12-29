package org.esourcej.core.event.serializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.esourcej.core.command.handler.annotated.fixture.Account;
import org.esourcej.core.event.Event;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class JacksonEventSerializerTest {
    static class DomainTypeAwareEvent extends Event {
        Account.Event payload;

        public Account.Event payload() {
            return payload;
        }

        public void setPayload(Account.Event payload) {
            this.payload = payload;
        }
    }

    EventSerializer serializer;

    ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        serializer = new JacksonEventSerializer(objectMapper, DomainTypeAwareEvent.class);
    }

    @Test
    public void serializeAndDeserialize() throws Exception {
        DomainTypeAwareEvent event = new DomainTypeAwareEvent();

        Account.MoneyWithdrawn moneyWithdrawn = new Account.MoneyWithdrawn();

        moneyWithdrawn.amount = 1000L;

        event.setPayload(moneyWithdrawn);

        String serializedEvent = serializer.serialize(event);

        Event deserializedEvent = serializer.deserialize(serializedEvent);

        assertThat(deserializedEvent.payload(), is(instanceOf(Account.MoneyWithdrawn.class)));
    }
}