package com.prabhu.myapp.elements;

import com.microsoft.playwright.Locator;
import com.prabhu.myapp.helpers.ExceptionHelper;
import com.prabhu.myapp.helpers.LoggerHelper;
import org.slf4j.Logger;

public class Button {

    private static final Logger logger = LoggerHelper.getLogger(Button.class);

    /**
     * Clicks on the button located by the given locator.
     *
     * @param locator The locator of the button to click.
     */
    public static void click(Locator locator) {
        try {
            locator.click();
            logger.info("✅ Clicked on button: {}", locator);
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "❌ Failed to click button: " + locator, e);
        }
    }

    /**
     * Waits until the button is visible and then clicks it.
     *
     * @param locator The locator of the button.
     * @param timeoutMs The timeout in milliseconds.
     */
    public static void clickWithWait(Locator locator, int timeoutMs) {
        try {
            locator.waitFor(new Locator.WaitForOptions().setTimeout(timeoutMs));
            click(locator);
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "❌ Failed to wait and click button: " + locator, e);
        }
    }
}
