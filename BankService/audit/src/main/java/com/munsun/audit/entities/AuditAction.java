package com.munsun.audit.entities;

import com.munsun.audit.dto.OperationType;
import com.munsun.audit.dto.ServiceType;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.UUID;

@Getter
@Builder
@RedisHash("audit")
@FieldNameConstants
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class AuditAction {
    @Id
    UUID uuid;
    @Indexed
    OperationType type;
    @Indexed
    ServiceType service;
    String message;
}
