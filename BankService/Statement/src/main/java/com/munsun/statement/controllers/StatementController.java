package com.munsun.statement.controllers;

import com.munsun.statement.dto.LoanOfferDto;
import com.munsun.statement.dto.LoanStatementRequestDto;
import com.munsun.statement.dto.TypePayments;
import com.munsun.statement.services.StatementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatementController implements V1Api {
    private final StatementService service;

    @Override
    public ResponseEntity<List<LoanOfferDto>> _getLoanOffers(TypePayments typePayment, LoanStatementRequestDto loanStatementRequestDto) {
        return ResponseEntity
                .ok()
                .body(service.getLoanOffers(typePayment, loanStatementRequestDto));
    }

    @Override
    public ResponseEntity<Void> _selectLoanOffer(TypePayments typePayment, LoanOfferDto loanOfferDto) {
        service.selectLoanOffer(typePayment, loanOfferDto);
        return ResponseEntity
                .ok()
                .build();
    }
}
