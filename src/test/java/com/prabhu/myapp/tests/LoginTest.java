package com.prabhu.myapp.tests;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.prabhu.myapp.base.BaseTest;
import com.prabhu.myapp.config.models.FrameworkConfig;
import com.prabhu.myapp.helpers.LoggerHelper;
import jakarta.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    @Inject
    private FrameworkConfig frameworkConfig;
    private static final Logger logger = LoggerHelper.getLogger(SampleTest.class);

    @Test
    public void openHomePageTest() {
        Page page = getPage(); // ✅ Use from BaseTest
        String baseUrl = frameworkConfig.getApplicationConfig().getBaseUrl();
        page.navigate(baseUrl);
        page.waitForLoadState(LoadState.NETWORKIDLE); // Wait until network
        // Click on "My Account" and wait for navigation

        String pageTitle = page.title();
        logger.info("Navigated to URL: {}", baseUrl);
        logger.info("Page Title: {}", pageTitle);
        Assert.assertTrue(pageTitle.trim().equalsIgnoreCase("Your Store"), "Title did not match with actual title! "+pageTitle);

        Locator myAccountLink = page.locator("text=My Account");
        myAccountLink.waitFor(); // Wait until it's available
        myAccountLink.click();

        // Wait for login form to appear
        page.locator("#input-email").waitFor();

        // Enter username and password
        page.fill("#input-email", "invalid@example.com");
        page.fill("#input-password", "wrongpassword");

        // Click the Login button and wait for alert
        Locator loginButton = page.locator("input[type='submit']");
        loginButton.waitFor(); // Ensure button is visible
        loginButton.click();

        // Wait for alert message and assert failure
        Locator alertMessage = page.locator(".alert.alert-danger.alert-dismissible");
        System.out.println("Alert message: "+ alertMessage.textContent());
        alertMessage.waitFor(); // Ensure alert appears
        Assert.assertTrue(alertMessage.isVisible(), "Warning: No match for E-Mail Address and/or Password.");

        System.out.println("Test Passed: Error message displayed for invalid credentials");


    }
}
