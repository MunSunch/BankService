package com.munsun.deal.controllers;

import com.munsun.deal.dto.request.FinishRegistrationRequestDto;
import com.munsun.deal.dto.request.LoanStatementRequestDto;
import com.munsun.deal.dto.response.LoanOfferDto;
import com.munsun.deal.services.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/deal")
@RequiredArgsConstructor
public class DealRestController {
    private final DealService service;
    @PostMapping("/statement")
    public List<LoanOfferDto> calculateLoanOffers(@RequestBody LoanStatementRequestDto loanStatement) {
        return service.getLoanOffers(loanStatement);
    }

    @PostMapping("/offer/select")
    public void selectLoanOffer(@RequestBody LoanOfferDto loanOffer) {
        service.selectLoanOffer(loanOffer);
    }

    @PostMapping("/calculate/{statementId}")
    public void selectLoanOffer(@RequestBody FinishRegistrationRequestDto finishRegistration,
                                @PathVariable String statementId)
    {
        service.calculateCredit(statementId, finishRegistration);
    }
}