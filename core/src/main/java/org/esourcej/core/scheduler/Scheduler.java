package org.esourcej.core.scheduler;

public interface Scheduler {
    void schedule(Object command, String aggregateId, int delay);
}
