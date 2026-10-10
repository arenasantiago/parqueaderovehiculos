package com.example.parqueaderomotoycarro.Service;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Service
public class ServicePdf {

    // Convierte un HTML (XHTML bien formado) en un PDF en memoria
    public byte[] generarPdf(String html) {
        return generarPdf(html, null);
    }

    // baseUrl permite resolver recursos relativos del HTML (por ejemplo imagenes)
    public byte[] generarPdf(String html, String baseUrl) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            if (baseUrl == null || baseUrl.isEmpty()) {
                renderer.setDocumentFromString(html);
            } else {
                renderer.setDocumentFromString(html, baseUrl);
            }
            renderer.layout();
            renderer.createPDF(baos);
            renderer.finishPDF();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el PDF", e);
        }
    }
}
