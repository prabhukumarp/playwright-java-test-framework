package com.prabhu.myapp.config.utils;

import com.microsoft.playwright.*;
import com.prabhu.myapp.config.models.FrameworkConfig;
import com.prabhu.myapp.helpers.ExceptionHelper;
import com.prabhu.myapp.helpers.LoggerHelper;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;

@Singleton
public class PlaywrightManager {

    private static final Logger logger = LoggerHelper.getLogger(PlaywrightManager.class);

    private final DriverFactory driverFactory;
    private Playwright playwright;
    private Browser browser;
    private Page page;

    @Inject
    public PlaywrightManager(DriverFactory driverFactory) {
        this.driverFactory = driverFactory;
    }

    public void init() {
        try {
            logger.info("Initializing PlaywrightManager...");
            playwright = Playwright.create();
            browser = driverFactory.createBrowser(playwright);
            page = browser.newPage();
            logger.info("Playwright, Browser and Page initialized.");
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "Failed during Playwright initialization", e);
        }
    }

    public Page getPage() {
        return page;
    }

    public void cleanup() {
        try {
            if (page != null) page.close();
            if (browser != null) browser.close();
            if (playwright != null) playwright.close();
            logger.info("Resources cleaned up.");
        } catch (Exception e) {
            logger.warn("Cleanup issue: {}", e.getMessage());
        }
    }

}
