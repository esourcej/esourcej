package org.esourcej.core.event.handler.reflection;

import org.esourcej.core.event.handler.EventHandler;
import org.esourcej.core.event.handler.provider.EventHandlerProvider;
import org.esourcej.core.utils.reflection.ReflectionAnnotatedMethodsFinder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class ReflectionEventHandlerProvider implements EventHandlerProvider{

    private final ReflectionAnnotatedMethodsFinder reflectionAnnotatedMethodsFinder;

    public ReflectionEventHandlerProvider(String _package, Class<? extends Annotation> aClass) {
        reflectionAnnotatedMethodsFinder = new ReflectionAnnotatedMethodsFinder(_package, aClass);
    }

    @Override
    public EventHandler getEventHandler(Object event) {

        Method method = reflectionAnnotatedMethodsFinder.findMethods(event.getClass()).get(0);

        return new ReflectionEventHandler(method);
    }
}
