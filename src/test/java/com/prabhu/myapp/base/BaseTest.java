package com.prabhu.myapp.base;

import com.google.inject.Inject;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.prabhu.myapp.config.models.ApplicationConfig;
import com.prabhu.myapp.config.models.BrowserConfig;
import com.prabhu.myapp.config.models.FrameworkConfig;
import com.prabhu.myapp.config.utils.DriverFactory;
import com.prabhu.myapp.helpers.LoggerHelper;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;

@Listeners({
        com.prabhu.myapp.listeners.TestListener.class,
        com.prabhu.myapp.utils.reports.PdfSummaryReportGenerator.class
})
public class BaseTest {

    @Inject
    protected FrameworkConfig frameworkConfig;

    @Inject
    protected DriverFactory driverFactory;

    protected Playwright playwright;
    protected Browser browser;

    protected ApplicationConfig applicationConfig;
    protected BrowserConfig browserConfig;

    // ✅ ThreadLocal to support parallel execution
    private static final ThreadLocal<Page> threadLocalPage = new ThreadLocal<>();

    protected final Logger logger = LoggerHelper.getLogger(BaseTest.class);

    // ✅ Getter to be used by listeners
    public Page getPage() {
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
        logger.info("🚀 Initializing Playwright and launching browser...");
        playwright = Playwright.create();
        browser = driverFactory.createBrowser(playwright);

        Page page = browser.newPage(); // ✅ Create page instance
        threadLocalPage.set(page);     // ✅ Store it thread-locally

        logger.info("✅ Browser launched: {}", browserConfig.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        Page page = threadLocalPage.get();
        if (page != null) {
            page.close(); // ✅ Clean up page instance
            threadLocalPage.remove();
        }

        if (browser != null) {
            browser.close();
            logger.info("🧹 Browser closed.");
        }
        if (playwright != null) {
            playwright.close();
            logger.info("🧹 Playwright shut down.");
        }
    }

    @AfterClass(alwaysRun = true)
    public void cleanUpClass() {
        logger.info("🏁 Test class execution completed.");
    }
}
