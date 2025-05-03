package com.prabhu.myapp.elements;

import com.microsoft.playwright.Locator;
import com.prabhu.myapp.helpers.ExceptionHelper;
import com.prabhu.myapp.helpers.LoggerHelper;
import org.slf4j.Logger;

public class Checkbox {

    private static final Logger logger = LoggerHelper.getLogger(Checkbox.class);

    /**
     * Checks the checkbox if it is not already checked.
     *
     * @param locator The locator of the checkbox.
     */
    public static void check(Locator locator) {
        try {
            if (!locator.isChecked()) {
                locator.check();
                logger.info("☑️ Checked checkbox: {}", locator);
            }
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "❌ Failed to check checkbox: " + locator, e);
        }
    }

    /**
     * Unchecks the checkbox if it is checked.
     *
     * @param locator The locator of the checkbox.
     */
    public static void uncheck(Locator locator) {
        try {
            if (locator.isChecked()) {
                locator.uncheck();
                logger.info("❎ Unchecked checkbox: {}", locator);
            }
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "❌ Failed to uncheck checkbox: " + locator, e);
        }
    }
}
