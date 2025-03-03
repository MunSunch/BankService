package com.munsun.statement.services;

import com.munsun.statement.dto.LoanOfferDto;
import com.munsun.statement.dto.LoanStatementRequestDto;
import com.munsun.statement.dto.TypePayments;

import javax.validation.Valid;
import java.util.List;

public interface StatementService {
    List<LoanOfferDto> getLoanOffers(TypePayments typePayment, @Valid LoanStatementRequestDto loanStatement);

    void selectLoanOffer(TypePayments typePayment, LoanOfferDto loanOfferDto);
}
