package org.esourcej.core.event;

import com.fasterxml.jackson.annotation.JsonProperty;

abstract public class Event {

    protected Object payload;
    protected String id;

    @JsonProperty("payload")
    public Object payload() {
        return payload;
    }

    @JsonProperty("id")
    public String id() {
        return id;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}
