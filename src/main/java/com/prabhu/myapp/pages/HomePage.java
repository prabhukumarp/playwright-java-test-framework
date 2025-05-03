package com.prabhu.myapp.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.prabhu.myapp.config.models.ApplicationConfig;
import com.prabhu.myapp.pages.components.HeaderSection;
import jakarta.inject.Inject;

public class HomePage extends BasePage<HomePage> {

    private final Locator contentHome;

    @Inject
    public HomePage(Page page, ApplicationConfig appConfig) {
        super(page, appConfig);
        this.contentHome = page.locator("#common-home"); // Adjust for actual landing element
    }

    public HeaderSection header() {
        return headerSection;
    }

    public boolean isPageLoaded() {
        return contentHome.isVisible(); // More consistent check
    }
}
