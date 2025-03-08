package com.munsun.auth.controllers;

import com.munsun.auth.dto.SecurityInfoDto;
import com.munsun.auth.dto.UserInfoDto;
import com.munsun.auth.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthRestControllerApi {
    private final AuthService authService;

    @Override
    public ResponseEntity<SecurityInfoDto> _login(UserInfoDto userInfoDto) {
        return ResponseEntity
                .ok()
                .body(authService.login(userInfoDto));
    }

    @Override
    public ResponseEntity<Void> _registration(UserInfoDto userInfoDto) {
        authService.register(userInfoDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Override
    public ResponseEntity<Void> _validate(String token) {
        authService.validate(token);
        return ResponseEntity
                .ok().build();
    }
}
