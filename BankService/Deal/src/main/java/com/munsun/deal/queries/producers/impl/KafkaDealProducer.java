package com.munsun.deal.queries.producers.impl;

import com.munsun.deal.dto.payload.EmailMessage;
import com.munsun.deal.dto.payload.enums.Theme;
import com.munsun.deal.queries.producers.DealProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KafkaDealProducer implements DealProducer {
    private final KafkaTemplate<String, EmailMessage> kafkaTemplate;
    @Value("${kafka.topics.finish_registration}")
    private String finishRegistration;
    @Value("${kafka.topics.create_documents}")
    private String createDocuments;
    @Value("${kafka.topics.send_documents}")
    private String sendDocuments;
    @Value("${kafka.topics.send_ses}")
    private String sendSes;
    @Value("${kafka.topics.credit_issued}")
    private String creditIssued;
    @Value("${kafka.topics.statement_denied}")
    private String statementDenied;

    @Override
    public void sendFinishRegistrationRequestNotification(String email, Theme theme, UUID statementId) {
        kafkaTemplate.send(finishRegistration, new EmailMessage(email, theme, statementId, "Finish registration"));
    }

    @Override
    public void sendPrepareDocumentsNotification(String email, Theme theme, UUID statementId) {
        kafkaTemplate.send(createDocuments, new EmailMessage(email, theme, statementId, "Create document"));
    }

    @Override
    public void sendSignCodeDocumentsNotification(String email, Theme theme, UUID statementId, String sesCode) {
        kafkaTemplate.send(sendSes, new EmailMessage(email, theme, statementId, sesCode));
    }

    @Override
    public void sendSuccessSignDocumentsNotification(String email, Theme theme, UUID statementId, String creditIssued) {

    }
}
