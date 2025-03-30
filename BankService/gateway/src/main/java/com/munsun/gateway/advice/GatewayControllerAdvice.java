package com.munsun.gateway.advice;

import com.munsun.gateway.dto.ErrorDto;
import com.munsun.gateway.dto.ErrorMessageDto;
import com.munsun.gateway.exceptions.*;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GatewayControllerAdvice {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessageDto> handleConstraintViolationException(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));
        log.error("Error = {}", message);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessageDto(message));
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

    @ExceptionHandler(UserCreateException.class)
    public ResponseEntity<ErrorDto> handleUserCreateException(UserCreateException e) {
        log.error("User create exception: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorDto.builder()
                        .message(e.getMessage())
                        .httpStatusCode(HttpStatus.CONFLICT.value())
                        .exception(e.getClass().getSimpleName())
                        .build());
    }

    @ExceptionHandler(InvalidSesCode.class)
    public ResponseEntity<ErrorDto> handleInvalidSesCode(InvalidSesCode e) {
        log.error("Invalid ses code: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorDto.builder()
                        .message(e.getMessage())
                        .httpStatusCode(HttpStatus.CONFLICT.value())
                        .exception(e.getClass().getSimpleName())
                        .build());
    }

    @ExceptionHandler({InvalidCredentialsException.class, PrescoringException.class})
    public ResponseEntity<ErrorDto> handleInvalidCredentialsException(Exception e) {
        log.error("Invalid credentials: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body( ErrorDto.builder()
                        .message(e.getMessage())
                        .httpStatusCode(HttpStatus.BAD_REQUEST.value())
                        .exception(e.getClass().getSimpleName())
                        .build());
    }

    @ExceptionHandler({UserNotFoundException.class, StatementNotFoundException.class})
    public ResponseEntity<ErrorDto> handleUserNotFoundException(Exception e) {
        log.error("Object not found: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorDto.builder()
                        .message(e.getMessage())
                        .httpStatusCode(HttpStatus.NOT_FOUND.value())
                        .exception(e.getClass().getSimpleName())
                        .build());
    }

    @ExceptionHandler(ScoringException.class)
    public ResponseEntity<ErrorDto> handleScoringException(ScoringException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorDto.builder()
                        .message(e.getMessage())
                        .httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .exception(e.getClass().getSimpleName())
                        .build());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorDto> handleAuthorizationDeniedException(AuthorizationDeniedException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ErrorDto.builder()
                        .message(e.getMessage())
                        .httpStatusCode(HttpStatus.FORBIDDEN.value())
                        .exception(e.getClass().getSimpleName())
                        .build());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDto> handleRuntimeException(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorDto.builder()
                        .message(e.getMessage())
                        .httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .exception(e.getClass().getSimpleName())
                        .build());
    }
}
