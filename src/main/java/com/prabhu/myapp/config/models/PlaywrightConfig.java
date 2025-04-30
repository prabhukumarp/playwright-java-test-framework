package com.prabhu.myapp.config.models;

public class PlaywrightConfig {
    private boolean takeScreenshots;
    private boolean recordVideo;
    private String trace;
    private Viewport viewport;

    public boolean isTakeScreenshots() {
        return takeScreenshots;
    }

    public void setTakeScreenshots(boolean takeScreenshots) {
        this.takeScreenshots = takeScreenshots;
    }

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

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }
}
