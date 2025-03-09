package com.munsun.gateway.integrations.http;

import com.munsun.gateway.config.DealClientConfig;
import com.munsun.gateway.dto.FinishRegistrationRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(value = "${services.deal.name}", configuration = DealClientConfig.class)
public interface DealClient {
    @PutMapping("/v1/deal/admin/statement/{statementId}/status")
    void updateStatusStatement(@PathVariable(name = "statementId") UUID statementId);

    @PostMapping("/v1/deal/document/{statementId}/send")
    void createDocument(@PathVariable(name = "statementId") UUID statementId);

    @PostMapping("/v1/deal/document/{statementId}/code")
    void signCodeDocument(@PathVariable(name = "statementId") UUID statementId,
                          @RequestParam(name = "sesCode") String signCode);

    @PostMapping("/v1/deal/document/{statementId}/sign")
    void getSesCodeDocument(@PathVariable(name = "statementId") UUID statementId);

    @PostMapping("/v1/deal/calculate/{statementId}")
    void calculateCredit(@PathVariable(name = "statementId") UUID statementId,
                         @RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto);
}
