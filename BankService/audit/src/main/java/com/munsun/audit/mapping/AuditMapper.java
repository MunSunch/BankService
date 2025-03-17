package com.munsun.audit.mapping;

import com.munsun.audit.dto.AuditActionRequestDto;
import com.munsun.audit.dto.AuditActionResponseDto;
import com.munsun.audit.entities.AuditAction;
import com.munsun.audit.integrations.kafka.payload.AuditActionPayload;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditMapper {

    AuditAction toEntity(AuditActionPayload auditAction);

    AuditAction toEntity(AuditActionRequestDto auditActionDto);

    AuditActionResponseDto toDto(AuditAction auditAction);
}
