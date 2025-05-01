package com.prabhu.myapp.tests;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.prabhu.myapp.base.BaseTest;
import com.prabhu.myapp.base.TestBase;
import com.prabhu.myapp.config.models.FrameworkConfig;
import com.prabhu.myapp.helpers.LoggerHelper;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Inject
    private FrameworkConfig frameworkConfig;

    private static final Logger logger = LoggerHelper.getLogger(LoginTest.class);

    @Test
    public void loginTest() {
        Page page = getPage(); // ✅ Inherited from BaseTest
        String baseUrl = frameworkConfig.getApplicationConfig().getBaseUrl();

        logger.info("Navigating to base URL: {}", baseUrl);
        page.navigate(baseUrl);
        page.waitForLoadState(LoadState.NETWORKIDLE);

        String pageTitle = page.title();
        logger.info("Page title after navigation: {}", pageTitle);
        Assert.assertTrue(pageTitle.trim().equalsIgnoreCase("Your Store"),
                "Expected title 'Your Store' but got: " + pageTitle);

        Locator myAccountLink = page.locator("#main-navigation a.dropdown-toggle[href*=\"account/account\"]");
        myAccountLink.waitFor();
        myAccountLink.click();
        logger.info("Clicked on 'My Account' link");

        // Wait and fill login form
        page.locator("#input-email").waitFor();
        page.fill("#input-email", "invalid@example.com");
        page.fill("#input-password", "wrongpassword");
        logger.info("Entered invalid login credentials");

        Locator loginButton = page.locator("input[type='submit']");
        loginButton.waitFor();
        loginButton.click();
        logger.info("Clicked login button");

        Locator alertMessage = page.locator(".alert.alert-danger.alert-dismissible");
        alertMessage.waitFor();
        String alertText = alertMessage.textContent();
        logger.info("Alert message displayed: {}", alertText);

        Assert.assertTrue(alertMessage.isVisible(),
                "Expected login failure alert to be visible, but it wasn't");

        logger.info("Test passed: Error message correctly displayed for invalid login.");
    }
}
