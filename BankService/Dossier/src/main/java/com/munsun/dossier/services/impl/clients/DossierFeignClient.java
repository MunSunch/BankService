package com.munsun.dossier.services.impl.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.UUID;

@FeignClient(value = "${clients.deal.name}")
public interface DossierFeignClient {
    @PutMapping("v1/deal/admin/statement/{statementId}/status")
    void updateStatus(@PathVariable UUID statementId);
}
