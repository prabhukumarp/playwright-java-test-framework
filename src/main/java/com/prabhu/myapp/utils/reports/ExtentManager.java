package com.prabhu.myapp.utils.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;

    public static synchronized ExtentReports getExtent() {
        if (extent == null) {
            extent = new ExtentReports();
            ExtentSparkReporter spark = new ExtentSparkReporter("target/test-output/extent/extent-report.html");
            spark.config().setReportName("Playwright Java Test Report");
            spark.config().setDocumentTitle("Test Execution Report");
            extent.attachReporter(spark);
        }
        return extent;
    }
}
