package com.prabhu.myapp.utils.reports;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.prabhu.myapp.helpers.FileHelper;
import com.prabhu.myapp.helpers.LoggerHelper;
import com.prabhu.myapp.models.FailureInfo;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PdfFailureReportGenerator {

    private static final Logger logger = LoggerHelper.getLogger(PdfFailureReportGenerator.class);

    public static void generate(List<FailureInfo> failureInfos, String outputPath) {
        try {
            FileHelper.ensureDirectoryExists(outputPath);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String outputFile = Paths.get(outputPath, "failure-report_" + timestamp + ".pdf").toString();

            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, new FileOutputStream(outputFile));
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.RED);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
            Font errorFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 12, BaseColor.DARK_GRAY);

            document.add(new Paragraph("❌ Failure Report", titleFont));
            document.add(new Paragraph("Generated on: " + new Date(), normalFont));
            document.add(Chunk.NEWLINE);
            document.add(new LineSeparator());
            document.add(Chunk.NEWLINE);

            for (FailureInfo info : failureInfos) {
                document.add(new Paragraph("Test Case: " + info.getTestName(), normalFont));
                document.add(new Paragraph("Error Message: " + info.getErrorMessage(), errorFont));
                document.add(Chunk.NEWLINE);

                String screenshotPath = info.getScreenshotPath();
                if (screenshotPath != null && !screenshotPath.trim().isEmpty()) {
                    File screenshotFile = new File(screenshotPath);
                    if (screenshotFile.exists()) {
                        try {
                            Image img = Image.getInstance(screenshotPath);
                            float maxWidth = 400f;
                            float maxHeight = 300f;

                            float widthScale = maxWidth / img.getWidth();
                            float heightScale = maxHeight / img.getHeight();
                            float scale = Math.min(widthScale, heightScale);

                            img.scaleAbsolute(img.getWidth() * scale, img.getHeight() * scale);
                            img.setAlignment(Image.MIDDLE);

                            document.add(img);
                            logger.info("🖼️ Screenshot added for test: {}", info.getTestName());
                        } catch (Exception e) {
                            document.add(new Paragraph("⚠️ Failed to load screenshot: " + e.getMessage(), errorFont));
                            logger.warn("⚠️ Error loading image for test '{}': {}", info.getTestName(), e.getMessage());
                        }
                    } else {
                        document.add(new Paragraph("⚠️ Screenshot not found at path: " + screenshotPath, errorFont));
                        logger.warn("⚠️ Screenshot not found for test '{}': {}", info.getTestName(), screenshotPath);
                    }
                } else {
                    document.add(new Paragraph("⚠️ No screenshot path provided.", errorFont));
                }

                document.add(Chunk.NEWLINE);
                document.add(new LineSeparator());
                document.add(Chunk.NEWLINE);
            }

            document.close();
            logger.info("📄 PDF Failure Report generated successfully: {}", outputFile);
        } catch (Exception e) {
            logger.error("❌ Failed to generate PDF report", e);
        }
    }
}
