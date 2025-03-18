package com.munsun.gateway.controllers;

import com.munsun.gateway.dto.SecurityInfoDto;
import com.munsun.gateway.dto.UserInfoDto;
import com.munsun.gateway.integrations.http.AuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SecurityController implements SecurityApi {
    private final AuthClient authClient;

    @PreAuthorize("hasRole('ROLE_GUEST')")
    @Override
    public ResponseEntity<SecurityInfoDto> login(UserInfoDto userInfoDto) {
        return ResponseEntity
                .ok()
                .body(authClient.login(userInfoDto));
    }

    @PreAuthorize("hasRole('ROLE_GUEST')")
    @Override
    public ResponseEntity<Void> registration(UserInfoDto userInfoDto) {
        authClient.register(userInfoDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
