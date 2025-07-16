package com.example;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.BaseFont;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.net.URL;

@Component
public class PdfGenerationService {

    @Value("${pdf.text}")
    private String pdfText;

    @Value("${pdf.image}")
    private String imageUrl;

    @Value("${pdf.image.maxWidth}")
    private float maxWidth;

    @Value("${pdf.image.maxHeight}")
    private float maxHeight;

    public void generatePdfToFile(String filePath) {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            Document document = new Document();
            PdfWriter.getInstance(document, fos);
            document.open();

            document.add(new Paragraph(pdfText));

            Image image = Image.getInstance(new URL(imageUrl));
            image.scaleToFit(maxWidth, maxHeight);
            document.add(image);

            document.close();
            System.out.println("PDF generated at: " + filePath);
        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed", e);
        }
    }

    public void addTextToExistingPdf(String inputPdfPath, String outputPdfPath, String text, float x, float y) {
        try (
                InputStream is = getClass().getClassLoader().getResourceAsStream(inputPdfPath);
                FileOutputStream fos = new FileOutputStream(outputPdfPath)
        ) {
            PdfReader reader = new PdfReader(is);
            PdfStamper stamper = new PdfStamper(reader, fos);
            PdfContentByte over = stamper.getOverContent(1);
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
            over.beginText();
            over.setFontAndSize(bf, 14);
            over.setTextMatrix(x, y);
            over.showText(text);
            over.endText();
            stamper.close();
            reader.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to add text to PDF", e);
        }
    }
}
