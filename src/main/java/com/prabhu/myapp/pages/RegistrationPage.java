package com.prabhu.myapp.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.prabhu.myapp.config.models.ApplicationConfig;
import com.prabhu.myapp.pages.components.HeaderSection;
import jakarta.inject.Inject;

public class RegistrationPage extends BasePage<RegistrationPage> {

    private final Locator registrationForm;

    @Inject
    public RegistrationPage(Page page, ApplicationConfig appConfig) {
        super(page, appConfig);
        this.registrationForm = page.locator("#content > form"); // Adjust selector as needed
    }

    public HeaderSection header() {
        return headerSection;
    }

    public boolean isPageLoaded() {
        return registrationForm.isVisible(); // More robust than title
    }
}
