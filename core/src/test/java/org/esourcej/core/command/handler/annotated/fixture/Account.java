package org.esourcej.core.command.handler.annotated.fixture;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.esourcej.core.annotation.CommandHandler;
import org.esourcej.core.annotation.EventHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Account {
    @JsonTypeInfo(use=JsonTypeInfo.Id.MINIMAL_CLASS, include=JsonTypeInfo.As.PROPERTY, property="@type")
    static public class Event {}

    static public class Create {
        public String ownerName;
        public Long balance;
    }

    static public class Created extends Event {
        public String ownerName;
        public Long balance;
    }

    static public class MoneyWithdrawn extends Event {
        public Long amount;
    }

    static public class State {
        public String ownerName;
        public Long balance;
}

    @CommandHandler
    static public List<Object> handle(Create command) {

        Created created = new Created();

        created.ownerName = command.ownerName;
        created.balance = command.balance;

        return Arrays.asList(created);
    }

    @EventHandler
    static public State handle(Created event) {
        State state = new State();

        state.balance = event.balance;
        state.ownerName = event.ownerName;

        return state;
    }

    @EventHandler
    static public State handle(MoneyWithdrawn event, State state) {
        state.balance -= event.amount;

        return state;
    }
}
