package com.prabhu.myapp.config.utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import com.prabhu.myapp.config.models.ApplicationConfig;
import com.prabhu.myapp.config.models.EnvironmentConfig;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.apache.logging.log4j.Logger;
import com.prabhu.myapp.helpers.LoggerHelper;

@Singleton
public class DriverFactory {

    private static final Logger logger = LoggerHelper.getLogger(DriverFactory.class);

    private EnvironmentConfig environmentConfig;
    private ApplicationConfig applicationConfig;

    @Inject
    public DriverFactory(EnvironmentConfig environmentConfig) {
        this.environmentConfig = environmentConfig;
    }

    public void loadEnvironmentConfig(EnvironmentConfig config) {
        // This method could load the configuration dynamically based on the environment
        logger.info("Loaded environment configuration: " + config);
    }

    public Browser createBrowser(Playwright playwright, String browserType) {
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(applicationConfig.isBrowserHeadless());

        switch (browserType.toLowerCase()) {
            case "chromium":
                return playwright.chromium().launch(options);
            case "firefox":
                return playwright.firefox().launch(options);
            case "webkit":
                return playwright.webkit().launch(options);
            default:
                throw new IllegalArgumentException("Unsupported browser type: " + browserType);
        }
    }
}
