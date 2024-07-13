package com.munsun.gateway.clients;

import com.munsun.gateway.dto.request.LoanStatementRequestDto;
import com.munsun.gateway.dto.response.LoanOfferDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "statement-client", url = "${clients.url.statement}/v1")
public interface StatementClient {
    @PostMapping(value = "/statement", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    String getLoanOffers(LoanStatementRequestDto loanStatement);
    @PostMapping(value = "/statement/offer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    void selectOffer(LoanOfferDto loanOfferDto);
}
