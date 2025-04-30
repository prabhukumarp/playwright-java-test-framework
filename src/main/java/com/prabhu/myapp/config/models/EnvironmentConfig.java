package com.prabhu.myapp.config.models;

public class EnvironmentConfig {
    private CredentialConfig credentials;
    private DatabaseConfig database;
    private ApiConfig api;
    private PlaywrightConfig playwright;

    // Getters and Setters

    public CredentialConfig getCredentials() {
        return credentials;
    }

    public void setCredentials(CredentialConfig credentials) {
        this.credentials = credentials;
    }

    public DatabaseConfig getDatabase() {
        return database;
    }

    public void setDatabase(DatabaseConfig database) {
        this.database = database;
    }

    public ApiConfig getApi() {
        return api;
    }

    public void setApi(ApiConfig api) {
        this.api = api;
    }

    public PlaywrightConfig getPlaywright() {
        return playwright;
    }

    public void setPlaywright(PlaywrightConfig playwright) {
        this.playwright = playwright;
    }
}
