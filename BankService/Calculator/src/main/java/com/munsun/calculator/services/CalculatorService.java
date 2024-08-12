package com.munsun.calculator.services;

import com.munsun.calculator.dto.request.LoanStatementRequestDto;
import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.dto.response.CreditDto;
import com.munsun.calculator.dto.response.LoanOfferDto;

import java.util.List;

public interface CalculatorService {
    CreditDto calculateCredit(ScoringDataDto scoringDataDto);
    List<LoanOfferDto> calculateLoan(LoanStatementRequestDto loanStatementRequestDto);
}