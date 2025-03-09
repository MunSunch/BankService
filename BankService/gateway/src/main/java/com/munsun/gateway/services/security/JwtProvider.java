package com.munsun.gateway.services.security;

import com.auth0.jwt.JWT;
import com.munsun.gateway.integrations.http.AuthClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final AuthClient authClient;
    @Value("${jwt.header}")
    private String header;

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(header);
    }

    public boolean validateToken(String token) {
        try {
            authClient.validateToken(token);
            return true;
        } catch (Exception e) {
            log.error("Token is invalid", e);
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        var claims = JWT.decode(token)
                        .getClaims();
        return new UsernamePasswordAuthenticationToken(
                claims.get("username"), null, List.of(new SimpleGrantedAuthority(claims.get("role").asString())));
    }
}
