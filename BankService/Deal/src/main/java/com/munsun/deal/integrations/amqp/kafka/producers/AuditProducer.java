package com.munsun.deal.integrations.amqp.kafka.producers;

import com.munsun.deal.integrations.amqp.kafka.payload.AuditActionPayload;

public interface AuditProducer {
    void sendAudit(AuditActionPayload auditActionPayload);
}
