package com.munsun.calculator.advice;

import com.munsun.calculator.dto.ErrorMessageDto;
import com.munsun.calculator.exceptions.ScoringException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class CalculatorControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageDto> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.debug(message);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessageDto(message));
    }

    @ExceptionHandler(ScoringException.class)
    public ResponseEntity<ErrorMessageDto> handlerScoring(ScoringException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessageDto(e.getMessage()));
    }
}
