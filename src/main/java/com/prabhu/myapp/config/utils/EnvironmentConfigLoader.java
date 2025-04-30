package com.prabhu.myapp.config.utils;

import com.prabhu.myapp.config.models.EnvironmentConfig;
import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.inject.Singleton;

@Singleton
public class EnvironmentConfigLoader implements Provider<EnvironmentConfig> {

    private final YamlConfigLoader yamlConfigLoader;
    private EnvironmentConfig environmentConfig;

    @Inject
    public EnvironmentConfigLoader(YamlConfigLoader yamlConfigLoader) {
        this.yamlConfigLoader = yamlConfigLoader;
    }

    @Override
    public EnvironmentConfig get() {
        if (environmentConfig == null) {
            environmentConfig = yamlConfigLoader.loadEnvironmentConfig();
        }
        return environmentConfig;
    }
}
