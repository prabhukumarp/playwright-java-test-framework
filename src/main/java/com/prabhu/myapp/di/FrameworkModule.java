package com.prabhu.myapp.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.prabhu.myapp.config.models.EnvironmentConfig;
import com.prabhu.myapp.config.models.FrameworkConfig;
import com.prabhu.myapp.config.utils.YamlConfigLoader;
import com.prabhu.myapp.helpers.ExceptionHelper;
import com.prabhu.myapp.helpers.LoggerHelper;
import org.apache.logging.log4j.Logger;

@Singleton
public class FrameworkModule extends AbstractModule {

    private static final Logger logger = LoggerHelper.getLogger(FrameworkModule.class);

    @Override
    protected void configure() {
        logger.info("Binding YamlConfigLoader as Singleton");
        bind(YamlConfigLoader.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    public FrameworkConfig provideFrameworkConfig(YamlConfigLoader configLoader) {
        logger.info("Providing FrameworkConfig from application.yml");
        try {
            FrameworkConfig frameworkConfig = configLoader.loadFrameworkConfig();
            ExceptionHelper.throwIfNull(frameworkConfig, "FrameworkConfig loading failed", logger);
            return frameworkConfig;
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "Exception while providing FrameworkConfig", e);
            return null; // not reached, but required for compilation
        }
    }

    @Provides
    @Singleton
    public EnvironmentConfig provideEnvironmentConfig(YamlConfigLoader configLoader) {
        logger.info("Providing EnvironmentConfig from environment-specific yml");
        try {
            EnvironmentConfig environmentConfig = configLoader.loadEnvironmentConfig();
            ExceptionHelper.throwIfNull(environmentConfig, "EnvironmentConfig loading failed", logger);
            return environmentConfig;
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "Exception while providing EnvironmentConfig", e);
            return null; // not reached, but required for compilation
        }
    }
}
