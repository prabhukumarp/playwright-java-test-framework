package com.prabhu.myapp.elements;

import com.microsoft.playwright.Locator;
import com.prabhu.myapp.helpers.ExceptionHelper;
import com.prabhu.myapp.helpers.LoggerHelper;
import org.slf4j.Logger;

public class TextField {

    private static final Logger logger = LoggerHelper.getLogger(TextField.class);

    /**
     * Types the given text into the text field located by the locator.
     *
     * @param locator The locator of the text field.
     * @param text The text to type.
     */
    public static void type(Locator locator, String text) {
        try {
            locator.fill(text);
            logger.info("✅ Typed '{}' into text field: {}", text, locator);
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "❌ Failed to type text in: " + locator, e);
        }
    }

    /**
     * Clears the text field and types the given text.
     *
     * @param locator The locator of the text field.
     * @param text The text to type.
     */
    public static void clearAndType(Locator locator, String text) {
        try {
            locator.fill("");  // Clear existing text
            locator.type(text);
            logger.info("✏️ Cleared and typed '{}' into text field: {}", text, locator);
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "❌ Failed to clear and type in: " + locator, e);
        }
    }

    /**
     * Retrieves the text from the text field.
     *
     * @param locator The locator of the text field.
     * @return The text of the field.
     */
    public static String getText(Locator locator) {
        try {
            return locator.textContent();
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "❌ Failed to get text from: " + locator, e);
            return null;
        }
    }
}
