package com.munsun.calculator.services;

import com.munsun.calculator.dto.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

public interface CalculatorService {
    List<LoanOfferDto> calculateLoan(@NotBlank TypePayments typePayment, @Valid LoanStatementRequestDto loanStatementRequestDto);
    CreditDto calculateCredit(@NotBlank TypePayments typePayment, @Valid ScoringDataDto scoringDataDto);
}