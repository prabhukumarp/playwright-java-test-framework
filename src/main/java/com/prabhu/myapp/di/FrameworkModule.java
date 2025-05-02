package com.prabhu.myapp.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.prabhu.myapp.config.models.FrameworkConfig;
import com.prabhu.myapp.config.models.EnvironmentConfig;
import com.prabhu.myapp.config.utils.ConfigLoader;
import com.prabhu.myapp.config.utils.DriverFactory;
import com.prabhu.myapp.config.utils.PlaywrightManager;

public class FrameworkModule extends AbstractModule {
    @Provides
    @Singleton
    public ConfigLoader provideConfigLoader() {
        return new ConfigLoader();
    }

    @Provides
    @Singleton
    public FrameworkConfig provideFrameworkConfig(ConfigLoader loader) {
        return loader.getFrameworkConfig();
    }

    @Provides
    @Singleton
    public EnvironmentConfig provideEnvironmentConfig(ConfigLoader loader) {
        return loader.getEnvironmentConfig();
    }

}
