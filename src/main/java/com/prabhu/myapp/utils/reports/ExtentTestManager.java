package com.prabhu.myapp.utils.reports;

import com.aventstack.extentreports.ExtentTest;

import java.util.concurrent.ConcurrentHashMap;

public class ExtentTestManager {
    private static final ConcurrentHashMap<Long, ExtentTest> testMap = new ConcurrentHashMap<>();

    public static synchronized ExtentTest getTest() {
        return testMap.get(Thread.currentThread().getId());
    }

    public static synchronized void startTest(String testName, String description) {
        ExtentTest test = ExtentManager.getExtent().createTest(testName, description);
        testMap.put(Thread.currentThread().getId(), test);
    }

    public static synchronized void endTest() {
        ExtentManager.getExtent().flush();
    }
}
