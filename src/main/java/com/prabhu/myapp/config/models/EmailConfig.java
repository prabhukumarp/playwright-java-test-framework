package com.prabhu.myapp.config.models;

import java.util.List;

public class EmailConfig {
    private boolean enabled;
    private List<String> recipients;
    private SmtpConfig smtp;

    // Getters and Setters

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    public SmtpConfig getSmtp() {
        return smtp;
    }

    public void setSmtp(SmtpConfig smtp) {
        this.smtp = smtp;
    }
}
