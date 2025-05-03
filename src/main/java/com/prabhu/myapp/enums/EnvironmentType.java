package com.prabhu.myapp.enums;

public enum EnvironmentType {
    DEV,
    QA,
    UAT,
    PROD,
    STAGING;

    public static EnvironmentType fromString(String value) {
        try {
            return EnvironmentType.valueOf(value.trim().toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid EnvironmentType: " + value);
        }
    }
}
