package org.esourcej.core.command.handler.annotated;

import org.esourcej.core.command.handler.CommandHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AnnotatedCommandHandler implements CommandHandler {
    private Method method;

    public AnnotatedCommandHandler(Method method) {
        this.method = method;
    }

    @Override
    public List<Object> handle(Object payload, Object state) throws Throwable {
        List<Object> returnedEventsList = new ArrayList<>();

        try {
            if (method.getParameterCount() < 2) {

                @SuppressWarnings("unchecked")
                List<Object> _list = (List<Object>) method.invoke(null, payload);

                returnedEventsList = _list;
            } else {
                Class<?> aggregateClass = (Class<?>) method.getDeclaringClass();

                @SuppressWarnings("unchecked")
                List<Object> _list = (List<Object>) method.invoke(null, payload, state);

                returnedEventsList = _list;
            }

        } catch(InvocationTargetException e) {
            throw e.getCause();
        } catch (IllegalAccessException ignored) {}

        return returnedEventsList;
    }

    @Override
    public String aggregateName() {
        // @todo extract aggregate name from class or something
        return "default_aggreagate";
    }
}
