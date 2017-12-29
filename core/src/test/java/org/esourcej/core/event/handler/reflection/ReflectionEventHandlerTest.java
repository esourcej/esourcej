package org.esourcej.core.event.handler.reflection;

import org.esourcej.core.command.handler.annotated.fixture.Account;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ReflectionEventHandlerTest {
    @Test
    public void handle() throws Exception {
        Optional<Method> method = getMethod(Account.Created.class);

        assertTrue(method.isPresent());

        ReflectionEventHandler reflectionEventHandler = new ReflectionEventHandler(method.get());

        Account.State newState = (Account.State) reflectionEventHandler.handle(getCreated(), null);

        assertEquals((long) newState.balance, 125);
    }

    @Test
    public void handleWithState() throws Exception {
        Optional<Method> method = getMethod(Account.MoneyWithdrawn.class);

        assertTrue(method.isPresent());

        ReflectionEventHandler reflectionEventHandler = new ReflectionEventHandler(method.get());

        Account.Created event = getCreated();

        Account.State state = Account.handle(event);

        Account.MoneyWithdrawn moneyWithdrawn = new Account.MoneyWithdrawn();

        moneyWithdrawn.amount = 34L;

        Account.State newState = (Account.State) reflectionEventHandler.handle(moneyWithdrawn, state);

        assertEquals((long) newState.balance, 91);

    }

    private Account.Created getCreated() {
        Account.Created event = new Account.Created();

        event.balance = 125L;
        event.ownerName = "Ina Fields";
        return event;
    }

    private Optional<Method> getMethod(Class<?> event) {
        Method[] accountMethods = Account.class.getDeclaredMethods();

        Optional<Method> method = Arrays.stream(accountMethods)
                .filter(m -> {
                    return m.getParameterTypes()[0] == event;
                })
                .findFirst();

        return method;
    }
}