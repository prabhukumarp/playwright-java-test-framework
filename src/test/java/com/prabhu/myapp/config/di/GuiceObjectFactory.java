package com.prabhu.myapp.config.di;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.prabhu.myapp.di.FrameworkModule;
import org.testng.ITestObjectFactory;

import java.lang.reflect.Constructor;

public class GuiceObjectFactory implements ITestObjectFactory {
    private final Injector injector = Guice.createInjector(new FrameworkModule());

    @Override
    public Object newInstance(Constructor constructor, Object... params) {
        Class<?> testClass = constructor.getDeclaringClass();
        return injector.getInstance(testClass);
    }
}
