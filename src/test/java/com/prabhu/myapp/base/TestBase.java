package com.prabhu.myapp.base;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.microsoft.playwright.*;
import com.prabhu.myapp.config.models.EnvironmentConfig;
import com.prabhu.myapp.config.models.FrameworkConfig;
import com.prabhu.myapp.config.utils.DriverFactory;
import com.prabhu.myapp.di.FrameworkModule;
import org.testng.annotations.*;

public abstract class TestBase {

    protected static Injector injector;
    protected static FrameworkConfig frameworkConfig;
    protected static EnvironmentConfig environmentConfig;

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;

    @BeforeSuite
    public void beforeSuite() {
        // You could set up logs or reports here if needed
    }

    @BeforeClass
    public void setupClass() {
        // Guice DI only once per test class
        injector = Guice.createInjector(new FrameworkModule());
        frameworkConfig = injector.getInstance(FrameworkConfig.class);
        environmentConfig = injector.getInstance(EnvironmentConfig.class);
    }

    @BeforeMethod
    public void setupMethod() {
        playwright = Playwright.create();
        DriverFactory driverFactory = injector.getInstance(DriverFactory.class);
        browser = driverFactory.createBrowser(playwright);
        browserContext = browser.newContext();
        page = browserContext.newPage();
    }

    @AfterMethod(alwaysRun = true)
    public void teardownMethod() {
        if (page != null) page.close();
        if (browserContext != null) browserContext.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @AfterClass(alwaysRun = true)
    public void teardownClass() {
        // Cleanup or flush DI resources if necessary
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        // Final reporting flush, cleanup
    }
}
