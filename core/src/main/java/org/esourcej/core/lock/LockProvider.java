package org.esourcej.core.lock;

import org.esourcej.core.state.State;

public interface LockProvider {
    /**
     * Lock aggregate across system to avoid race condition
     * lock is not critical due to fact EventStore should provide
     * optimistic locking mechanism as well.
     *
     * We use it to prevent resource waste trying update aggregate
     * from concurrency workers too many times.
     *
     * It helps to avoid throwing VersionMismatchException randomly.
     *
     * It's ok to use NullLockProvider in a low-traffic system.
     *
     * @param aggregateName Aggregate name
     * @param aggregateId Aggregate ID
     */
    Lock lock(String aggregateName, String aggregateId);

    void unlock(Lock lock);
}
