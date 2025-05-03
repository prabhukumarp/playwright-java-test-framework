package com.prabhu.myapp.base;

import com.google.inject.Inject;
import com.microsoft.playwright.Page;
import com.prabhu.myapp.config.models.ApplicationConfig;
import com.prabhu.myapp.config.models.BrowserConfig;
import com.prabhu.myapp.config.models.FrameworkConfig;
import com.prabhu.myapp.config.utils.PlaywrightManager;
import com.prabhu.myapp.helpers.LoggerHelper;
import org.slf4j.Logger;
import org.testng.annotations.*;

@Listeners({
        com.prabhu.myapp.listeners.TestListener.class,
        com.prabhu.myapp.utils.reports.PdfSummaryReportGenerator.class
})
public class BaseTest {

    @Inject
    protected FrameworkConfig frameworkConfig;

    @Inject
    protected PlaywrightManager playwrightManager;

    protected ApplicationConfig applicationConfig;
    protected BrowserConfig browserConfig;

    private static final ThreadLocal<Page> threadLocalPage = new ThreadLocal<>();
    protected final Logger logger = LoggerHelper.getLogger(BaseTest.class);

    public Page getPage() {
        return threadLocalPage.get();
    }

    public static Page currentPage() {
        return threadLocalPage.get();
    }

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        applicationConfig = frameworkConfig.getApplicationConfig();
        browserConfig = applicationConfig.getBrowserConfig();

        logger.info("🔧 Framework initialized");
        logger.info("🌐 Base URL: {}", applicationConfig.getBaseUrl());
        logger.info("🧪 Browser: {}", browserConfig.getName());
        logger.info("🕶️ Headless: {}", browserConfig.isHeadless());
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        playwrightManager.init();
        Page page = playwrightManager.getPage();
        threadLocalPage.set(page);
    }

    @AfterMethod(alwaysRun = true)

    public void tearDown() {
        playwrightManager.cleanup();
        threadLocalPage.remove();
    }

    @AfterClass(alwaysRun = true)
    public void cleanUpClass() {
        logger.info("🏁 Test class execution completed.");
    }
}
