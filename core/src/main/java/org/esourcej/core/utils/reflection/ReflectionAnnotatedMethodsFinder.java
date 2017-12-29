package org.esourcej.core.utils.reflection;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ReflectionAnnotatedMethodsFinder implements AnnotatedMethodsFinder {
    private Set<Method> methodsCache;
    private HashMap<Class, List<Method>> handlersCache = new HashMap<>();
    private Class<? extends Annotation> annotationClass;

    private Reflections reflections;
    private String _package;

    public ReflectionAnnotatedMethodsFinder(String _package, Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;

        reflections = new Reflections(
                new ConfigurationBuilder()
                        .setUrls(ClasspathHelper.forPackage( _package ) )
                        .setScanners(new MethodAnnotationsScanner() )
                        .filterInputsBy(new FilterBuilder().includePackage( _package ))
        );
    }

    // @TODO: use Predicate here
    public List<Method> findMethods(Class<?> aClass) {

        String commandClassName = aClass.getName();

        List<Method> handlers;

        if (!handlersCache.containsKey(aClass)) {
            handlers = doFindMethods(commandClassName);

            handlersCache.put(aClass, handlers);
        } else {
            handlers = handlersCache.get(aClass);
        }

        return handlers;
    }

    private List<Method> doFindMethods(String commandClass) {
        Set<Method> methods = findMethods();

        Predicate<Method> methodMatcher = method -> {
            try {
                return Class.forName(method.getGenericParameterTypes()[0].getTypeName())
                        .isAssignableFrom(Class.forName(commandClass));
            } catch (ClassNotFoundException e) {
                return false;
            }
        };

        return methods.stream()
                .filter(methodMatcher)
                .collect(Collectors.toList());
    }

    private Set<Method> findMethods() {
        methodsCache = methodsCache == null ? doFindMethods() : methodsCache;

        return methodsCache;
    }

    private Set<Method> doFindMethods() {
        return reflections.getMethodsAnnotatedWith(annotationClass);
    }
}
