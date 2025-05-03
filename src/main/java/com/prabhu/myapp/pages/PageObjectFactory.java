package com.prabhu.myapp.pages;

import com.microsoft.playwright.Page;
import com.prabhu.myapp.config.models.ApplicationConfig;
import com.prabhu.myapp.pages.components.HeaderSection;
import jakarta.inject.Inject;

public class PageObjectFactory {

    private final ApplicationConfig appConfig;

    @Inject
    public PageObjectFactory(ApplicationConfig appConfig) {
        this.appConfig = appConfig;
    }

    public HomePage navigateToHomePage(Page page) {
        page.navigate(appConfig.getBaseUrl());
        HeaderSection header = new HeaderSection(page, appConfig); // Construct here
        return new HomePage(page, appConfig);
    }

    // Add more creators as needed
}
