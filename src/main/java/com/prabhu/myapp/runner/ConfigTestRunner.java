package com.prabhu.myapp.runner;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.prabhu.myapp.config.models.EnvironmentConfig;
import com.prabhu.myapp.config.models.FrameworkConfig;
import com.prabhu.myapp.config.utils.YamlConfigLoader;
import com.prabhu.myapp.di.FrameworkModule;
import com.prabhu.myapp.helpers.LoggerHelper;
import org.slf4j.Logger;

public class ConfigTestRunner {

    private static final Logger logger = LoggerHelper.getLogger(ConfigTestRunner.class);

    public static void main(String[] args) {
        logger.info("Starting ConfigTestRunner...");

        // Step 1: Create Guice Injector with FrameworkModule
        Injector injector = Guice.createInjector(new FrameworkModule());

        // Step 2: Load Framework Config
        FrameworkConfig frameworkConfig = injector.getInstance(FrameworkConfig.class);
        logger.info("App Name         : {}", frameworkConfig.getApplicationConfig().getName());
        logger.info("Base URL         : {}", frameworkConfig.getApplicationConfig().getBaseUrl());
        logger.info("Browser          : {}", frameworkConfig.getApplicationConfig().getBrowserConfig().getName());
        logger.info("Report Path      : {}", frameworkConfig.getReports().getReportPath());

        // Step 3: Load Environment Config
        EnvironmentConfig envConfig = injector.getInstance(EnvironmentConfig.class);
        logger.info("Environment URL  : {}", envConfig.getApplicationConfig().getBaseUrl());
        logger.info("API URL          : {}", envConfig.getApiConfig().getApiUrl());
        logger.info("DB Host          : {}", envConfig.getDatabase().getUrl());
        logger.info("Username         : {}", envConfig.getAppCredentials().getUsername());
        logger.info("Viewport Width   : {}", envConfig.getPlaywright().getViewport().getWidth());

        logger.info("✅ Configuration loading test completed successfully.");
    }
}
