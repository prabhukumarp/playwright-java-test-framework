package com.prabhu.myapp.utils.reports;

import com.prabhu.myapp.helpers.LoggerHelper;
import com.prabhu.myapp.utils.pdf.PdfUtils;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.testng.*;
import org.testng.xml.XmlSuite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PdfSummaryReportGenerator implements IReporter {

    private static final Logger logger = LoggerHelper.getLogger(PdfSummaryReportGenerator.class);
    private static final int PAGE_START_Y = 750;
    private static final int PAGE_MARGIN_X = 50;
    private static final int LINE_SPACING = 15;
    private static final int SECTION_SPACING = 25;
    private static final Path PDF_PATH = Paths.get("target", "test-output", "pdfs", "summary-report.pdf");

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        logger.info("Starting PDF summary report generation...");

        int totalPassed = 0;
        int totalFailed = 0;
        int totalSkipped = 0;
        int totalExecuted = 0;

        List<ISuite> suitesWithResults = suites.stream()
                .filter(suite -> !suite.getResults().isEmpty())
                .collect(Collectors.toList());

        try (PDDocument document = PdfUtils.createDocument()) {
            PDPage page = PdfUtils.addPage(document);
            int y = PAGE_START_Y;

            PdfUtils.addText(document, page, "Test Execution Summary", PAGE_MARGIN_X, y, 18);
            y -= LINE_SPACING + 5;
            PdfUtils.addText(document, page, "Generated on: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), PAGE_MARGIN_X, y, 12);
            y -= SECTION_SPACING;

            for (ISuite suite : suites) {
                if (suite.getResults().isEmpty()) {
                    logger.warn("Skipping suite with no results: {}", suite.getName());
                    continue;
                }

                PdfUtils.addText(document, page, "Suite: " + suite.getName(), PAGE_MARGIN_X, y, 14);
                y -= LINE_SPACING;

                int suitePassed = 0;
                int suiteFailed = 0;
                int suiteSkipped = 0;

                for (ISuiteResult result : suite.getResults().values()) {
                    ITestContext context = result.getTestContext();

                    int passed = context.getPassedTests().size();
                    int failed = context.getFailedTests().size();
                    int skipped = context.getSkippedTests().size();

                    suitePassed += passed;
                    suiteFailed += failed;
                    suiteSkipped += skipped;

                    logger.info("Suite: {} | Passed: {} | Failed: {} | Skipped: {}", suite.getName(), passed, failed, skipped);

                    long startMillis = context.getStartDate().getTime();
                    long endMillis = context.getEndDate().getTime();
                    String duration = formatDuration(endMillis - startMillis);

                    String startTime = new SimpleDateFormat("HH:mm:ss").format(context.getStartDate());
                    String endTime = new SimpleDateFormat("HH:mm:ss").format(context.getEndDate());

                    PdfUtils.addText(document, page, "Duration: " + duration, PAGE_MARGIN_X + 10, y, 12);
                    y -= LINE_SPACING;
                    PdfUtils.addText(document, page, "Start: " + startTime + " | End: " + endTime, PAGE_MARGIN_X + 10, y, 12);
                    y -= LINE_SPACING;
                }

                int suiteTotal = suitePassed + suiteFailed + suiteSkipped;
                double suitePassPercentage = suiteTotal > 0 ? ((double) suitePassed / suiteTotal) * 100 : 0;

                PdfUtils.addText(document, page, "Passed: " + suitePassed, PAGE_MARGIN_X + 10, y, 12);
                y -= LINE_SPACING;
                PdfUtils.addText(document, page, "Failed: " + suiteFailed, PAGE_MARGIN_X + 10, y, 12);
                y -= LINE_SPACING;
                PdfUtils.addText(document, page, "Skipped: " + suiteSkipped, PAGE_MARGIN_X + 10, y, 12);
                y -= LINE_SPACING;
                PdfUtils.addText(document, page, String.format("Pass Percentage: %.2f%%", suitePassPercentage), PAGE_MARGIN_X + 10, y, 12);
                y -= SECTION_SPACING;

                totalPassed += suitePassed;
                totalFailed += suiteFailed;
                totalSkipped += suiteSkipped;

                logger.info("Suite cumulative: | Passed: {} | Failed: {} | Skipped: {}", totalPassed, totalFailed, totalSkipped);

                if (y < 150) {
                    page = PdfUtils.addPage(document);
                    y = PAGE_START_Y;
                }
            }

            totalExecuted = totalPassed + totalFailed + totalSkipped;
            double totalPassPercentage = totalExecuted > 0 ? ((double) totalPassed / totalExecuted) * 100 : 0;
            logger.info("Total execution: | Passed: {} | Failed: {} | Skipped: {} | Pass %%: {}", totalPassed, totalFailed, totalSkipped, totalPassPercentage);

            // ➕ Add consolidated summary if multiple real suites executed -- removed this as we are generating overall summary in pie chart
            /*if (suitesWithResults.size() > 1) {
                PdfUtils.addText(document, page, "==========================================", PAGE_MARGIN_X, y, 12);
                y -= LINE_SPACING;
                PdfUtils.addText(document, page, "Consolidated Summary (All Suites)", PAGE_MARGIN_X, y, 14);
                y -= LINE_SPACING;

                PdfUtils.addText(document, page, "Total Passed: " + totalPassed, PAGE_MARGIN_X + 10, y, 12);
                y -= LINE_SPACING;
                PdfUtils.addText(document, page, "Total Failed: " + totalFailed, PAGE_MARGIN_X + 10, y, 12);
                y -= LINE_SPACING;
                PdfUtils.addText(document, page, "Total Skipped: " + totalSkipped, PAGE_MARGIN_X + 10, y, 12);
                y -= LINE_SPACING;

                totalExecuted = totalPassed + totalFailed + totalSkipped;
                String passPercentage = calculatePassPercentage(totalPassed, totalExecuted);
                PdfUtils.addText(document, page, "Pass Percentage: " + passPercentage + "%", PAGE_MARGIN_X + 10, y, 12);
                y -= SECTION_SPACING;
            }*/


            // ✅ Add pie chart to new page
            PDPage chartPage = PdfUtils.addPage(document);
            BufferedImage chartImage = generatePieChartImage(totalPassed, totalFailed, totalSkipped);
            byte[] imageBytes = bufferedImageToByteArray(chartImage);
            PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, imageBytes, "summary-chart");

            PdfUtils.addText(document, chartPage, "Overall Summary", PAGE_MARGIN_X, 720, 16);
            PdfUtils.addText(document, chartPage, "Total Passed: " + totalPassed, PAGE_MARGIN_X, 690, 12);
            PdfUtils.addText(document, chartPage, "Total Failed: " + totalFailed, PAGE_MARGIN_X, 675, 12);
            PdfUtils.addText(document, chartPage, "Total Skipped: " + totalSkipped, PAGE_MARGIN_X, 660, 12);
            PdfUtils.addText(document, chartPage, String.format("Overall Pass Percentage: %.2f%%", totalPassPercentage), PAGE_MARGIN_X, 645, 12);

            // ✅ Correct image placement
            int chartWidth = 300;
            int chartHeight = 250;
            float pageWidth = chartPage.getMediaBox().getWidth();
            float centerX = (pageWidth - chartWidth) / 2f;
            int imageY = 360; // Bottom-left Y for image

            try (PDPageContentStream contentStream = PdfUtils.createContentStream(document, chartPage)) {
                contentStream.drawImage(pdImage, centerX, imageY, chartWidth, chartHeight);
            }

            PdfUtils.saveDocument(document, PDF_PATH);
            logger.info("PDF summary report generated at: {}", PDF_PATH);

        } catch (IOException e) {
            logger.error("Failed to generate PDF summary report", e);
        }
    }



    private BufferedImage generatePieChartImage(int passed, int failed, int skipped) {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        dataset.setValue("Passed", passed);
        dataset.setValue("Failed", failed);
        dataset.setValue("Skipped", skipped);

        JFreeChart chart = ChartFactory.createPieChart("Test Result Distribution", dataset, true, true, false);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("Passed", new Color(76, 175, 80));   // Green
        plot.setSectionPaint("Failed", new Color(244, 67, 54));   // Red
        plot.setSectionPaint("Skipped", new Color(255, 193, 7));  // Yellow

        return chart.createBufferedImage(500, 400);
    }

    private byte[] bufferedImageToByteArray(BufferedImage image) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "PNG", baos);
            return baos.toByteArray();
        }
    }

    private String formatDuration(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        return String.format("%02dh:%02dm:%02ds", hours, minutes % 60, seconds % 60);
    }

    private String calculatePassPercentage(int passed, int total) {
        if (total == 0) return "0.00";
        double percentage = (double) passed * 100 / total;
        return String.format("%.2f", percentage);
    }

}
