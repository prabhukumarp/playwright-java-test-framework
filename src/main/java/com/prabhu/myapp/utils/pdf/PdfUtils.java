package com.prabhu.myapp.utils.pdf;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PdfUtils {

    public static PDDocument createDocument() {
        return new PDDocument();
    }

    public static PDPage addPage(PDDocument document) {
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        return page;
    }

    public static void addText(PDDocument document, PDPage page, String text, float x, float y, int fontSize) throws IOException {
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD_OBLIQUE), fontSize);
            contentStream.beginText();
            contentStream.newLineAtOffset(x, y);
            contentStream.showText(text);
            contentStream.endText();
        }
    }

    public static void saveDocument(PDDocument document, Path path) throws IOException {
        Files.createDirectories(path.getParent());
        document.save(path.toFile());
        document.close();
    }

    public static PDPageContentStream createContentStream(PDDocument document, PDPage page) throws IOException {
        return new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);
    }
}
