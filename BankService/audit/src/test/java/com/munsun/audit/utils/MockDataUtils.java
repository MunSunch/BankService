package com.munsun.audit.utils;

import com.munsun.audit.dto.AuditActionRequestDto;
import com.munsun.audit.dto.AuditActionResponseDto;
import com.munsun.audit.dto.OperationType;
import com.munsun.audit.dto.ServiceType;
import com.munsun.audit.entities.AuditAction;

import java.util.UUID;

public class MockDataUtils {
    public static AuditAction getMockAuditAction_DealStarted() {
        return AuditAction.builder()
                .type(OperationType.START)
                .service(ServiceType.DEAL)
                .message("Deal has been started")
                .build();
    }

    public static AuditActionRequestDto getMockAuditActionDto_DealStarted() {
        return new AuditActionRequestDto(null, OperationType.START, ServiceType.DEAL);
    }

    public static AuditActionRequestDto getMockAuditActionDto_Deal() {
        return new AuditActionRequestDto(null, null, ServiceType.DEAL);
    }

    public static AuditAction getMockAuditAction_DealSuccess() {
        return AuditAction.builder()
                .type(OperationType.SUCCESS)
                .service(ServiceType.DEAL)
                .message("Deal successfully completed")
                .build();
    }

    public static AuditAction getMockAuditAction_DealFailure() {
        return AuditAction.builder()
                .type(OperationType.FAILURE)
                .service(ServiceType.DEAL)
                .message("Deal failed")
                .build();
    }

    public static AuditAction getMockAuditAction_StatementFailure() {
        return AuditAction.builder()
                .type(OperationType.FAILURE)
                .service(ServiceType.STATEMENT)
                .message("Statement failed")
                .build();
    }

    public static AuditAction getMockAuditAction_StatementStarted() {
        return AuditAction.builder()
                .type(OperationType.START)
                .service(ServiceType.STATEMENT)
                .message("Statement has been started")
                .build();
    }

    public static AuditActionRequestDto getMockAuditActionDto() {
        return AuditActionRequestDto.builder()
                .uuid(UUID.randomUUID())
                .type(OperationType.START)
                .service(ServiceType.STATEMENT)
                .build();
    }

    public static String getMockAuditActionDto_Invalid() {
        return "{\"uuid\":\"98444b84-2018-4298-b264-792991650708\",\"type\":\"START\",\"service\":\"STATEMENT\"}";
    }
}
