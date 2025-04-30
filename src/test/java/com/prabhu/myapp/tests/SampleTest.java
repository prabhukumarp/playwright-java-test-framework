package com.prabhu.myapp.tests;

import com.microsoft.playwright.Page;
import com.prabhu.myapp.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SampleTest extends BaseTest {

    @Test
    public void openHomePageTest() {
        Page page = browser.newPage();
        String baseUrl = applicationConfig.getBaseUrl(); // Should be defined in your qa.yml or dev.yml
        page.navigate(baseUrl);

        String pageTitle = page.title();
        System.out.println("Page title: " + pageTitle);

        Assert.assertTrue(pageTitle.toLowerCase().contains("your expected text"), "Title did not match!");
    }
}
