package com.munsun.deal.advice;

import com.munsun.deal.dto.response.ErrorMessageDto;
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
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessageDto handlerStatementNotFoundException(Exception e) {
        log.error("Error = {}", e.getMessage());
        return new ErrorMessageDto(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDto handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.error("Error = {}", message);
        return new ErrorMessageDto(message);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handlerFeignException(FeignException e) {
        String message = String.valueOf(e.contentUTF8());
        log.error("Error's feign client = {}", e.contentUTF8());
        return ResponseEntity
                .status(e.status())
                .body(message);
    }
}
