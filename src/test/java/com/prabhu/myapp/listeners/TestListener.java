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
        String testName = result.getMethod().getMethodName();
        ExtentTestManager.getTest().pass("✅ Test Passed");

        if (frameworkConfig.getReports().isCaptureOnSuccess()) {
            captureScreenshot(result);
        }

        logger.info("✅ Test Passed: {}", testName);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentTestManager.getTest().fail("❌ Test Failed");
        captureScreenshot(result);
        logger.error("❌ Test Failed: {}", testName);
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
                ExtentTestManager.getTest().addScreenCaptureFromPath(FileHelper.toWebPath(screenshotFile));
            } else {
                logger.warn("📸 Could not capture screenshot. Page is null.");
            }
        }
    }
}
