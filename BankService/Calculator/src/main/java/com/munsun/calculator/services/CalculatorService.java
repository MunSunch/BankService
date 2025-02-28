package com.munsun.calculator.services;

import com.munsun.calculator.dto.request.LoanStatementRequestDto;
import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.dto.request.enums.TypePayments;
import com.munsun.calculator.dto.response.CreditDto;
import com.munsun.calculator.dto.response.LoanOfferDto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

public interface CalculatorService {
    List<LoanOfferDto> calculateLoan(@NotBlank TypePayments typePayment, @Valid LoanStatementRequestDto loanStatementRequestDto);

    CreditDto calculateCredit(@NotBlank TypePayments typePayment, @Valid ScoringDataDto scoringDataDto);
}