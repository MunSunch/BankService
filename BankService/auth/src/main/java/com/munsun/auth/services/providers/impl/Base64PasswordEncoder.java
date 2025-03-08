package com.munsun.auth.services.providers.impl;

import com.munsun.auth.services.providers.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class Base64PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
}
