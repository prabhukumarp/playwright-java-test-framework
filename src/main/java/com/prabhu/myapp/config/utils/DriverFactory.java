package com.prabhu.myapp.config.utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import com.prabhu.myapp.config.models.ApplicationConfig;
import com.prabhu.myapp.config.models.BrowserConfig;
import com.prabhu.myapp.config.models.FrameworkConfig;
import com.prabhu.myapp.helpers.ExceptionHelper;
import com.prabhu.myapp.helpers.LoggerHelper;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;

@Singleton
public class DriverFactory {

    private static final Logger logger = LoggerHelper.getLogger(DriverFactory.class);

    private final BrowserConfig browserConfig;

    @Inject
    public DriverFactory(FrameworkConfig frameworkConfig) {
        ApplicationConfig applicationConfig = frameworkConfig.getApplicationConfig();
        this.browserConfig = applicationConfig.getBrowserConfig();

        ExceptionHelper.throwIfNull(applicationConfig, "ApplicationConfig is null", logger);
        ExceptionHelper.throwIfNull(browserConfig, "BrowserConfig is null", logger);

        logger.info("DriverFactory initialized with browser = '{}', headless = '{}'",
                browserConfig.getName(), browserConfig.isHeadless());
    }

    public Browser createBrowser(Playwright playwright) {
        String browserType = browserConfig.getName().toLowerCase();

        try {
            BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                    .setHeadless(browserConfig.isHeadless())
                    .setTimeout(browserConfig.getTimeout() * 1000); // Convert to ms

            logger.info("Launching browser '{}' with headless = {}", browserType, browserConfig.isHeadless());

            switch (browserType) {
                case "chromium":
                case "chrome":
                    return playwright.chromium().launch(options);
                case "firefox":
                    return playwright.firefox().launch(options);
                case "webkit":
                    return playwright.webkit().launch(options);
                default:
                    String message = "Unsupported browser type: " + browserType;
                    logger.error(message);
                    throw new IllegalArgumentException(message);
            }

        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "Failed to launch browser: " + browserType, e);
            return null;
        }
    }
}
