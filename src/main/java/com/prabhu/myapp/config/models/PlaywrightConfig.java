package com.prabhu.myapp.config.models;

public class PlaywrightConfig {
    private boolean recordVideo;
    private String trace;
    private PlaywrightViewport viewport;

    // Getters and Setters

    public boolean isRecordVideo() {
        return recordVideo;
    }

    public void setRecordVideo(boolean recordVideo) {
        this.recordVideo = recordVideo;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public PlaywrightViewport getViewport() {
        return viewport;
    }

    public void setViewport(PlaywrightViewport viewport) {
        this.viewport = viewport;
    }
}
