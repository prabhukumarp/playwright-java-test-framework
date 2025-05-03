package com.prabhu.myapp.tests;

import com.microsoft.playwright.Page;
import com.prabhu.myapp.config.models.ApplicationConfig;
import com.prabhu.myapp.config.models.EnvironmentConfig;
import com.prabhu.myapp.pages.BlogPage;
import com.prabhu.myapp.pages.components.HeaderSection;
import com.prabhu.myapp.base.BaseTest;
import jakarta.inject.Inject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BlogPageTest extends BaseTest {

    @Inject
    private EnvironmentConfig environmentConfig;
    @Test
    public void testBlogPageLoadsCorrectly() {
        Page page = getPage(); // You should get this from PlaywrightManager or similar
        ApplicationConfig appConfig = environmentConfig.getApplicationConfig(); // Load this from your DI or config loader

        // Navigate to Home Page and then Blog Page using Header
        page.navigate(appConfig.getBaseUrl());

        HeaderSection header = new HeaderSection(page, appConfig);
        BlogPage blogPage = header.navigateToBlogPage();

        Assert.assertTrue(blogPage.isBlogPageLoaded(), "Blog Page did not load correctly");

        // Optional: Log or assert something from header
        System.out.println("Navigation to Blog Page successful.");
    }
}
