package com.munsun.gateway.controllers;

import com.munsun.gateway.dto.*;
import com.munsun.gateway.integrations.http.AuditClient;
import com.munsun.gateway.integrations.http.AuthClient;
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
public class GatewayController implements GatewayV1Api {
    private final AuthClient authClient;
    private final DealClient dealClient;
    private final StatementClient statementClient;
    private final AuditClient auditClient;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ResponseEntity<List<AuditActionResponseDto>> getAuditLogs(Integer page, Integer size, AuditActionRequestDto auditActionRequestDto) {
        return ResponseEntity
                .ok()
                .body(auditClient.getAuditLogs(page, size, auditActionRequestDto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ResponseEntity<Void> getStatements(UUID statementId) {
        dealClient.updateStatusStatement(statementId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    public ResponseEntity<Void> createDocuments(UUID statementId) {
        dealClient.createDocument(statementId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    public ResponseEntity<Void> sendSesCodeForSignDocument(UUID statementId) {
        dealClient.getSesCodeDocument(statementId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    public ResponseEntity<Void> signCodeDocument(UUID statementId, String sesCode) {
        dealClient.signCodeDocument(statementId, sesCode);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    public ResponseEntity<Void> calculateCredit(UUID statementId, FinishRegistrationRequestDto finishRegistrationRequestDto) {
        dealClient.calculateCredit(statementId, finishRegistrationRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    public ResponseEntity<List<LoanOfferDto>> getLoanStatements(TypePayments typePayment, LoanStatementRequestDto loanStatementRequestDto) {
        return ResponseEntity
                .ok()
                .body(statementClient.getLoanStatements(typePayment, loanStatementRequestDto));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    public ResponseEntity<Void> selectLoanOffer(TypePayments typePayment, LoanOfferDto loanOfferDto) {
        statementClient.selectLoanOffer(typePayment, loanOfferDto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_GUEST')")
    @Override
    public ResponseEntity<SecurityInfoDto> login(UserInfoDto userInfoDto) {
        return ResponseEntity
                .ok()
                .body(authClient.login(userInfoDto));
    }

    @PreAuthorize("hasRole('GUEST')")
    @Override
    public ResponseEntity<Void> registration(UserInfoDto userInfoDto) {
        authClient.register(userInfoDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}