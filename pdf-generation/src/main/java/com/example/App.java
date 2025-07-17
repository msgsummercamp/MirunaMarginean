package com.example;

import com.example.AppConfig;
import com.example.PdfGenerationService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        PdfGenerationService pdfService = context.getBean(PdfGenerationService.class);
        pdfService.generatePdfToFile("D:/Facultate/Practica/MirunaMarginean/pdf-generation/src/main/resources/pdfs/generated.pdf");
        pdfService.addTextToExistingPdf(
                "pdfs/generated.pdf",
                "D:/Facultate/Practica/MirunaMarginean/pdf-generation/src/main/resources/pdfs/modified.pdf",
                "Hello from iText!",
                100,
                700
        );
        context.close();
    }
}
