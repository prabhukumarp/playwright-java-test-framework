package com.prabhu.myapp.utils.reports;

import com.prabhu.myapp.utils.pdf.PdfUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PdfSummaryReportGenerator implements IReporter {

    @Override
    public void generateReport(java.util.List<XmlSuite> xmlSuites, java.util.List<ISuite> suites, String outputDirectory) {
        Path pdfPath = Paths.get("target/test-output/pdfs/summary-report.pdf");

        try {
            PDDocument document = PdfUtils.createDocument();
            PDPage page = PdfUtils.addPage(document);

            int y = 750;
            PdfUtils.addText(document, page, "Test Execution Summary", 50, y, 18);
            y -= 30;

            for (ISuite suite : suites) {
                for (ISuiteResult suiteResult : suite.getResults().values()) {
                    ITestContext context = suiteResult.getTestContext();

                    PdfUtils.addText(document, page, "Suite: " + suite.getName(), 50, y, 14);
                    y -= 20;
                    PdfUtils.addText(document, page, "Passed: " + context.getPassedTests().size(), 60, y, 12);
                    y -= 15;
                    PdfUtils.addText(document, page, "Failed: " + context.getFailedTests().size(), 60, y, 12);
                    y -= 15;
                    PdfUtils.addText(document, page, "Skipped: " + context.getSkippedTests().size(), 60, y, 12);
                    y -= 25;

                    if (y < 100) {
                        page = PdfUtils.addPage(document);
                        y = 750;
                    }
                }
            }

            PdfUtils.saveDocument(document, pdfPath);
            System.out.println("✅ PDF summary report generated at: " + pdfPath);

        } catch (IOException e) {
            System.err.println("❌ Failed to generate PDF summary report: " + e.getMessage());
        }
    }
}
