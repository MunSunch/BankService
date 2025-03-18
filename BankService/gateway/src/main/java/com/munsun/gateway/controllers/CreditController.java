package com.munsun.gateway.controllers;

import com.munsun.gateway.dto.FinishRegistrationRequestDto;
import com.munsun.gateway.dto.LoanOfferDto;
import com.munsun.gateway.dto.LoanStatementRequestDto;
import com.munsun.gateway.dto.TypePayments;
import com.munsun.gateway.integrations.http.DealClient;
import com.munsun.gateway.integrations.http.StatementClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CreditController implements CreditsApi {
    private final DealClient dealClient;
    private final StatementClient statementClient;

    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    public ResponseEntity<Void> calculateCredit(UUID statementId, String authorization, FinishRegistrationRequestDto finishRegistrationRequestDto) {
        dealClient.calculateCredit(statementId, finishRegistrationRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    public ResponseEntity<List<LoanOfferDto>> getLoanStatements(TypePayments typePayment, String authorization, LoanStatementRequestDto loanStatementRequestDto) {
        return ResponseEntity
                .ok()
                .body(statementClient.getLoanStatements(typePayment, loanStatementRequestDto));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    public ResponseEntity<Void> selectLoanOffer(TypePayments typePayment, String authorization, LoanOfferDto loanOfferDto) {
        statementClient.selectLoanOffer(typePayment, loanOfferDto);
        return ResponseEntity.ok().build();
    }
}
