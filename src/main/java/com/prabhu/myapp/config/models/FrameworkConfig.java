package com.prabhu.myapp.config.models;

public class FrameworkConfig {
    private ReportConfig reports;
    private ApplicationConfig applicationConfig;
    private NotificationConfig notifications;
    private DatabaseConfig database;
    private PlaywrightConfig playwright;

    // Getters and Setters

    public ReportConfig getReports() {
        return reports;
    }

    public void setReports(ReportConfig reports) {
        this.reports = reports;
    }

    public ApplicationConfig getApplicationConfig() {
        return applicationConfig;
    }

    public void setApplicationConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    public NotificationConfig getNotifications() {
        return notifications;
    }

    public void setNotifications(NotificationConfig notifications) {
        this.notifications = notifications;
    }

    public DatabaseConfig getDatabase() {
        return database;
    }

    public void setDatabase(DatabaseConfig database) {
        this.database = database;
    }

    public PlaywrightConfig getPlaywright() {
        return playwright;
    }

    public void setPlaywright(PlaywrightConfig playwright) {
        this.playwright = playwright;
    }
}

