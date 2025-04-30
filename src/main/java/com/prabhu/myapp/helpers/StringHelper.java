package com.prabhu.myapp.helpers;

public class StringHelper {
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static String sanitize(String input) {
        return input.replaceAll("[^a-zA-Z0-9]", "_");
    }
}
