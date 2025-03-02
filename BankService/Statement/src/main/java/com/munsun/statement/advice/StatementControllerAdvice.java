package com.munsun.statement.advice;

import com.munsun.statement.dto.ErrorMessageDto;
import com.munsun.statement.exceptions.PrescoringException;
import com.munsun.statement.exceptions.StatementNotFoundException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class StatementControllerAdvice {
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorMessageDto> handlerFeignException(FeignException e) {
        log.error("Exception deal-mc: status={}, message={}", e.status(), e.contentUTF8());
        return ResponseEntity
                .status(e.status())
                .body(new ErrorMessageDto(e.contentUTF8()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageDto> handler(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.error("Validation error DTO, message={}", message);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessageDto(message));
    }

    @ExceptionHandler(StatementNotFoundException.class)
    public ResponseEntity<ErrorMessageDto> handlerStatementNotFoundException(StatementNotFoundException e) {
        log.error("Statement not found! StatementId = {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessageDto(e.getMessage()));
    }
}
