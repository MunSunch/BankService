package com.munsun.deal.services;

import com.munsun.deal.dto.FinishRegistrationRequestDto;
import com.munsun.deal.dto.LoanOfferDto;
import com.munsun.deal.dto.LoanStatementRequestDto;
import com.munsun.deal.dto.TypePayments;

import java.util.List;
import java.util.UUID;

public interface DealService {
    List<LoanOfferDto> getLoanOffers(TypePayments typePayment, LoanStatementRequestDto loanStatement);
    void calculateCredit(String statementId, FinishRegistrationRequestDto finishRegistration);
    void selectLoanOffer(TypePayments typePayment, LoanOfferDto loanOffer);
    void prepareDocuments(UUID statementId);
    void createSignCodeDocuments(UUID statementId);
    void signDocuments(UUID statementId, String sesCode);
    void updateStatus(UUID statementId);
}
