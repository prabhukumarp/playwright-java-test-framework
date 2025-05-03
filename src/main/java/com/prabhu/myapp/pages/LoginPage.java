package com.prabhu.myapp.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.prabhu.myapp.config.models.ApplicationConfig;
import com.prabhu.myapp.pages.components.HeaderSection;
import jakarta.inject.Inject;

public class LoginPage extends BasePage<LoginPage> {

    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator loginButton;

    @Inject
    public LoginPage(Page page, ApplicationConfig appConfig) {
        super(page, appConfig);


        this.usernameInput = page.locator("#input-email");
        this.passwordInput = page.locator("#input-password");
        this.loginButton = page.locator("input[value='Login']");
    }

    public HeaderSection header() {
        return headerSection;
    }

    public LoginPage enterUsername(String username) {
        return type(usernameInput, username);
    }

    public LoginPage enterPassword(String password) {
        return type(passwordInput, password);
    }

    public BasePage<?> login(String username, String password) {
        return enterUsername(username).enterPassword(password).clickLogin();
    }

    public BasePage<?> clickLogin() {
        click(loginButton);
        page.waitForLoadState();

        if (page.url().contains("route=account/account")) {
            page.waitForSelector("div#content h2");
            return new AccountPage(page, appConfig);
        } else {
            //page.waitForSelector(".alert.alert-danger.alert-dismissible");
            page.waitForSelector(".alert.alert-danger.alert-dismissible", new Page.WaitForSelectorOptions().setTimeout(5000));
            return new LoginPage(page, appConfig);
        }
    }

    public String getIncorrectLoginAlertMessage() {
        Locator alert = page.locator(".alert.alert-danger.alert-dismissible");
        return getText(alert).trim();
    }


    public boolean isLoginPageVisible() {
        return page.url().contains("route=account/login");
    }
}
