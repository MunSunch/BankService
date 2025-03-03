package com.munsun.calculator.controllers;

import com.munsun.calculator.dto.*;
import com.munsun.calculator.services.CalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CalculatorController implements V1Api {
    private final CalculatorService calculatorService;

    @Override
    public ResponseEntity<List<LoanOfferDto>> _calculatePossibleLoanTerms(TypePayments typePayment, LoanStatementRequestDto loanStatementRequestDto) {
        return ResponseEntity
                .ok()
                .body(calculatorService.calculateLoan(typePayment, loanStatementRequestDto));
    }

    @Override
    public ResponseEntity<CreditDto> _fullCalculateLoanParametersAndScoring(TypePayments typePayment, ScoringDataDto scoringDataDto) {
        return ResponseEntity
                .ok()
                .body(calculatorService.calculateCredit(typePayment, scoringDataDto));
    }
}