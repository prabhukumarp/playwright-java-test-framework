package com.prabhu.myapp.config.utils;

import com.microsoft.playwright.Playwright;
import com.prabhu.myapp.helpers.ExceptionHelper;
import com.prabhu.myapp.helpers.LoggerHelper;
import jakarta.inject.Singleton;
import org.slf4j.Logger;

@Singleton
public class PlaywrightManager {

    private static final Logger logger = LoggerHelper.getLogger(PlaywrightManager.class);

    private Playwright playwright;

    public Playwright getPlaywright() {
        if (playwright == null) {
            try {
                logger.info("Creating Playwright instance...");
                playwright = Playwright.create();
                logger.info("Playwright instance created successfully.");
            } catch (Exception e) {
                ExceptionHelper.logAndThrow(logger, "Failed to create Playwright instance", e);
            }
        }
        return playwright;
    }

    public void close() {
        if (playwright != null) {
            try {
                logger.info("Closing Playwright instance...");
                playwright.close();
                playwright = null;
                logger.info("Playwright instance closed.");
            } catch (Exception e) {
                logger.warn("Failed to close Playwright instance: {}", e.getMessage());
            }
        }
    }
}
