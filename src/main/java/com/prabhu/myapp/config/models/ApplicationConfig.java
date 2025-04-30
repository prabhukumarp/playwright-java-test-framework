package com.prabhu.myapp.config.models;

public class ApplicationConfig {
    private String name;
    private String baseUrl;
    private String browser;
    private boolean browserHeadless;
    private int defaultTimeout;
    private int retryCount;
    private String logLevel;
    private String reportPath;
    private boolean takeScreenshots;
    private String environment;

    // Getters and Setters

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public boolean isTakeScreenshots() {
        return takeScreenshots;
    }

    public void setTakeScreenshots(boolean takeScreenshots) {
        this.takeScreenshots = takeScreenshots;
    }

    public String getReportPath() {
        return reportPath;
    }

    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int getDefaultTimeout() {
        return defaultTimeout;
    }

    public void setDefaultTimeout(int defaultTimeout) {
        this.defaultTimeout = defaultTimeout;
    }

    public boolean isBrowserHeadless() {
        return browserHeadless;
    }

    public void setBrowserHeadless(boolean browserHeadless) {
        this.browserHeadless = browserHeadless;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
