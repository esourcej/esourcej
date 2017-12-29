package org.esourcej.core.state;

public class SimpleState implements State {
    private Object payload = null;
    private Long version = 0L;

    @Override
    public Object payload() {
        return payload;
    }

    @Override
    public Long version() {
        return version;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
