package org.esourcej.core.command.handler.annotated;

import org.esourcej.core.command.handler.CommandHandler;
import org.esourcej.core.command.handler.CommandHandlerProvider;
import org.esourcej.core.utils.reflection.ReflectionAnnotatedMethodsFinder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public class AnnotatedCommandHandlerProvider implements CommandHandlerProvider {

    private ReflectionAnnotatedMethodsFinder finder;

    public AnnotatedCommandHandlerProvider(String _package) {
        init(_package, org.esourcej.core.annotation.CommandHandler.class);
    }

    public AnnotatedCommandHandlerProvider(String _package, Class<? extends Annotation> annotationClass) {
        init(_package, annotationClass);
    }

    private void init(String _package, Class<? extends Annotation> annotationClass) {
        this.finder = new ReflectionAnnotatedMethodsFinder(_package, annotationClass);
    }

    @Override
    public CommandHandler provide(Object payload) {
        List<Method> methods = finder.findMethods(payload.getClass());

        if (methods.size() > 0) {
            return new AnnotatedCommandHandler(methods.get(0));
        }

        return null;

    }
}
