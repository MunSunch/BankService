package com.munsun.statement.controllers;

import com.munsun.statement.controllers.annotations.GetLoanOffersSwaggerDescription;
import com.munsun.statement.controllers.annotations.SelectLoanOfferSwaggerDescription;
import com.munsun.statement.dto.LoanOfferDto;
import com.munsun.statement.dto.LoanStatementRequestDto;
import com.munsun.statement.services.StatementService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/statement")
public class StatementController {
    private final StatementService service;
    @PostMapping
    @GetLoanOffersSwaggerDescription
    public List<LoanOfferDto> getLoanOffers(@RequestBody @Valid LoanStatementRequestDto loanStatement) {
        return service.getLoanOffers(loanStatement);
    }

    @PostMapping("/offer")
    @SelectLoanOfferSwaggerDescription
    public void selectLoanOffer(@RequestBody @Valid LoanOfferDto loanOfferDto) {
        service.selectLoanOffer(loanOfferDto);
    }
}
