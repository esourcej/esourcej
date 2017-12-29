package org.esourcej.core.command.handler;

public interface CommandHandlerProvider {
    /**
     * @param payload Domain command
     * @return Command handler
     */
    CommandHandler provide(Object payload);
}
