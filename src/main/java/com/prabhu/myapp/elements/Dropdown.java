package com.prabhu.myapp.elements;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.SelectOption;
import com.prabhu.myapp.helpers.ExceptionHelper;
import com.prabhu.myapp.helpers.LoggerHelper;
import org.slf4j.Logger;

public class Dropdown {

    private static final Logger logger = LoggerHelper.getLogger(Dropdown.class);

    /**
     * Selects the option by visible text in the dropdown.
     *
     * @param locator The locator of the dropdown.
     * @param value The option to select by visible text.
     */
    public static void selectByText(Locator locator, String value) {
        try {
            locator.selectOption(value); // Directly passing value
            logger.info("🔽 Selected option '{}' from dropdown: {}", value, locator);
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "❌ Failed to select option from dropdown: " + locator, e);
        }
    }

    /**
     * Selects the option by value in the dropdown.
     *
     * @param locator The locator of the dropdown.
     * @param value The option to select by value.
     */
    public static void selectByValue(Locator locator, String value) {
        try {
            locator.selectOption(new String[] {value}); // Directly passing value
            logger.info("🔽 Selected value '{}' from dropdown: {}", value, locator);
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "❌ Failed to select value from dropdown: " + locator, e);
        }
    }

    /**
     * Selects the option by index in the dropdown.
     *
     * @param locator The locator of the dropdown.
     * @param index The option to select by index.
     */
    public static void selectByIndex(Locator locator, int index) {
        try {
            locator.selectOption(new SelectOption().setIndex(1)); // Correcting the syntax
            logger.info("🔽 Selected option index '{}' from dropdown: {}", index, locator);
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "❌ Failed to select option by index from dropdown: " + locator, e);
        }
    }
}
