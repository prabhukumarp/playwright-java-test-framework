package com.prabhu.myapp.config.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.prabhu.myapp.config.models.EnvironmentConfig;
import com.prabhu.myapp.config.models.FrameworkConfig;
import com.prabhu.myapp.config.utils.YamlConfigLoader;

public class ConfigModule extends AbstractModule {

    @Override
    protected void configure() {
        // Nothing to bind manually
    }

    @Provides
    @Singleton
    public YamlConfigLoader provideConfigLoader() {
        return new YamlConfigLoader();
    }

    @Provides
    @Singleton
    public FrameworkConfig provideFrameworkConfig(YamlConfigLoader loader) {
        return loader.loadFrameworkConfig();
    }

    @Provides
    @Singleton
    public EnvironmentConfig provideEnvironmentConfig(YamlConfigLoader loader) {
        return loader.loadEnvironmentConfig();
    }
}
