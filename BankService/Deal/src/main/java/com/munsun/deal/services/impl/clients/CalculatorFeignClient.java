package com.munsun.deal.services.impl.clients;

import com.munsun.deal.dto.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "${clients.calculator.name}")
public interface CalculatorFeignClient {
    @PostMapping("/v1/calculator/offers")
    List<LoanOfferDto> getLoanOffers(@RequestParam TypePayments typePayment,
                                     @RequestBody LoanStatementRequestDto loanStatementRequestDto);

    @PostMapping("/v1/calculator/calc")
    CreditDto getCredit(@RequestParam TypePayments typePayment,
                        @RequestBody ScoringDataDto scoringDataDto);
}
