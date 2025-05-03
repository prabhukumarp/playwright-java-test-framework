package com.prabhu.myapp.config.utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import com.prabhu.myapp.config.models.ApplicationConfig;
import com.prabhu.myapp.config.models.BrowserConfig;
import com.prabhu.myapp.config.models.FrameworkConfig;
import com.prabhu.myapp.enums.SupportedBrowser;
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
        SupportedBrowser browserType = SupportedBrowser.fromString(browserConfig.getName());

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(browserConfig.isHeadless())
                .setTimeout(browserConfig.getTimeout() * 1000); // convert to ms

        try {
            long startTime = System.currentTimeMillis();

            Browser browser;

            switch (browserType) {
                case CHROME:
                case CHROMIUM:
                    browser = playwright.chromium().launch(options);
                    break;
                case MSEDGE:
                    browser = playwright.chromium().launch(
                            options.setChannel("msedge"));
                    break;
                case FIREFOX:
                    browser = playwright.firefox().launch(options);
                    break;
                case WEBKIT:
                    browser = playwright.webkit().launch(options);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + browserType);
            }

            long duration = System.currentTimeMillis() - startTime;
            logger.info("✅ Browser '{}' launched in {} ms", browserType, duration);
            return browser;

        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "❌ Failed to launch browser: " + browserType, e);
            return null;
        }
    }
}
