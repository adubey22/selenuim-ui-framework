package org.myProject.listeners;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RetryListener implements IAnnotationTransformer {
    @Override
    public void transform(final ITestAnnotation annotation, final Class testClass, final Constructor testConstructor,
                          final Method testMethod) {
        annotation.setRetryAnalyzer(Retry.class);
    }
}

