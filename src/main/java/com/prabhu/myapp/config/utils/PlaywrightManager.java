package com.prabhu.myapp.config.utils;

import com.microsoft.playwright.Playwright;
import jakarta.inject.Singleton;

@Singleton
public class PlaywrightManager {

    private Playwright playwright;

    public Playwright getPlaywright() {
        if (playwright == null) {
            playwright = Playwright.create();
        }
        return playwright;
    }

    public void close() {
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }
}
