package com.prabhu.myapp.pages.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.prabhu.myapp.config.models.ApplicationConfig;
import com.prabhu.myapp.pages.BlogPage;
import com.prabhu.myapp.pages.HomePage;
import com.prabhu.myapp.pages.LoginPage;
import com.prabhu.myapp.pages.RegistrationPage;
import jakarta.inject.Inject;

public class HeaderSection {

    private final Page page;
    private final ApplicationConfig appConfig;

    // Locators
    private final Locator myAccountDropdown;
    private final Locator loginLink;
    private final Locator registerLink;
    private final Locator homeLink;
    private final Locator blogLink;

    @Inject
    public HeaderSection(Page page, ApplicationConfig appConfig) {
        this.page = page;
        this.appConfig = appConfig;

        this.myAccountDropdown = page.locator("#main-navigation a.dropdown-toggle[href*='account/account']");
        this.loginLink = page.locator("ul.dropdown-menu li a", new Page.LocatorOptions().setHasText("Login"));
        this.registerLink = page.locator("ul.dropdown-menu li a", new Page.LocatorOptions().setHasText("Register"));
        this.homeLink = page.locator("#main-navigation a.nav-link[href*='common/home']");
        this.blogLink = page.locator("#main-navigation a.nav-link[href*='blog/home']");
    }

    public LoginPage clickMyAccountDropdown() {
        myAccountDropdown.click();
        waitForPageToLoad();
        return new LoginPage(page, appConfig); // Pass 'this' to the LoginPage constructor
    }

    public void hoverOverMyAccount() {
        myAccountDropdown.hover();
    }

    public LoginPage navigateToLoginPage() {
        hoverOverMyAccount();
        loginLink.click();
        waitForPageToLoad();
        return new LoginPage(page, appConfig); // Pass 'this' to the LoginPage constructor
    }

    public RegistrationPage navigateToRegistrationPage() {
        hoverOverMyAccount();
        registerLink.click();
        waitForPageToLoad();
        return new RegistrationPage(page, appConfig); // Pass 'this' to the RegistrationPage constructor
    }

    public HomePage navigateToHomePage() {
        homeLink.click();
        waitForPageToLoad();
        return new HomePage(page, appConfig); // Pass 'this' to the HomePage constructor
    }

    public BlogPage navigateToBlogPage() {
        blogLink.click();
        waitForPageToLoad();
        return new BlogPage(page, appConfig); // Pass 'this' to the BlogPage constructor
    }

    private void waitForPageToLoad() {
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

}
