package com.munsun.deal.queries.producers.impl;

import com.munsun.deal.dto.response.CreditDto;
import com.munsun.deal.queries.payload.EmailMessage;
import com.munsun.deal.queries.payload.EmailMessageWithCreditDto;
import com.munsun.deal.queries.payload.EmailMessageWithSesCode;
import com.munsun.deal.queries.payload.enums.Theme;
import com.munsun.deal.queries.producers.DealProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
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
    private String sendSesCode;
    @Value("${kafka.topics.credit_issued}")
    private String creditIssued;
    @Value("${kafka.topics.statement_denied}")
    private String statementDenied;

    @Override
    public void sendFinishRegistrationRequestNotification(String email, Theme theme, UUID statementId) {
        sendNotification(email, finishRegistration, theme, statementId);
    }

    private void sendNotification(String email, String topic, Theme theme, UUID statementId) {
        Message<EmailMessage> message = MessageBuilder
                .withPayload(new EmailMessage(email, theme, statementId))
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();
        kafkaTemplate.send(message);
    }

    @Override
    public void sendPrepareDocumentsNotification(String email, Theme theme, UUID statementId, CreditDto creditDto) {
        Message<EmailMessageWithCreditDto> message = MessageBuilder
                .withPayload(new EmailMessageWithCreditDto(email, theme, statementId, creditDto))
                    .setHeader(KafkaHeaders.TOPIC, sendDocuments)
                .build();
        kafkaTemplate.send(message);
    }

    @Override
    public void sendSignCodeDocumentsNotification(String email, Theme theme, UUID statementId, UUID sesCode) {
        Message<EmailMessageWithSesCode> message = MessageBuilder
                .withPayload(new EmailMessageWithSesCode(email, theme, statementId, sesCode))
                .setHeader(KafkaHeaders.TOPIC, sendSesCode)
                .build();
        kafkaTemplate.send(message);
    }

    @Override
    public void sendSuccessSignDocumentsNotification(String email, Theme theme, UUID statementId) {
        sendNotification(email, creditIssued, theme, statementId);
    }

    @Override
    public void sendScoringException(String email, Theme theme, UUID statementId) {
        //
    }

    @Override
    public void sendCreateDocumentsNotification(String email, Theme theme, UUID statementId) {
        sendNotification(email, createDocuments, theme, statementId);
    }
}