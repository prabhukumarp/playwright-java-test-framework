package com.prabhu.myapp.listeners;

import com.google.inject.Module;
import com.prabhu.myapp.di.FrameworkModule;
import org.testng.IModuleFactory;
import org.testng.ITestContext;
import org.testng.annotations.Guice;

public class GuiceInjectorListener implements IModuleFactory {

    @Override
    public Module createModule(ITestContext context, Class<?> testClass) {
        return new FrameworkModule();
    }

    /*@Override
    public Module createModule(Class<?> testClass) {
        return new FrameworkModule();
    }*/
}
