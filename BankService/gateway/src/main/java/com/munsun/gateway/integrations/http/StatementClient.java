package com.munsun.gateway.integrations.http;

import com.munsun.gateway.config.StatementClientConfig;
import com.munsun.gateway.dto.LoanOfferDto;
import com.munsun.gateway.dto.LoanStatementRequestDto;
import com.munsun.gateway.dto.TypePayments;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "${services.statement.name}", configuration = StatementClientConfig.class)
public interface StatementClient {
    @PostMapping("/v1/statement")
    List<LoanOfferDto> getLoanStatements(@RequestParam(name = "typePayment") TypePayments typePayments,
                                                    @RequestBody LoanStatementRequestDto loanStatementRequestDto);

    @PostMapping("/v1/statement/offer")
    void selectLoanOffer(@RequestParam(name = "typePayment") TypePayments typePayments,
                         @RequestBody LoanOfferDto loanOfferDto);
}
