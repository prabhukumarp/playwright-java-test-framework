package com.prabhu.myapp.tests;

import com.microsoft.playwright.Page;
import com.prabhu.myapp.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.prabhu.myapp.helpers.LoggerHelper;
import org.apache.logging.log4j.Logger;

public class SampleTest extends BaseTest {
    protected static final Logger logger = LoggerHelper.getLogger(SampleTest.class);

    @Test
    public void openHomePageTest() {
        Page page = browser.newPage();
        String baseUrl = applicationConfig.getBaseUrl(); // Should be defined in your qa.yml or dev.yml
        page.navigate(baseUrl);

        String pageTitle = page.title();
        logger.info("Page Title: "+ pageTitle);

        Assert.assertTrue(pageTitle.toLowerCase().contains("your expected text"), "Title did not match!");
    }
}
