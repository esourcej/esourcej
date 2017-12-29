package org.esourcej.core.state;

public interface State {
    /**
     * @return Plain state
     */
    Object payload();

    /**
     * @return Last applied event number
     */
    Long version();
}
