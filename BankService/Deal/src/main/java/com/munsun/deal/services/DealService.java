package com.munsun.deal.services;

import com.munsun.deal.dto.request.FinishRegistrationRequestDto;
import com.munsun.deal.dto.request.LoanStatementRequestDto;
import com.munsun.deal.dto.response.LoanOfferDto;
import com.munsun.deal.models.Statement;

import java.util.List;
import java.util.UUID;

public interface DealService {
    List<LoanOfferDto> getLoanOffers(LoanStatementRequestDto loanStatement);
    void calculateCredit(String statementId, FinishRegistrationRequestDto finishRegistration);
    void selectLoanOffer(LoanOfferDto loanOffer);
    void prepareDocuments(UUID statementId);
    void createSignCodeDocuments(UUID statementId);
    void signDocuments(UUID statementId, String sesCode);
    void updateStatus(UUID statementId);

    Statement getStatement(UUID statementId);
}
