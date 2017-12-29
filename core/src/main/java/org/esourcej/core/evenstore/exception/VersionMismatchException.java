package org.esourcej.core.evenstore.exception;

public class VersionMismatchException extends Exception {
    private Long expectedVersion;
    private Long currentVersion;

    public VersionMismatchException(Long expectedVersion, Long currentVersion)
    {
        super(String.format("Version mismatch. Expected %d but it is %d", expectedVersion, currentVersion));

        this.expectedVersion = expectedVersion;
        this.currentVersion = currentVersion;
    }

    public Long getExpectedVersion() {
        return expectedVersion;
    }

    public Long getActualVersion() {
        return currentVersion;
    }
}
