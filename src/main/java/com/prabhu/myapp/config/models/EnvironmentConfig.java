package com.prabhu.myapp.config.models;

public class EnvironmentConfig {
    private ApplicationConfig applicationConfig;
    private AppCredentials appCredentials;
    private DatabaseConfig database;
    private ApiConfig apiConfig;
    private PlaywrightConfig playwright;
    private ReportConfig reports;
    private NotificationConfig notifications;


    // Getters and setters
    public ApplicationConfig getApplicationConfig() {
        return applicationConfig;
    }

    public void setApplicationConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    public AppCredentials getAppCredentials() {
        return appCredentials;
    }

    public void setAppCredentials(AppCredentials appCredentials) {
        this.appCredentials = appCredentials;
    }

    public DatabaseConfig getDatabase() {
        return database;
    }

    public void setDatabase(DatabaseConfig database) {
        this.database = database;
    }

    public ApiConfig getApiConfig() {
        return apiConfig;
    }

    public void setApiConfig(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
    }

    public PlaywrightConfig getPlaywright() {
        return playwright;
    }

    public void setPlaywright(PlaywrightConfig playwright) {
        this.playwright = playwright;
    }

    public NotificationConfig getNotifications() {
        return notifications;
    }

    public void setNotifications(NotificationConfig notifications) {
        this.notifications = notifications;
    }

    public ReportConfig getReports() {
        return reports;
    }

    public void setReports(ReportConfig reports) {
        this.reports = reports;
    }
}
