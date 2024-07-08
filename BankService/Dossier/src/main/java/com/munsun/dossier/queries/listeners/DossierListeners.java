package com.munsun.dossier.queries.listeners;

import com.munsun.dossier.queries.payload.EmailMessageWithCreditDto;
import com.munsun.dossier.queries.payload.EmailMessageWithSesCode;
import com.munsun.dossier.queries.payload.EmailMessage;

import com.munsun.dossier.services.DossierService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@RequiredArgsConstructor
public class DossierListeners {
    private final DossierService service;

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}",
                   topics = "${kafka.topics.finish_registration}")
    public void handleFinishRegistration(EmailMessage emailMessage) {
        service.sendMessageEmail(emailMessage);
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${kafka.topics.create_documents}")
    public void handleCreateDocuments(EmailMessage emailMessage) {
        service.sendMessageEmail(emailMessage);
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${kafka.topics.send_documents}")
    public void handleSendDocuments(EmailMessageWithCreditDto emailMessage) throws MessagingException {
        service.sendMessageEmail(emailMessage);
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}",
                   topics = "${kafka.topics.send_ses}")
    public void handleSendSesCode(EmailMessageWithSesCode emailMessageWithSesCode) {
        service.sendMessageEmail(emailMessageWithSesCode);
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}",
                   topics = "${kafka.topics.credit_issued}")
    public void handleCreditIssued(EmailMessage emailMessage) {
        service.sendMessageEmail(emailMessage);
    }
}
