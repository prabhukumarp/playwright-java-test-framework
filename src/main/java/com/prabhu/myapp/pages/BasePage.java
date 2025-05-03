package com.prabhu.myapp.pages;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.prabhu.myapp.config.models.ApplicationConfig;
import com.prabhu.myapp.config.models.FrameworkConfig;
import com.prabhu.myapp.exceptions.AutomationException;
import com.prabhu.myapp.helpers.ExceptionHelper;
import com.prabhu.myapp.helpers.LoggerHelper;
import com.prabhu.myapp.pages.components.HeaderSection;
import jakarta.inject.Inject;
import org.slf4j.Logger;

import java.nio.file.Paths;
import java.time.Duration;
import java.util.function.Predicate;

public abstract class BasePage<T extends BasePage<T>> {

    protected final Page page;
    protected final ApplicationConfig appConfig;
    protected final HeaderSection headerSection;
    protected final Logger logger;

    public BasePage(Page page, ApplicationConfig appConfig) {
        this.page = page;
        this.appConfig = appConfig;
        this.headerSection = new HeaderSection(page, appConfig); // ✅ YES, keep this!
        this.logger = LoggerHelper.getLogger(getClass());
    }

    /**
     * Clicks on a given locator and waits until the element is visible.
     *
     * @param locator the locator of the element to click
     */
    @SuppressWarnings("unchecked")
    protected T click(Locator locator) {
        waitUntilElementVisible(locator);
        try {
            logger.info("Clicking element: {}", locator);
            locator.click();
            logger.info("✅ Clicked on: {}", locator);
            return  (T)this;
        } catch (Exception e) {
            logger.error("❌ Click failed on element: " + locator, e);
            throw new AutomationException("❌ Click failed on element: " + locator, e);
        }
    }

    /**
     * Double Clicks on a given locator
     *
     * @param locator the locator of the element to click
     */
    @SuppressWarnings("unchecked")
    public T doubleClick(Locator locator) {
        waitUntilElementVisible(locator);
        try {
            logger.info("Double-clicking on: {}", locator);
            locator.dblclick();
            logger.info("✅ Double-clicked on: {}", locator);
            return (T) this;
        } catch (Exception e) {
            logger.error("❌ Double-click failed on element: " + locator, e);
            throw new AutomationException("❌ Double-click failed on element: " + locator, e);
        }
    }


    /**
     * Types the given text into the specified locator.
     *
     * @param locator the locator of the input field
     * @param text    the text to type into the field
     */
    @SuppressWarnings("unchecked")
    protected T type(Locator locator, String text) {
        waitUntilElementVisible(locator);
        try {
            locator.fill(text);
            logger.info("✅ Typed '{}' into: {}", text, locator);
            return  (T) this;
        } catch (Exception e) {
            logger.error("❌ Failed to type: " + locator, e);
            throw new AutomationException("❌ Failed to type: " + locator, e);
        }
    }

