package com.munsun.deal.controllers;

import com.munsun.deal.dto.FinishRegistrationRequestDto;
import com.munsun.deal.dto.LoanOfferDto;
import com.munsun.deal.dto.LoanStatementRequestDto;
import com.munsun.deal.dto.TypePayments;
import com.munsun.deal.services.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class DealRestController implements V1Api {
    private final DealService service;

    @Override
    public ResponseEntity<Void> calculateCredit(String statementId, FinishRegistrationRequestDto finishRegistrationRequestDto) {
        service.calculateCredit(statementId, finishRegistrationRequestDto);
        return ResponseEntity
                .ok()
                .build();
    }

    @Override
    public ResponseEntity<List<LoanOfferDto>> calculateLoanOffers(TypePayments typePayment, LoanStatementRequestDto loanStatementRequestDto) {
        return ResponseEntity
                .ok()
                .body(service.getLoanOffers(typePayment, loanStatementRequestDto));
    }

    @Override
    public ResponseEntity<Void> selectLoanOffer(TypePayments typePayment, LoanOfferDto loanOfferDto) {
        service.selectLoanOffer(typePayment, loanOfferDto);
        return ResponseEntity
                .ok()
                .build();
    }

    @Override
    public ResponseEntity<Void> createDocument(UUID statementId) {
        service.prepareDocuments(statementId);
        return ResponseEntity
                .ok()
                .build();
    }

    @Override
    public ResponseEntity<Void> sendCodeDocument(UUID statementId) {
        service.createSignCodeDocuments(statementId);
        return ResponseEntity
                .ok()
                .build();
    }

    @Override
    public ResponseEntity<Void> signCodeDocument(UUID statementId, String sesCode) {
        service.signDocuments(statementId, sesCode);
        return ResponseEntity
                .ok()
                .build();
    }

    @Override
    public ResponseEntity<Void> updateStatusDocument(UUID statementId) {
        service.updateStatus(statementId);
        return ResponseEntity
                .ok()
                .build();
    }
}