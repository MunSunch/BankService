package com.munsun.deal.integrations.amqp.kafka.payload;

import com.munsun.deal.integrations.amqp.kafka.payload.enums.OperationType;
import com.munsun.deal.integrations.amqp.kafka.payload.enums.ServiceType;

public record AuditActionPayload(
        OperationType type,
        ServiceType service,
        String message
) {}
