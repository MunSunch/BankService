package com.munsun.statement.controllers;

import com.munsun.statement.clients.DealClient;
import com.munsun.statement.dto.LoanOfferDto;
import com.munsun.statement.dto.LoanStatementRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/statement")
public class StatementController {
    private final DealClient client;
    @PostMapping
    public List<LoanOfferDto> getLoanOffers(@RequestBody @Valid LoanStatementRequestDto loanStatement) {
        return client.getLoanOffers(loanStatement);
    }

    @PostMapping("/offer")
    public void selectLoanOffer(@RequestBody LoanOfferDto loanOfferDto) {
        client.selectLoanOffer(loanOfferDto);
    }
}
