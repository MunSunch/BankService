package com.munsun.gateway.integrations.http;

import com.munsun.gateway.dto.AuditActionRequestDto;
import com.munsun.gateway.dto.AuditActionResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "${services.audit.name}")
public interface AuditClient {
    @PostMapping("/audit/admin/v1/logs")
    List<AuditActionResponseDto> getAuditLogs(@RequestParam Integer page,
                                              @RequestParam Integer size,
                                              @RequestBody AuditActionRequestDto auditActionRequestDto);
}
