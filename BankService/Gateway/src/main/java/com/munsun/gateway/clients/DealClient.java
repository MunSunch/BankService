package com.munsun.gateway.clients;

import com.munsun.gateway.dto.request.FinishRegistrationRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "deal-client", url = "${clients.url.deal}/v1/deal")
public interface DealClient {
    @PostMapping(value = "/calculate/{statementId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    void finishRegistration(@PathVariable String statementId,
                            @RequestBody FinishRegistrationRequestDto finishRegistration);

    @PostMapping(value = "/document/{statementId}/send", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    void createDocument(@PathVariable String statementId);

    @PostMapping(value = "/document/{statementId}/sign", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    void sendDocument(@PathVariable String statementId);

    @PostMapping(value = "/document/{statementId}/code", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    void confirmLoanOffer(@PathVariable String statementId, @RequestParam("sesCode") String confirmCode);
}
