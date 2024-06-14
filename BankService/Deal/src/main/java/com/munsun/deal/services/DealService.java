package com.munsun.deal.services;

import com.munsun.deal.dto.request.FinishRegistrationRequestDto;
import com.munsun.deal.dto.request.LoanStatementRequestDto;
import com.munsun.deal.dto.response.LoanOfferDto;

import java.util.List;

public interface DealService {
    List<LoanOfferDto> getLoanOffers(LoanStatementRequestDto loanStatement);
    void calculateCredit(String statementId, FinishRegistrationRequestDto finishRegistration);
    void selectLoanOffer(LoanOfferDto loanOffer);
}
