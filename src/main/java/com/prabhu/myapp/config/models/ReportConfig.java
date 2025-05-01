package com.prabhu.myapp.config.models;

public class ReportConfig {
    private  boolean captureOnSuccess;
    private boolean captureFailuresInPdf;
    private String title;
    private String reportPath;
    private String theme;
    private String screenshotsPath;
    private String pdfPath;
    private  String logsPath;
    private boolean captureLogsInFile;
    private boolean isArchived;

    // Getters and Setters

    public boolean isCaptureOnSuccess() {
        return captureOnSuccess;
    }

    public void setCaptureOnSuccess(boolean captureOnSuccess) {
        this.captureOnSuccess = captureOnSuccess;
    }

    public boolean isCaptureFailuresInPdf() {
        return captureFailuresInPdf;
    }

    public void setCaptureFailuresInPdf(boolean captureFailuresInPdf) {
        this.captureFailuresInPdf = captureFailuresInPdf;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReportPath() {
        return reportPath;
    }

    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getScreenshotsPath() {
        return screenshotsPath;
    }

    public void setScreenshotsPath(String screenshotsPath) {
        this.screenshotsPath = screenshotsPath;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public String getLogsPath() {
        return logsPath;
    }

    public void setLogsPath(String logsPath) {
        this.logsPath = logsPath;
    }

    public boolean isCaptureLogsInFile() {
        return captureLogsInFile;
    }

    public void setCaptureLogsInFile(boolean captureLogsInFile) {
        this.captureLogsInFile = captureLogsInFile;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }
}