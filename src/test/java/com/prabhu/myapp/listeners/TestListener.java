package com.prabhu.myapp.listeners;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.microsoft.playwright.Page;
import com.prabhu.myapp.base.BaseTest;
import com.prabhu.myapp.config.models.FrameworkConfig;
import com.prabhu.myapp.di.FrameworkModule;
import com.prabhu.myapp.helpers.FileHelper;
import com.prabhu.myapp.helpers.LoggerHelper;
import com.prabhu.myapp.utils.reports.ExtentTestManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Base64;

public class TestListener implements ITestListener {

    private static final Logger logger = LoggerHelper.getLogger(TestListener.class);

    private static final Injector injector = Guice.createInjector(new FrameworkModule());
    private static final FrameworkConfig frameworkConfig = injector.getInstance(FrameworkConfig.class);

    private static final String screenshotPath = frameworkConfig.getReports().getScreenshotsPath();

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentTestManager.startTest(testName, "Execution started for: " + testName);
        logger.info("▶️ Test Started: {}", testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Object currentInstance = result.getInstance();

        if (frameworkConfig.getReports().isCaptureOnSuccess() && currentInstance instanceof BaseTest baseTest) {
            Page page = baseTest.getPage();
            String testName = result.getMethod().getMethodName();
            captureScreenshot(result);
            if (page != null) {
                try {
                    byte[] screenshotBytes = page.screenshot(new Page.ScreenshotOptions());
                    String base64Screenshot = Base64.getEncoder().encodeToString(screenshotBytes);

                    ExtentTestManager.getTest().pass("✅ Test Passed. Screenshot below:")
                            .addScreenCaptureFromBase64String(base64Screenshot, testName + "_screenshot");

                    logger.info("✅ Test Passed: {} (screenshot captured)", testName);
                } catch (Exception e) {
                    logger.warn("Could not capture screenshot for passed test: {}", testName, e);
                }
            }
        } else {
            ExtentTestManager.getTest().pass("✅ Test Passed.");
            logger.info("✅ Test Passed: {}", result.getMethod().getMethodName());
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Object currentInstance = result.getInstance();

        if (currentInstance instanceof BaseTest baseTest) {
            Page page = baseTest.getPage();
            String testName = result.getMethod().getMethodName();
            captureScreenshot(result);
            if (page != null) {
                try {
                    byte[] screenshotBytes = page.screenshot(new Page.ScreenshotOptions());
                    String base64Screenshot = Base64.getEncoder().encodeToString(screenshotBytes);

                    ExtentTestManager.getTest().fail("❌ Test Failed. Screenshot below:")
                            .addScreenCaptureFromBase64String(base64Screenshot, testName + "_screenshot");

                    logger.error("❌ Test Failed: {}", testName);
                } catch (Exception e) {
                    logger.error("Failed to capture Base64 screenshot", e);
                }
            } else {
                ExtentTestManager.getTest().fail("❌ Test Failed. Page object was null, screenshot not captured.");
                logger.error("Unable to Capture Screenshot as page is null");
            }
        }
    }



    @Override
    public void onFinish(ITestContext context) {
        ExtentTestManager.endTest();
    }

    private void captureScreenshot(ITestResult result) {
        Object testInstance = result.getInstance();
        if (testInstance instanceof BaseTest baseTest) {
            Page page = baseTest.getPage();
            if (page != null) {
                String testName = result.getMethod().getMethodName();
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String fileName = testName + "_" + timestamp + ".png";
                String screenshotFile = Paths.get(screenshotPath, fileName).toString();

                FileHelper.ensureDirectoryExists(screenshotPath);
                page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotFile)));
                //ExtentTestManager.getTest().addScreenCaptureFromPath(FileHelper.toWebPath(screenshotFile));
            } else {
                logger.warn("📸 Could not capture screenshot. Page is null.");
            }
        }
    }
}
