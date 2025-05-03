package com.prabhu.myapp.tests;

import com.microsoft.playwright.Page;
import com.prabhu.myapp.base.BaseTest;
import com.prabhu.myapp.config.models.ApplicationConfig;
import com.prabhu.myapp.config.models.EnvironmentConfig;
import com.prabhu.myapp.pages.BasePage;
import com.prabhu.myapp.pages.BlogPage;
import com.prabhu.myapp.pages.LoginPage;
import com.prabhu.myapp.pages.components.HeaderSection;
import jakarta.inject.Inject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {
    @Inject
    private EnvironmentConfig environmentConfig;

    @Test
    public void testLoginErrorMessage() {
        Page page = getPage(); // You should get this from PlaywrightManager or similar
        ApplicationConfig appConfig = environmentConfig.getApplicationConfig(); // Load this from your DI or config loader
        String username = "abc@xyz.ccom";
        String password = "testtest12";

        // Navigate to Home Page and then Blog Page using Header
        page.navigate(appConfig.getBaseUrl());

        HeaderSection header = new HeaderSection(page, appConfig);
        BasePage<?> result = header.navigateToLoginPage()
                .login(username, password);

        if (result instanceof LoginPage) {
            Assert.assertTrue(((LoginPage) result).isLoginPageVisible());
            Assert.assertEquals(
                    ((LoginPage) result).getIncorrectLoginAlertMessage(),
                    "Warning: No match for E-Mail Address and/or Password."
            );
        } else {
            Assert.fail("Login should have failed, but navigated to: " + result.getClass().getSimpleName());
        }

    }
}
