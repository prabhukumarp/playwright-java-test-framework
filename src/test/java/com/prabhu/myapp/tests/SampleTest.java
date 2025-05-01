package com.prabhu.myapp.tests;

import com.microsoft.playwright.Page;
import com.prabhu.myapp.base.BaseTest;
import com.prabhu.myapp.config.models.FrameworkConfig;
import com.prabhu.myapp.di.FrameworkModule;
import com.prabhu.myapp.helpers.LoggerHelper;
import com.prabhu.myapp.helpers.FileHelper;
import jakarta.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Guice(modules = FrameworkModule.class)
//@Listeners(com.prabhu.myapp.listeners.TestListener.class)
public class SampleTest extends BaseTest {

    @Inject
    private FrameworkConfig frameworkConfig;
    private static final Logger logger = LoggerHelper.getLogger(SampleTest.class);
    //private Page page;

    @Test
    public void openHomePageTest() {
        Page page = getPage(); // ✅ Use from BaseTest
        String baseUrl = frameworkConfig.getApplicationConfig().getBaseUrl();

        page.navigate(baseUrl);

        String pageTitle = page.title();
        logger.info("Navigated to URL: {}", baseUrl);
        logger.info("Page Title: {}", pageTitle);

        Assert.assertTrue(pageTitle.trim().equalsIgnoreCase("Your Stor"), "Title did not match with actual title! "+pageTitle);
    }


}
