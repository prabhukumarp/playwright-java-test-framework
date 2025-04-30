package com.prabhu.myapp.config.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.prabhu.myapp.config.models.EnvironmentConfig;
import com.prabhu.myapp.config.models.FrameworkConfig;
import com.prabhu.myapp.helpers.ExceptionHelper;
import com.prabhu.myapp.helpers.LoggerHelper;
import com.prabhu.myapp.exceptions.ConfigLoadException;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

@Singleton
public class YamlConfigLoader {

    private static final Logger logger = LoggerHelper.getLogger(YamlConfigLoader.class);
    private static final String APP_CONFIG_FILE = "application.yml";
    private static final String ENV_FOLDER = "environment/";

    private final ObjectMapper mapper;

    @Inject
    public YamlConfigLoader() {
        this.mapper = new ObjectMapper(new YAMLFactory());
    }

    public FrameworkConfig loadFrameworkConfig() {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(APP_CONFIG_FILE)) {
            ExceptionHelper.throwIfNull(inputStream, "application.yml not found in classpath", logger);
            return mapper.readValue(inputStream, FrameworkConfig.class);
        } catch (IOException e) {
            ExceptionHelper.logAndThrow(logger, "Failed to load application.yml", e);
            throw new ConfigLoadException("application.yml could not be loaded", e);
        }
    }

    public EnvironmentConfig loadEnvironmentConfig() {
        FrameworkConfig frameworkConfig = loadFrameworkConfig();
        String env = frameworkConfig.getApp().getEnvironment();
        String envFile = ENV_FOLDER + env + ".yml";

        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(envFile)) {
            ExceptionHelper.throwIfNull(inputStream, envFile + " not found in classpath", logger);
            return mapper.readValue(inputStream, EnvironmentConfig.class);
        } catch (IOException e) {
            ExceptionHelper.logAndThrow(logger, "Failed to load " + envFile, e);
            return null;
        }
    }
}
