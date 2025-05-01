package com.prabhu.myapp.config.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.prabhu.myapp.config.models.EnvironmentConfig;
import com.prabhu.myapp.config.models.FrameworkConfig;
import com.prabhu.myapp.exceptions.ConfigLoadException;
import com.prabhu.myapp.helpers.ExceptionHelper;
import com.prabhu.myapp.helpers.LoggerHelper;
import jakarta.inject.Singleton;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;

@Singleton
public class YamlConfigLoader {

    private static final Logger logger = LoggerHelper.getLogger(YamlConfigLoader.class);
    private static final String APP_CONFIG_FILE = "application.yml";
    private static final String ENV_FOLDER = "environment/";

    private final ObjectMapper mapper;

    private FrameworkConfig frameworkConfig;
    private EnvironmentConfig environmentConfig;

    public YamlConfigLoader() {
        this.mapper = new ObjectMapper(new YAMLFactory());
    }

    public FrameworkConfig loadFrameworkConfig() {
        if (frameworkConfig != null) return frameworkConfig;

        logger.info("Loading base framework config from '{}'", APP_CONFIG_FILE);

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(APP_CONFIG_FILE)) {
            ExceptionHelper.throwIfNull(inputStream, APP_CONFIG_FILE + " not found in classpath", logger);

            frameworkConfig = mapper.readValue(inputStream, FrameworkConfig.class);

            ExceptionHelper.throwIf(frameworkConfig.getApplicationConfig() == null,
                    "Missing 'applicationConfig' section in " + APP_CONFIG_FILE, logger);

            logger.info("Loaded framework config. Environment = '{}'",
                    frameworkConfig.getApplicationConfig().getEnvironment());

            return frameworkConfig;
        } catch (IOException e) {
            ExceptionHelper.logAndThrow(logger, "Failed to load " + APP_CONFIG_FILE, new ConfigLoadException("YAML parsing failed", e));
            return null; // unreachable, but required for compilation
        }
    }

    public EnvironmentConfig loadEnvironmentConfig() {
        if (environmentConfig != null) return environmentConfig;

        FrameworkConfig baseConfig = loadFrameworkConfig();
        String env = baseConfig.getApplicationConfig().getEnvironment();
        String envFile = ENV_FOLDER + env + ".yml";

        logger.info("Loading environment config from '{}'", envFile);

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(envFile)) {
            ExceptionHelper.throwIfNull(inputStream, envFile + " not found in classpath", logger);

            environmentConfig = mapper.readValue(inputStream, EnvironmentConfig.class);

            logger.info("Successfully loaded environment config: '{}'", envFile);
            return environmentConfig;
        } catch (IOException e) {
            ExceptionHelper.logAndThrow(logger, "Failed to load " + envFile, new ConfigLoadException("Environment config parsing failed", e));
            return null; // unreachable, but required for compilation
        }
    }
}
