package com.munsun.deal.controllers;

import com.munsun.deal.controllers.annotations.CalculateCreditSwaggerDescription;
import com.munsun.deal.controllers.annotations.CalculateLoanOfferSwaggerDescription;
import com.munsun.deal.controllers.annotations.SelectLoanOfferSwaggerDescription;
import com.munsun.deal.dto.request.FinishRegistrationRequestDto;
import com.munsun.deal.dto.request.LoanStatementRequestDto;
import com.munsun.deal.dto.response.LoanOfferDto;
import com.munsun.deal.services.DealService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("v1/deal")
@RequiredArgsConstructor
public class DealRestController {
    private final DealService service;
    @PostMapping("/statement")
    @CalculateLoanOfferSwaggerDescription
    public List<LoanOfferDto> calculateLoanOffers(@RequestBody @Valid LoanStatementRequestDto loanStatement) {
        return service.getLoanOffers(loanStatement);
    }

    @PostMapping("/offer/select")
    @SelectLoanOfferSwaggerDescription
    public void selectLoanOffer(@RequestBody LoanOfferDto loanOffer) {
        service.selectLoanOffer(loanOffer);
    }

    @PostMapping("/calculate/{statementId}")
    @CalculateCreditSwaggerDescription
    public void calculateCredit(@RequestBody FinishRegistrationRequestDto finishRegistration,
                                @PathVariable @NotBlank String statementId)
    {
        service.calculateCredit(statementId, finishRegistration);
    }
}