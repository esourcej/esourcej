package org.esourcej.core.command.handler;

import java.util.List;

public interface CommandHandler {
    /**
     * @todo Throw something domain-specific
     *
     * @param payload Domain plain command
     * @param state State of aggregate
     * @return Unknown type domain events
     */
    List<Object> handle(Object payload, Object state) throws Throwable;

    /**
     * @return Aggregate identifier
     */
    String aggregateName();
}
