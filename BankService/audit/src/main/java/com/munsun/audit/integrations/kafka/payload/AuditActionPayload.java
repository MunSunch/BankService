package com.munsun.audit.integrations.kafka.payload;

import com.munsun.audit.dto.OperationType;
import com.munsun.audit.dto.ServiceType;

public record AuditActionPayload(
        OperationType type,
        ServiceType service,
        String message
) {}