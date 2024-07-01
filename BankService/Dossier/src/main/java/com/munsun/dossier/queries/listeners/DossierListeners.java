package com.munsun.dossier.queries.listeners;

import com.munsun.dossier.clients.DossierFeignClient;
import com.munsun.dossier.queries.payload.EmailMessageWithCreditDto;
import com.munsun.dossier.queries.payload.EmailMessageWithSesCode;
import com.munsun.dossier.services.MessageFactory;
import com.munsun.dossier.queries.payload.EmailMessage;
import com.munsun.dossier.services.DossierEmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DossierListeners {
    private final DossierEmailSender emailSender;
    private final MessageFactory messageFactory;
    private final DossierFeignClient client;

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}",
                   topics = "${kafka.topics.finish_registration}")
    public void handleFinishRegistration(EmailMessage emailMessage) {
        handle(emailMessage);
    }

    private void handle(EmailMessage emailMessage) {
        SimpleMailMessage message = messageFactory.getMessage(emailMessage);
        emailSender.send(message);
    }
    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}",
                topics = "${kafka.topics.create_documents}")
    public void handleCreateDocuments(EmailMessage emailMessage) {
        handle(emailMessage);
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}",
            topics = "${kafka.topics.send_documents}")
    public void handleSendDocuments(EmailMessageWithCreditDto emailMessage) {
        SimpleMailMessage message = messageFactory.getMessage(emailMessage);
        emailSender.send(message);
        client.updateStatus(emailMessage.statementId());
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}",
            topics = "${kafka.topics.send_ses}")
    public void handleSendSesCode(EmailMessageWithSesCode emailMessageWithSesCode) {
        SimpleMailMessage message = messageFactory.getMessage(emailMessageWithSesCode);
        emailSender.send(message);
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}",
            topics = "${kafka.topics.credit_issued}")
    public void handleCreditIssued(EmailMessage emailMessage) {
        handle(emailMessage);
    }
}
