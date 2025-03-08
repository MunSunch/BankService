package com.munsun.auth.services.providers.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.munsun.auth.entities.enums.Role;
import com.munsun.auth.exceptions.InvalidCredentialsException;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@Getter
public class JwtProvider {
    @Value("${spring.application.name}")
    private String issuerName;

    @Value("${security.token.type}")
    private String tokenType;

    @Value("${security.token.secret}")
    private String secretKey;

    @Value("${security.token.expire.ms}")
    private Long expire;

    private Algorithm algorithm;

    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC256(secretKey);
    }

    public String createToken(String username, Role role) {
        Date now = new Date();
        return tokenType + " "+ JWT.create()
                                .withIssuer(issuerName)
                                .withClaim("username", username)
                                .withClaim("role", role.getRole())
                                .withIssuedAt(now)
                                .withExpiresAt(new Date(now.getTime()+expire))
                                .sign(algorithm);
    }

    public void validateToken(String token) {
        try {
            if(!token.startsWith(tokenType+" ")) {
                throw new InvalidCredentialsException("Invalid type token=" + tokenType);
            }
            token = token.substring(tokenType.length()+1);
            JWT
                .require(algorithm)
                .withIssuer(issuerName)
                .build()
                .verify(token);
        } catch (Exception e) {
            log.warn("Invalid token: {}", e.getMessage());
            throw new InvalidCredentialsException("Invalid token: " + e.getMessage());
        }
    }
}
