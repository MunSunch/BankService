package com.munsun.dossier.services.impl.providers.impl;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.munsun.dossier.kafka.payload.EmailMessageWithCreditDto;
import com.munsun.dossier.services.impl.providers.DocumentGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultDocumentGenerator implements DocumentGenerator {
    private final SpringTemplateEngine engine;
    @Override
    public DataSource generateDocument(EmailMessageWithCreditDto emailMessage) {
        Context context = new Context();
        context.setVariables(Map.of("message", emailMessage));
        String content = engine.process("credit-document", context);
        try(var outputStream = new ByteArrayOutputStream()) {
            writePdf(outputStream, content);
            DataSource dataSource = new ByteArrayDataSource(outputStream.toByteArray(), "application/pdf");
            return dataSource;
        } catch (IOException e) {
            log.error("Ошибка создания документа: {}", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Ошибка создания документа", e);
        }
    }

    private void writePdf(ByteArrayOutputStream outputStream, String content) {
        PdfWriter writer = new PdfWriter(outputStream);
        Document document = HtmlConverter.convertToDocument(content, writer);
        document.close();
    }
}
