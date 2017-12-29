package org.esourcej.core.command.handler.annotated;

import org.esourcej.core.command.handler.CommandHandler;
import org.esourcej.core.command.handler.annotated.fixture.Account;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AnnotatedCommandHandlerProviderTest {
    @Test
    public void provideCommandHandler() throws Throwable {
        AnnotatedCommandHandlerProvider provider = new AnnotatedCommandHandlerProvider(
            "org.esourcej.core.command.handler.annotated"
        );

        Account.Create payload = new Account.Create();

        payload.balance = 100L;
        payload.ownerName = "Lula Morales";

        List<?> handle = provider.provide(payload).handle(payload, null);

        assertEquals(handle.size(), 1);

        Account.Created event = (Account.Created) handle.get(0);

        assertEquals(event.ownerName, payload.ownerName);
        assertEquals((long) event.balance, 100L);
    }
}