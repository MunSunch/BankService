package com.munsun.statement.clients;

import com.munsun.statement.dto.LoanOfferDto;
import com.munsun.statement.dto.LoanStatementRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "deal-mc", url = "${client.deal.url}")
public interface DealClient {
    @PostMapping("v1/deal/statement")
    List<LoanOfferDto> getLoanOffers(LoanStatementRequestDto loanStatement);

    @PostMapping("v1/deal/offer/select")
    void selectLoanOffer(LoanOfferDto loanOfferDto);
}
