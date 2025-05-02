package com.prabhu.myapp.runner;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.prabhu.myapp.config.models.EnvironmentConfig;
import com.prabhu.myapp.config.models.FrameworkConfig;
import com.prabhu.myapp.config.utils.ConfigLoader;
import com.prabhu.myapp.di.FrameworkModule;

public class ConfigTestRunner {

    @Inject
    private FrameworkConfig frameworkConfig;

    @Inject
    private EnvironmentConfig environmentConfig;

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new FrameworkModule());
        ConfigTestRunner runner = injector.getInstance(ConfigTestRunner.class);
        runner.printConfigDetails();
    }

    private void printConfigDetails() {
        String environment = frameworkConfig.getApplicationConfig().getEnvironment();
        System.out.println("✅ Active Environment: " + environment);
        System.out.println("Base URL: " + environmentConfig.getApplicationConfig().getBaseUrl());
        System.out.println("Username: " + environmentConfig.getAppCredentials().getUsername());
        System.out.println("Password: " + environmentConfig.getAppCredentials().getPassword());
        System.out.println("Timeout: " + environmentConfig.getApplicationConfig().getTimeout());
        System.out.println("Browser: " + environmentConfig.getApplicationConfig().getBrowserConfig().getName());
        System.out.println("Browser Timeout: " + environmentConfig.getApplicationConfig().getBrowserConfig().getTimeout());
        System.out.println("Retry Count: " + environmentConfig.getApplicationConfig().getRetryCount());
        System.out.println("Report Path: " + environmentConfig.getReports().getReportPath());
        System.out.println("Notification Email: " + environmentConfig.getNotifications().getEmail());
        System.out.println("Viewport Width: " + environmentConfig.getPlaywright().getViewport().getWidth());

        // Validation
        if (environmentConfig.getApplicationConfig().getBaseUrl() == null) {
            System.err.println("❌ Base URL is not loaded correctly!");
        } else {
            System.out.println("✅ Config loaded successfully.");
        }
    }
}
