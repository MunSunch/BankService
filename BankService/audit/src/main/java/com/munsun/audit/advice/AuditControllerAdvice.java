package com.munsun.audit.advice;

import com.munsun.audit.dto.ErrorResponseDto;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class AuditControllerAdvice {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handlerConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponseDto.builder()
                                .statusCodeHttp(HttpStatus.BAD_REQUEST.value())
                                .message(e.getMessage())
                                .exception(e.getClass().getSimpleName())
                                .timestamp(LocalDateTime.now().atOffset(java.time.ZoneOffset.UTC))
                                .build()
                );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDto> handlerRuntimeException(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponseDto.builder()
                                .statusCodeHttp(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(e.getMessage())
                                .exception(e.getClass().getSimpleName())
                                .timestamp(LocalDateTime.now().atOffset(java.time.ZoneOffset.UTC))
                                .build()
                );
    }
}
