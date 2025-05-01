package com.prabhu.myapp.di;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class InjectorProvider {
    private static final Injector injector = Guice.createInjector(new FrameworkModule());

    public static Injector getInjector() {
        return injector;
    }
}

