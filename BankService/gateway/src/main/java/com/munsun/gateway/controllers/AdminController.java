package com.munsun.gateway.controllers;

import com.munsun.gateway.dto.AuditActionRequestDto;
import com.munsun.gateway.dto.AuditActionResponseDto;
import com.munsun.gateway.integrations.http.AuditClient;
import com.munsun.gateway.integrations.http.AuthClient;
import com.munsun.gateway.integrations.http.DealClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AdminController implements AdminApi {
    private final AuditClient auditClient;
    private final DealClient dealClient;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ResponseEntity<Void> getStatements(UUID statementId, String authorization) {
        dealClient.updateStatusStatement(statementId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<AuditActionResponseDto>> getAuditLogs(Integer page, Integer size, String authorization, AuditActionRequestDto auditActionRequestDto) {
        return ResponseEntity
                .ok()
                .body(auditClient.getAuditLogs(page, size, auditActionRequestDto));
    }
}
