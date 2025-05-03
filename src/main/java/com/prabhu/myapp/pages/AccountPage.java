package com.prabhu.myapp.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.prabhu.myapp.config.models.ApplicationConfig;
import com.prabhu.myapp.pages.components.HeaderSection;
import jakarta.inject.Inject;

public class AccountPage extends BasePage<AccountPage> {

    private final Locator accountContent;

    @Inject
    public AccountPage(Page page, ApplicationConfig appConfig) {
        super(page, appConfig);
        this.accountContent = page.locator("div#content h2:has-text('My Account')");
    }

    public HeaderSection header() {
        return headerSection;
    }

    public boolean isPageLoaded() {
        return accountContent.isVisible(); // Verifies page content instead of title
    }
}
