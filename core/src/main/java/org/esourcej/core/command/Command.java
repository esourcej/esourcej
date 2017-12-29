package org.esourcej.core.command;

public interface Command {
    /**
     * @return Command itself
     */
    Object payload();

    /**
     * @return Aggregate name due to current aggregation name strategy
     * @deprecated Generate aggregate name basing on class name by default; Create AggregateNameStrategy
     */
    String aggregateName();

    /**
     * @return Aggregate ID
     */
    String aggregateId();

    /**
     * @return Client identifier - used to securely feedback the client
     */
    String clientId();

    /**
     * @return Cause event ID - used to track Saga chain
     */
    String causeEventId();
}
