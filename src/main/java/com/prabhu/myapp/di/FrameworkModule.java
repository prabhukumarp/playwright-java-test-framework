package com.prabhu.myapp.di;

import com.google.inject.AbstractModule;
import com.prabhu.myapp.config.models.EnvironmentConfig;
import com.prabhu.myapp.config.utils.EnvironmentConfigLoader;
import com.prabhu.myapp.config.utils.YamlConfigLoader;

public class FrameworkModule extends AbstractModule {

    @Override
    protected void configure() {
        // Bind YamlConfigLoader as a singleton
        bind(YamlConfigLoader.class).in(jakarta.inject.Singleton.class);

        // Bind EnvironmentConfigLoader as provider for EnvironmentConfig
        bind(EnvironmentConfigLoader.class).in(jakarta.inject.Singleton.class);
        bind(EnvironmentConfig.class).toProvider(EnvironmentConfigLoader.class).in(jakarta.inject.Singleton.class);
    }
}
