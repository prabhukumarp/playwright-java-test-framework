package com.prabhu.myapp.config.utils;

import com.microsoft.playwright.*;
import com.prabhu.myapp.helpers.ExceptionHelper;
import com.prabhu.myapp.helpers.LoggerHelper;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;

@Singleton
public class PlaywrightManager {

    private static final Logger logger = LoggerHelper.getLogger(PlaywrightManager.class);

    private final DriverFactory driverFactory;

    // Thread-safe browser context and page management
    private static final ThreadLocal<Playwright> threadLocalPlaywright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> threadLocalBrowser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> threadLocalBrowserContext = new ThreadLocal<>();
    private static final ThreadLocal<Page> threadLocalPage = new ThreadLocal<>();

    @Inject
    public PlaywrightManager(DriverFactory driverFactory) {
        this.driverFactory = driverFactory;
    }

    public void init() {
        try {
            logger.info("Initializing PlaywrightManager...");
            Playwright playwright = Playwright.create();
            Browser browser = driverFactory.createBrowser(playwright);
            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            threadLocalPlaywright.set(playwright);
            threadLocalBrowser.set(browser);
            threadLocalBrowserContext.set(context);
            threadLocalPage.set(page);

            logger.info("Playwright, BrowserContext, and Page initialized for thread: {}", Thread.currentThread().getName());
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "Failed during Playwright initialization", e);
        }
    }

    public Page getPage() {
        return threadLocalPage.get();
    }

    public BrowserContext getBrowserContext() {
        return threadLocalBrowserContext.get();
    }

    public void cleanup() {
        try {
            Page page = threadLocalPage.get();
            BrowserContext context = threadLocalBrowserContext.get();
            Browser browser = threadLocalBrowser.get();
            Playwright playwright = threadLocalPlaywright.get();

            if (page != null) page.close();
            if (context != null) context.close();
            if (browser != null) browser.close();
            if (playwright != null) playwright.close();

            logger.info("Resources cleaned up for thread: {}", Thread.currentThread().getName());
        } catch (Exception e) {
            logger.warn("Cleanup issue: {}", e.getMessage());
        } finally {
            threadLocalPage.remove();
            threadLocalBrowserContext.remove();
            threadLocalBrowser.remove();
            threadLocalPlaywright.remove();
        }
    }
}
