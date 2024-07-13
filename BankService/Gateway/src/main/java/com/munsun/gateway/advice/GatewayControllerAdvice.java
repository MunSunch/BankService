package com.munsun.gateway.advice;

import com.munsun.gateway.exceptions.DealClientException;
import com.munsun.gateway.exceptions.StatementClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GatewayControllerAdvice {
    @ExceptionHandler(StatementClientException.class)
    public ResponseEntity<String> handleStatementClientException(StatementClientException e) {
        log.info("Response: thrown statement-mc exception={}", e.toString());
        return ResponseEntity
                .status(e.getStatusCode())
                .body(e.getResponseBody());
    }

    @ExceptionHandler(DealClientException.class)
    public ResponseEntity<String> handleDealClientException(DealClientException e) {
        log.info("Response: thrown deal-mc exception={}", e.toString());
        return ResponseEntity
                .status(e.getStatusCode())
                .body(e.getResponseBody());
    }
}
