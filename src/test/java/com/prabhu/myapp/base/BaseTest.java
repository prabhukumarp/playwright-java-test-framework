package com.prabhu.myapp.base;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;
import com.google.inject.Inject;
import com.prabhu.myapp.config.models.ApplicationConfig;
import com.prabhu.myapp.config.models.EnvironmentConfig;
import com.prabhu.myapp.config.utils.DriverFactory;
import com.prabhu.myapp.config.utils.EnvironmentConfigLoader;
import com.prabhu.myapp.config.utils.YamlConfigLoader;
import com.prabhu.myapp.helpers.LoggerHelper;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;

public class BaseTest {

    @Inject
    private EnvironmentConfigLoader environmentConfigLoader;

    protected static Playwright playwright;
    protected static Browser browser;

    protected static EnvironmentConfig environmentConfig;
    protected static ApplicationConfig applicationConfig;

    protected static DriverFactory driverFactory;

    protected static final Logger logger = LoggerHelper.getLogger(BaseTest.class);

    @BeforeClass
    public void setUpClass() {
        environmentConfig = environmentConfigLoader.get();  // ✅ Uses injected config loader
        applicationConfig = environmentConfig.getApplicationConfig();
        driverFactory = new DriverFactory(environmentConfig);
        driverFactory.loadEnvironmentConfig(environmentConfig);
    }

    @BeforeMethod
    public void setUp() {
        logger.info("🚀 Starting Playwright...");
        playwright = Playwright.create();
        String browserType = environmentConfig.getBrowser();        // chromium, firefox, etc.
        browser = driverFactory.createBrowser(playwright, browserType);
        logger.info("✅ Browser launched: " + browserType);
    }

    @AfterMethod
    public void tearDown() {
        if (browser != null) {
            browser.close();
            logger.info("🧹 Browser closed");
        }
        if (playwright != null) {
            playwright.close();
            logger.info("🧹 Playwright shutdown");
        }
    }

    @AfterClass
    public void cleanUpClass() {
        logger.info("🏁 Test class execution complete");
        // Currently nothing extra to clean — later we can handle reports/cleanup here.
    }
}
