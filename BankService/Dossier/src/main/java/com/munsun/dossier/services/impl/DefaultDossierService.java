package com.munsun.dossier.services.impl;

import com.munsun.dossier.queries.payload.EmailMessage;
import com.munsun.dossier.queries.payload.EmailMessageWithCreditDto;
import com.munsun.dossier.queries.payload.EmailMessageWithSesCode;
import com.munsun.dossier.services.DossierService;
import com.munsun.dossier.services.impl.clients.DossierFeignClient;
import com.munsun.dossier.services.impl.providers.DocumentGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class DefaultDossierService implements DossierService {
    private final JavaMailSender sender;
    private final DossierFeignClient client;
    private final DocumentGenerator documentGenerator;

    @Override
    public void sendMessageEmail(EmailMessage emailMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailMessage.address());
            message.setSubject("Кредитные уведомления");
        String text = "";
        switch (emailMessage.theme()) {
            case FINISH_REGISTRATION -> {
                text = "Завершите регистрацию для получения кредита";
            }
            case PREPARE_DOCUMENTS -> {
                text = "Документы формируются, ожидайте";
            }
            case CC_DENIED -> {
                text = "В кредите отказано, заполните заявку позже";
            }
            case CC_APPROVED -> {
                text = "Кредит одобрен";
            }
            case CREATED_DOCUMENTS -> {
                text = "Можете запросить документы для ознакомления с кредитным предложением";
            }
            case SIGN_DOCUMENTS -> {
                text = "Поздравляем! Документы подписаны, можете пользоваться кредитом";
            }
        }
        message.setText(text);
        sender.send(message);
    }

    @Override
    public void sendMessageEmail(EmailMessageWithCreditDto emailMessage) throws MessagingException {
        DataSource dataSource = documentGenerator.generateDocument(emailMessage);
        MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(emailMessage.address());
            helper.setSubject("Кредитные уведомления");
            helper.addAttachment("documents.pdf", dataSource);
            helper.setText("Ваши документы");
        sender.send(message);
    }

    @Override
    public void sendMessageEmail(EmailMessageWithSesCode emailMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailMessage.address());
            message.setSubject("Кредитные уведомления");
            message.setText(String.format("Код подтверждения операции: %s", emailMessage.sesCodeConfirm().toString()));
        sender.send(message);
    }
}
