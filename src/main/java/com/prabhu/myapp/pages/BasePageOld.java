package com.prabhu.myapp.pages;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.prabhu.myapp.config.models.ApplicationConfig;
import com.prabhu.myapp.helpers.ExceptionHelper;
import com.prabhu.myapp.helpers.LoggerHelper;
import jakarta.inject.Inject;
import org.slf4j.Logger;

import java.nio.file.Paths;
import java.util.function.Predicate;

public abstract class BasePageOld {

    protected final Page page;
    protected final ApplicationConfig appConfig;
    protected final Logger logger = LoggerHelper.getLogger(getClass());

    public BasePageOld(Page page, ApplicationConfig appConfig) {
        this.page = page;
        this.appConfig = appConfig;
    }

    /**
     * Clicks on a given locator and waits until the element is visible.
     *
     * @param locator the locator of the element to click
     */
    protected void click(Locator locator) {
        waitUntilElementVisible(locator);
        try {
            locator.click();
            logger.info("✅ Clicked on: {}", locator);
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "❌ Failed to click: " + locator, e);
        }
    }

    /**
     * Types the given text into the specified locator.
     *
     * @param locator the locator of the input field
     * @param text    the text to type into the field
     */
    protected void type(Locator locator, String text) {
        waitUntilElementVisible(locator);
        try {
            locator.fill(text);
            logger.info("✅ Typed '{}' into: {}", text, locator);
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "❌ Failed to type in: " + locator, e);
        }
    }

    /**
     * Clears the existing text and types the new text into the specified locator.
     *
     * @param locator the locator of the input field
     * @param text    the text to type into the field
     */
    protected void clearAndType(Locator locator, String text) {
        waitUntilElementVisible(locator);
        try {
            locator.fill("");
            locator.type(text);
            logger.info("✏️ Cleared and typed '{}' into: {}", text, locator);
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "❌ Failed to clear/type: " + locator, e);
        }
    }

    /**
     * Retrieves the text content of the specified locator.
     *
     * @param locator the locator from which to retrieve text
     * @return the text content of the element
     */
    protected String getText(Locator locator) {
        waitUntilElementVisible(locator);
        try {
            String text = locator.textContent();
            logger.info("✅ Retrieved text '{}' from: {}", text, locator);
            return text;
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "❌ Failed to get text from: " + locator, e);
            return null;
        }
    }

    /**
     * Waits for the specified element to become visible.
     *
     * @param locator the locator of the element to wait for
     */
    protected void waitUntilElementVisible(Locator locator) {
        try {
            locator.waitFor(new Locator.WaitForOptions().setTimeout((double) appConfig.getTimeout() * 1000));
            logger.info("👁️ Element visible: {}", locator);
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "❌ Element not visible: " + locator, e);
        }
    }

    /**
     * Waits for the page to load completely by checking both DOM content and network idle state.
     */
    public void waitForPageToLoad() {
        try {
            page.waitForLoadState(LoadState.DOMCONTENTLOADED);
            page.waitForLoadState(LoadState.NETWORKIDLE);
            logger.info("✅ Page loaded completely: {}", page.url());
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "❌ Page did not load completely", e);
        }
    }

    /**
     * Checks the specified checkbox if it is not already checked.
     *
     * @param locator the locator of the checkbox
     */
    protected void check(Locator locator) {
        try {
            if (!locator.isChecked()) {
                locator.check();
                logger.info("☑️ Checked: {}", locator);
            }
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "❌ Failed to check: " + locator, e);
        }
    }

    /**
     * Selects an option from a dropdown or select element.
     *
     * @param locator the locator of the dropdown/select element
     * @param value   the value to select from the dropdown
     */
    protected void select(Locator locator, String value) {
        try {

            locator.selectOption(value);
            logger.info("🔽 Selected '{}' from: {}", value, locator);
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "❌ Failed to select from: " + locator, e);
        }
    }

    /**
     * Asserts that the text content of the specified element matches the expected text.
     *
     * @param locator  the locator of the element
     * @param expected the expected text
     */
    protected void assertText(Locator locator, String expected) {
        try {
            String actual = locator.textContent();
            if (!expected.equals(actual)) {
                throw new AssertionError("Expected '" + expected + "', but got '" + actual + "'");
            }
            logger.info("✅ Asserted text '{}' on: {}", expected, locator);
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "❌ Text assertion failed for: " + locator, e);
        }
    }

    /**
     * Checks if the specified element is visible.
     *
     * @param locator the locator of the element
     * @return true if the element is visible, false otherwise
     */
    public boolean isVisible(Locator locator) {
        return locator.isVisible();
    }

    /**
     * Retrieves the title of the current page.
     *
     * @return the title of the page
     */
    public String getTitle() {
        return page.title();
    }

    /**
     * Hovers over the specified element.
     *
     * @param locator the locator of the element to hover over
     */
    public void hover(Locator locator) {
        waitUntilElementVisible(locator);
        try {
            locator.hover();
            logger.info("🖱️ Hovered over: {}", locator);
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "❌ Failed to hover over: " + locator, e);
        }
    }

    /**
     * Scrolls the specified element into view if it is not already in the viewport.
     *
     * @param locator the locator of the element to scroll into view
     */
    public void scrollIntoView(Locator locator) {
        try {
            locator.scrollIntoViewIfNeeded();
            logger.info("📜 Scrolled into view: {}", locator);
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "❌ Failed to scroll into view: " + locator, e);
        }
    }

    /**
     * Retrieves the value of the specified attribute from an element.
     *
     * @param locator   the locator of the element
     * @param attribute the name of the attribute to retrieve
     * @return the value of the attribute, or null if it could not be retrieved
     */
    public String getAttribute(Locator locator, String attribute) {
        try {
            return locator.getAttribute(attribute);
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "❌ Failed to get attribute '" + attribute + "' from: " + locator, e);
            return null;
        }
    }

    /**
     * Uploads a file to the specified file input element.
     *
     * @param locator  the locator of the file input element
     * @param filePath the path of the file to upload
     */
    public void uploadFile(Locator locator, String filePath) {
        try {
            locator.setInputFiles(Paths.get(filePath));
            logger.info("📁 Uploaded file: {}", filePath);
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "❌ Failed to upload file to: " + locator, e);
        }
    }

    /**
     * Switches to a frame by its name attribute.
     *
     * @param frameName the name of the frame
     * @return the Frame object if found, or throws an exception if not found
     */
    public Frame switchToFrameByName(String frameName) {
        try {
            Frame frame = page.frames()
                    .stream()
                    .filter(f -> frameName.equals(f.name()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Frame not found: " + frameName));
            logger.info("🪟 Switched to frame: {}", frameName);
            return frame;
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "❌ Failed to switch to frame: " + frameName, e);
            return null;
        }
    }

    /**
     * Executes a JavaScript snippet on the current page.
     *
     * @param script the JavaScript code to execute
     * @return the result of the JavaScript execution
     */
    public Object executeJS(String script) {
        try {
            Object result = page.evaluate(script);
            logger.info("⚙️ Executed JS: {}", script);
            return result;
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "❌ Failed to execute JS: " + script, e);
            return null;
        }
    }

    /**
     * Retrieves the number of elements matching the specified locator.
     *
     * @param locator the locator of the elements to count
     * @return the number of matching elements
     */
    public int getElementCount(Locator locator) {
        try {
            int count = locator.count();
            logger.info("🔢 Element count: {} for {}", count, locator);
            return count;
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "❌ Failed to count elements: " + locator, e);
            return 0;
        }
    }

    /**
     * Waits for the current URL to contain a specified substring.
     *
     * @param partialUrl the substring to check for in the URL
     */
    public void waitForUrlContains(String partialUrl) {
        try {
            page.waitForURL((Predicate<String>) url -> url.contains(partialUrl),
                    new Page.WaitForURLOptions().setTimeout((double) appConfig.getTimeout() * 1000));
            logger.info("🌐 URL contains: {}", partialUrl);
        } catch (Exception e) {
            ExceptionHelper.logAndThrow(logger, "❌ URL did not contain: " + partialUrl, e);
        }
    }

    /**
     * Clicks the specified locator with retry logic in case of failure.
     *
     * @param locator  the locator of the element to click
     * @param attempts the number of retry attempts before failing
     */
    public void clickWithRetry(Locator locator, int attempts) {
        int count = 0;
        while (count < attempts) {
            try {
                waitUntilElementVisible(locator);
                locator.click();
                logger.info("✅ Clicked (attempt {}): {}", count + 1, locator);
                return;
            } catch (Exception e) {
                logger.warn("⚠️ Click failed (attempt {}): {}", count + 1, locator);
                count++;
            }
        }
        ExceptionHelper.logAndThrow(logger, "❌ Failed to click after " + attempts + " attempts: " + locator, null);
    }
}
