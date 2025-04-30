package com.prabhu.myapp.config.models;

public class FrameworkConfig {
    private String defaultEnv;
    private ReportConfig report;
    private ApplicationConfig app;
    private NotificationsConfig notifications;
    private DatabaseConfig database;
    private PlaywrightConfig playwright;

    // Getters and Setters

    public String getDefaultEnv() {
        return defaultEnv;
    }

    public void setDefaultEnv(String defaultEnv) {
        this.defaultEnv = defaultEnv;
    }

    public ReportConfig getReport() {
        return report;
    }

    public void setReport(ReportConfig report) {
        this.report = report;
    }

    public ApplicationConfig getApp() {
        return app;
    }

    public void setApp(ApplicationConfig app) {
        this.app = app;
    }

    public NotificationsConfig getNotifications() {
        return notifications;
    }

    public void setNotifications(NotificationsConfig notifications) {
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
