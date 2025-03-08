package com.munsun.auth.advice;

import com.munsun.auth.dto.ErrorDto;
import com.munsun.auth.exceptions.InvalidCredentialsException;
import com.munsun.auth.exceptions.UserCreateException;
import com.munsun.auth.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AuthExceptionAdvice {
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


    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorDto> handleInvalidCredentialsException(InvalidCredentialsException e) {
        log.error("Invalid credentials: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body( ErrorDto.builder()
                        .message(e.getMessage())
                        .httpStatusCode(HttpStatus.BAD_REQUEST.value())
                        .exception(e.getClass().getSimpleName())
                        .build());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFoundException e) {
        log.error("User not found: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorDto.builder()
                        .message(e.getMessage())
                        .httpStatusCode(HttpStatus.NOT_FOUND.value())
                        .exception(e.getClass().getSimpleName())
                        .build());
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDto> handleRuntimeException(RuntimeException e) {
        log.error("Exception: ", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorDto.builder()
                        .message(e.getMessage())
                        .httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .exception(e.getClass().getSimpleName())
                        .build());
    }
}
