package com.munsun.gateway.controllers;

import com.munsun.gateway.integrations.http.DealClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DocumentsController implements DocumentsApi {
    private final DealClient dealClient;

    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    public ResponseEntity<Void> createDocuments(UUID statementId, String authorization) {
        dealClient.createDocument(statementId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    public ResponseEntity<Void> sendSesCodeForSignDocument(UUID statementId, String authorization) {
        dealClient.getSesCodeDocument(statementId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    public ResponseEntity<Void> signCodeDocument(UUID statementId, String sesCode, String authorization) {
        dealClient.signCodeDocument(statementId, sesCode);
        return ResponseEntity.ok().build();
    }
}