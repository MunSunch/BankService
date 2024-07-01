package com.munsun.deal.advice;

import com.munsun.deal.dto.response.ErrorMessageDto;
import com.munsun.deal.exceptions.InvalidSesCode;
import com.munsun.deal.exceptions.PrescoringException;
import com.munsun.deal.exceptions.ScoringException;
import com.munsun.deal.exceptions.StatementNotFoundException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class DealRestControllerAdvice {
    @ExceptionHandler(StatementNotFoundException.class)
    public ResponseEntity<ErrorMessageDto> handlerStatementNotFoundException(Exception e) {
        log.error("Error = {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessageDto(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageDto> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.error("Error = {}", message);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessageDto(message));
    }

    @ExceptionHandler(PrescoringException.class)
    public ResponseEntity<ErrorMessageDto> handlerPrescoringException(PrescoringException e) {
        log.error("Prescoring error: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessageDto(e.getMessage()));
    }

    @ExceptionHandler(ScoringException.class)
    public ResponseEntity<String> handlerScoringException(ScoringException e) {
        log.error("Scoring error = {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handlerFeignException(FeignException e) {
        String message = String.valueOf(e.contentUTF8());
        log.error("Error's feign client = {}", e.contentUTF8());
        return ResponseEntity
                .status(e.status())
                .body(message);
    }

    @ExceptionHandler(InvalidSesCode.class)
    public ResponseEntity<String> handlerInvalidSesCode(Exception e) {
        log.error("Invalid ses code = {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(e.getMessage());
    }
}
