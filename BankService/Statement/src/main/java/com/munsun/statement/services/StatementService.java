package com.munsun.statement.services;

import com.munsun.statement.dto.LoanOfferDto;
import com.munsun.statement.dto.LoanStatementRequestDto;

import javax.validation.Valid;
import java.util.List;

public interface StatementService {
    List<LoanOfferDto> getLoanOffers(@Valid LoanStatementRequestDto loanStatement);

    void selectLoanOffer(LoanOfferDto loanOfferDto);
}
