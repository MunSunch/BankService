package com.munsun.deal.services.impl.clients;

import com.munsun.deal.dto.request.LoanStatementRequestDto;
import com.munsun.deal.dto.request.ScoringDataDto;
import com.munsun.deal.dto.response.CreditDto;
import com.munsun.deal.dto.response.LoanOfferDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "calculator-mc", url = "${client.calculator.url}")
public interface CalculatorFeignClient {
    @PostMapping("/v1/calculator/offers")
    List<LoanOfferDto> getLoanOffers(LoanStatementRequestDto loanStatementRequestDto);

    @PostMapping("/v1/calculator/calc")
    CreditDto getCredit(ScoringDataDto scoringDataDto);
}
