package com.munsun.dossier.services.impl.providers.impl;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.munsun.dossier.queries.payload.EmailMessageWithCreditDto;
import com.munsun.dossier.services.impl.providers.DocumentGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DefaultDocumentGenerator implements DocumentGenerator {
    private final SpringTemplateEngine engine;
    @Override
    public DataSource generateDocument(EmailMessageWithCreditDto emailMessage) {
        Context context = new Context();
        context.setVariables(Map.of("message", emailMessage));
        String content = engine.process("credit-document", context);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        writePdf(outputStream, content);
        DataSource dataSource = new ByteArrayDataSource(outputStream.toByteArray(), "application/pdf");
        return dataSource;
    }

    private void writePdf(ByteArrayOutputStream outputStream, String content) {
        PdfWriter writer = new PdfWriter(outputStream);
        Document document = HtmlConverter.convertToDocument(content, writer);
        document.close();
    }
}
