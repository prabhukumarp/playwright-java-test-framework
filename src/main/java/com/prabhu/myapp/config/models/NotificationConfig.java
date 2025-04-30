package com.prabhu.myapp.config.models;

public class NotificationConfig {
    private EmailConfig email;
    private TeamsConfig teams;

    // Getters and Setters

    public EmailConfig getEmail() {
        return email;
    }

    public void setEmail(EmailConfig email) {
        this.email = email;
    }

    public TeamsConfig getTeams() {
        return teams;
    }

    public void setTeams(TeamsConfig teams) {
        this.teams = teams;
    }
}

