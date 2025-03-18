package com.munsun.deal.integrations.amqp.kafka.producers.impl;

import com.munsun.deal.dto.CreditDto;
import com.munsun.deal.integrations.amqp.kafka.configurations.KafkaTopics;
import com.munsun.deal.integrations.amqp.kafka.payload.AuditActionPayload;
import com.munsun.deal.integrations.amqp.kafka.payload.EmailMessage;
import com.munsun.deal.integrations.amqp.kafka.payload.EmailMessageWithCreditDto;
import com.munsun.deal.integrations.amqp.kafka.payload.EmailMessageWithSesCode;
import com.munsun.deal.integrations.amqp.kafka.payload.enums.Theme;
import com.munsun.deal.integrations.amqp.kafka.producers.AuditProducer;
import com.munsun.deal.integrations.amqp.kafka.producers.DealProducer;
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
public class KafkaDealProducer implements DealProducer, AuditProducer {
    private final KafkaTemplate<String, EmailMessage> kafkaTemplate;
    private final KafkaTopics kafkaTopics;

    @Override
    public void sendAudit(AuditActionPayload auditActionPayload) {
        Message<AuditActionPayload> message = MessageBuilder
                .withPayload(auditActionPayload)
                .setHeader(KafkaHeaders.TOPIC, kafkaTopics.getAudit_logs())
                .build();
        kafkaTemplate.send(message);
    }

    @Override
    public void sendFinishRegistrationRequestNotification(String email, Theme theme, UUID statementId) {
        sendNotification(email, kafkaTopics.getFinish_registration(), theme, statementId);
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
                    .setHeader(KafkaHeaders.TOPIC, kafkaTopics.getSend_documents())
                .build();
        kafkaTemplate.send(message);
    }

    @Override
    public void sendSignCodeDocumentsNotification(String email, Theme theme, UUID statementId, UUID sesCode) {
        Message<EmailMessageWithSesCode> message = MessageBuilder
                .withPayload(new EmailMessageWithSesCode(email, theme, statementId, sesCode))
                .setHeader(KafkaHeaders.TOPIC, kafkaTopics.getSend_ses())
                .build();
        kafkaTemplate.send(message);
    }

    @Override
    public void sendSuccessSignDocumentsNotification(String email, Theme theme, UUID statementId) {
        sendNotification(email, kafkaTopics.getCredit_issued(), theme, statementId);
    }

    @Override
    public void sendScoringException(String email, Theme theme, UUID statementId) {
        //
    }

    @Override
    public void sendCreateDocumentsNotification(String email, Theme theme, UUID statementId) {
        sendNotification(email, kafkaTopics.getCreate_documents(), theme, statementId);
    }
}