    /**
     * Clears the existing text and types the new text into the specified locator.
     *
     * @param locator the locator of the input field
     * @param text    the text to type into the field
     */
    @SuppressWarnings("unchecked")
    protected T clearAndType(Locator locator, String text) {
        waitUntilElementVisible(locator);
        try {
            locator.fill("");
            locator.fill(text);
            logger.info("✏️ Cleared and typed '{}' into: {}", text, locator);
            return  (T)this;
        } catch (Exception e) {
            logger.error("❌ Failed to clear/type: " + locator, e);
            throw new AutomationException("❌ Failed to clear/type: " + locator, e);
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
    @SuppressWarnings("unchecked")
    protected T check(Locator locator) {
        try {
            if (!locator.isChecked()) {
                locator.check();
                logger.info("☑️ Checked: {}", locator);
            } else {
                logger.info("☑️ Already checked: {}", locator);
            }
            return (T) this;
        } catch (Exception e) {
            logger.error("❌ Check failed on element: " + locator, e);
            throw new AutomationException("❌ Check failed on element: " + locator, e);
        }
    }

    /**
     * Selects an option from a dropdown or select element.
     *
     * @param locator the locator of the dropdown/select element
     * @param value   the value to select from the dropdown
     */
    @SuppressWarnings("unchecked")
    protected T select(Locator locator, String value) {
        try {
            locator.selectOption(value);
            logger.info("🔽 Selected '{}' from: {}", value, locator);
            return  (T) this;
        } catch (Exception e) {
            logger.error("❌ Failed to select from element: " + locator, e);
            throw new AutomationException("❌ Failed to select from element: " + locator, e);
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
        try {
            return locator.isVisible();
        } catch (Exception e) {
            logger.warn("⚠️ Failed to check visibility for: {}", locator);
            return false;
        }
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
    @SuppressWarnings("unchecked")
    public T hover(Locator locator) {
        waitUntilElementVisible(locator);
        try {
            locator.hover();
            logger.info("🖱️ Hovered over: {}", locator);
            return (T) this;
        } catch (Exception e) {
            logger.error("❌ Failed to hover over: " + locator, e);
            throw new AutomationException("❌ Failed to hover over: " + locator, e);
        }
    }

    /**
     * Scrolls the specified element into view if it is not already in the viewport.
     *
     * @param locator the locator of the element to scroll into view
     */
    @SuppressWarnings("unchecked")
    public T scrollIntoView(Locator locator) {
        try {
            locator.scrollIntoViewIfNeeded();
            logger.info("📜 Scrolled into view: {}", locator);
            return (T) this;
        } catch (Exception e) {
            logger.error("❌ Failed to scrolled into view: " + locator, e);
            throw new AutomationException("❌ Failed to scrolled into view: " + locator, e);
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
    @SuppressWarnings("unchecked")
    public T uploadFile(Locator locator, String filePath) {
        try {
            locator.setInputFiles(Paths.get(filePath));
            logger.info("📁 Uploaded file: {}", filePath);
            return (T) this;
        } catch (Exception e) {
            logger.error("❌ Failed to upload file: " + locator, e);
            throw new AutomationException("❌ Failed to upload file: " + locator, e);
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

    /**
     * Waits until the locator disappears from the current page
     *
     * @param locator locator to disappear
     *
     */
    public void waitForElementToDisappear(Locator locator) {
        try {
            logger.info("Waiting for element to disappear: {}", locator);
            locator.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.HIDDEN)
                    .setTimeout(appConfig.getTimeout()*1000));
            logger.info("✅ Element disappeared: {}", locator);
        } catch (Exception e) {
            logger.error("❌ Element did not disappear: {}", locator, e);
            throw new AutomationException("❌ Timeout waiting for element to disappear: " + locator, e);
        }
    }

    public void assertElementExists(Locator locator) {
        try {
            logger.info("Asserting element exists: {}", locator);
            PlaywrightAssertions.assertThat(locator).isVisible();
            logger.info("✅ Element is visible: {}", locator);
        } catch (AssertionError e) {
            logger.error("❌ Element not visible: {}", locator, e);
            throw new AutomationException("❌ Assertion failed: element not visible: " + locator, e);
        }
    }

    @SuppressWarnings("unchecked")
    public T dragDrop(Locator source, Locator target) {
        try {
            logger.info("Dragging element [{}] to [{}]", source, target);
            source.dragTo(target);
            logger.info("✅ Drag and drop completed from [{}] to [{}]", source, target);
            return (T) this;
        } catch (Exception e) {
            logger.error("❌ Drag and drop failed from [{}] to [{}]", source, target, e);
            throw new AutomationException("❌ Drag and drop failed from source to target", e);
        }
    }


    /**
     * Setting defaultTimeout in millis
     *
     */

    protected int defaultTimeout() {
        return appConfig.getTimeout() * 1000; // milliseconds
    }

}
