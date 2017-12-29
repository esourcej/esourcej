package org.esourcej.core.command;

public class DTOCommand implements Command {

    private Object payload;
    private String aggregateId;

    public DTOCommand(Object payload, String aggregateId) {
        this.payload = payload;
        this.aggregateId = aggregateId;
    }

    @Override
    public Object payload() {
        return payload;
    }

    @Override
    public String aggregateName() {
        return null;
    }

    @Override
    public String aggregateId() {
        return aggregateId;
    }

    @Override
    public String clientId() {
        return null;
    }

    @Override
    public String causeEventId() {
        return null;
    }
}
