package com.prabhu.myapp.helpers;

import com.microsoft.playwright.Page;

import java.nio.file.Paths;

public class ScreenshotHelper {
    public static void captureScreenshot(Page page, String fileName) {
        String screenshotPath = "screenshots/" + fileName + ".png";
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)));
    }
}
