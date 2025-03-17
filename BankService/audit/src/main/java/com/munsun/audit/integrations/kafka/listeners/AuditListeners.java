package com.munsun.audit.integrations.kafka.listeners;

import com.munsun.audit.integrations.kafka.payload.AuditActionPayload;
import com.munsun.audit.services.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuditListeners {
    private final AuditService auditService;

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}",
                   topics = "${kafka.topics.audit}")
    public void handleFinishRegistration(AuditActionPayload auditAction) {
        log.info("Received message from kafka: {}", auditAction);
        auditService.createNewLog(auditAction);
    }
}
