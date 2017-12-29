package org.esourcej.core.event.handler.reflection;

import org.esourcej.core.event.handler.EventHandler;
import org.esourcej.core.event.handler.exception.EventHandleRuntimeException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionEventHandler implements EventHandler {
    private Method method;

    public ReflectionEventHandler(Method method) {
        this.method = method;
    }

    @Override
    public Object handle(Object event, Object state) throws EventHandleRuntimeException {
        try {
            if (method.getParameterCount() == 1) {
                return  method.invoke(null, event);
            } else {
                return  method.invoke(null, event, state);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new EventHandleRuntimeException(e);
        }
    }

    @Override
    public Object handle(Object event) throws EventHandleRuntimeException {
        return handle(event, null);
    }
}
