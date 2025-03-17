package com.munsun.audit.services;

import com.munsun.audit.dto.AuditActionRequestDto;
import com.munsun.audit.dto.AuditActionResponseDto;
import com.munsun.audit.entities.AuditAction;
import com.munsun.audit.integrations.kafka.payload.AuditActionPayload;
import com.munsun.audit.mapping.AuditMapper;
import com.munsun.audit.repositories.AuditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditService {
    private final AuditRepository auditRepository;
    private final AuditMapper mapper;

    public void createNewLog(AuditActionPayload auditAction) {
        AuditAction audit = mapper.toEntity(auditAction);
        auditRepository.save(audit);
    }

    public List<AuditActionResponseDto> getLogs(AuditActionRequestDto auditActionDto, Pageable page) {
        AuditAction example = mapper.toEntity(auditActionDto);
        return auditRepository.findAll(Example.of(example), page)
                .get().map(mapper::toDto).toList();
    }
}
