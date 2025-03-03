package com.munsun.statement.clients;

import com.munsun.statement.config.FeignClientConfiguration;
import com.munsun.statement.dto.LoanOfferDto;
import com.munsun.statement.dto.LoanStatementRequestDto;
import com.munsun.statement.dto.TypePayments;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "deal-mc", url = "${client.deal.url}", configuration = FeignClientConfiguration.class)
public interface DealClient {
    @PostMapping("v1/deal/statement")
    ResponseEntity<List<LoanOfferDto>> getLoanOffers(@RequestParam(value = "typePayment") TypePayments typePayments,
                                                     @RequestBody LoanStatementRequestDto loanStatement);

    @PostMapping("v1/deal/offer/select")
    ResponseEntity<Void> selectLoanOffer(@RequestParam(value = "typePayment") TypePayments typePayments,
                                         @RequestBody LoanOfferDto loanOfferDto);
}
