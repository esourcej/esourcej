package org.esourcej.core.lock;

public class NullLockProvider implements LockProvider {
    @Override
    public Lock lock(String aggregateName, String aggregateId) {
        return null;
    }

    @Override
    public void unlock(Lock lock) { }
}
