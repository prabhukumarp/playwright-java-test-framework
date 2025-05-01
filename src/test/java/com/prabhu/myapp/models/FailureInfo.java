// src/test/java/com/prabhu/myapp/models/FailureInfo.java
package com.prabhu.myapp.models;

public class FailureInfo {
    private final String testName;
    private final String errorMessage;
    private final String screenshotPath;

    public FailureInfo(String testName, String errorMessage, String screenshotPath) {
        this.testName = testName;
        this.errorMessage = errorMessage;
        this.screenshotPath = screenshotPath;
    }

    public String getTestName() { return testName; }
    public String getErrorMessage() { return errorMessage; }
    public String getScreenshotPath() { return screenshotPath; }
}
