package com.munsun.audit.controllers;

import com.munsun.audit.dto.AuditActionRequestDto;
import com.munsun.audit.dto.AuditActionRequestDto;
import com.munsun.audit.dto.AuditActionResponseDto;
import com.munsun.audit.services.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class AuditController implements AuditRestControllerApi {
    private final AuditService auditService;

    @Override
    public ResponseEntity<List<AuditActionResponseDto>> getAuditLogs(Integer page, Integer size, AuditActionRequestDto auditActionRequestDto) {
        return ResponseEntity
                .ok()
                .body(auditService.getLogs(auditActionRequestDto, Pageable.ofSize(size).withPage(page)));
    }
}
