package com.prabhu.myapp.enums;

public enum SupportedBrowser {
    CHROME,
    CHROMIUM,
    FIREFOX,
    WEBKIT,
    MSEDGE;

    public static SupportedBrowser fromString(String name) {
        try {
            return SupportedBrowser.valueOf(name.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unsupported browser type: " + name);
        }
    }
}
