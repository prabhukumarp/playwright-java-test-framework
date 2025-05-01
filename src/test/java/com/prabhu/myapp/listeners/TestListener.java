package com.prabhu.myapp.listeners;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.microsoft.playwright.Page;
import com.prabhu.myapp.base.BaseTest;
import com.prabhu.myapp.config.models.FrameworkConfig;
import com.prabhu.myapp.di.FrameworkModule;
import com.prabhu.myapp.helpers.FileHelper;
import com.prabhu.myapp.helpers.LoggerHelper;
import com.prabhu.myapp.models.FailureInfo;
import com.prabhu.myapp.utils.reports.ExtentTestManager;
import com.prabhu.myapp.utils.reports.PdfFailureReportGenerator;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Base64;
import java.util.List;

public class TestListener implements ITestListener {

    private static final Logger logger = LoggerHelper.getLogger(TestListener.class);

    private static final Injector injector = Guice.createInjector(new FrameworkModule());
    private static final FrameworkConfig frameworkConfig = injector.getInstance(FrameworkConfig.class);

    private static final String screenshotPath = frameworkConfig.getReports().getScreenshotsPath();

    private final List<FailureInfo> failureInfoList = new ArrayList<>();

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

        if (!(currentInstance instanceof BaseTest baseTest)) return;

        Page page = baseTest.getPage();
        if (page == null) {
            ExtentTestManager.getTest().fail("❌ Test Failed. Page object was null, screenshot not captured.");
            logger.error("Unable to capture screenshot as page is null");
            return;
        }

        String testName = result.getMethod().getMethodName();
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = testName + "_" + timestamp + ".png";
        String screenshotFile = Paths.get(screenshotPath, fileName).toString();
        Path screenshotPathObj = Paths.get(screenshotFile);

        try {
            FileHelper.ensureDirectoryExists(screenshotPath);
            page.screenshot(new Page.ScreenshotOptions().setPath(screenshotPathObj));

            // ✅ Base64 for ExtentReport
            byte[] screenshotBytes = Files.readAllBytes(screenshotPathObj);
            String base64Screenshot = Base64.getEncoder().encodeToString(screenshotBytes);
            ExtentTestManager.getTest().fail("❌ Test Failed. Screenshot below:")
                    .addScreenCaptureFromBase64String(base64Screenshot, testName + "_screenshot");

            // ✅ Add to PDF Failure list
            String errorMessage = result.getThrowable() != null ?
                    result.getThrowable().getMessage() : "No exception message";
            failureInfoList.add(new FailureInfo(testName, errorMessage, screenshotFile));

            logger.error("❌ Test Failed: {}", testName);
            logger.info("✅ Screenshot saved at {}", screenshotFile);
        } catch (Exception e) {
            logger.error("📸 Failed to capture screenshot or generate failure info", e);
        }
    }





    @Override
    public void onFinish(ITestContext context) {

        ExtentTestManager.endTest();
        if (frameworkConfig.getReports().isCaptureFailuresInPdf() && !failureInfoList.isEmpty()) {
            PdfFailureReportGenerator.generate(failureInfoList, frameworkConfig.getReports().getPdfPath());
        }
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

    private void captureScreenshotAndTrackFailure(ITestResult result) {
        Object testInstance = result.getInstance();
        if (!(testInstance instanceof BaseTest baseTest)) return;

        Page page = baseTest.getPage();
        if (page == null) {
            logger.warn("📸 Could not capture screenshot. Page is null.");
            return;
        }

        String testName = result.getMethod().getMethodName();
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = testName + "_" + timestamp + ".png";
        String screenshotFile = Paths.get(screenshotPath, fileName).toString();

        FileHelper.ensureDirectoryExists(screenshotPath);
        Path screenshotPathObj = Paths.get(screenshotFile);
        try {
            page.screenshot(new Page.ScreenshotOptions().setPath(screenshotPathObj));
            logger.info("✅ Screenshot saved at {}", screenshotFile);

            // ✅ Wait for file to exist before proceeding (max 3 seconds)
            int retryCount = 0;
            while (!Files.exists(screenshotPathObj) && retryCount < 10) {
                Thread.sleep(500); // wait 300ms
                retryCount++;
            }

            if (Files.exists(screenshotPathObj)) {
                // Add to failure list
                failureInfoList.add(new FailureInfo(
                        testName,
                        result.getThrowable().getMessage(),
                        screenshotFile
                ));
            } else {
                logger.warn("❌ Screenshot was not saved in time for test {}", testName);
            }

        } catch (Exception e) {
            logger.error("📸 Failed to capture screenshot", e);
        }
    }


    private String getScreenshotFilePath(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return Paths.get(screenshotPath, testName + "_" + timestamp + ".png").toString();
    }

}
