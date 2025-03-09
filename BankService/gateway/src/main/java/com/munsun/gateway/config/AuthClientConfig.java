package com.munsun.gateway.config;

import com.munsun.gateway.exceptions.*;
import feign.FeignException;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class AuthClientConfig {
    @Bean("authClientErrorDecoder")
    public ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            FeignException e = FeignException.errorStatus(methodKey, response);
            if(e.status() == HttpStatus.CONFLICT.value() && e.contentUTF8().contains("UserCreateException")) {
                throw new UserCreateException(e.contentUTF8());
            }
            if(e.status() == HttpStatus.BAD_REQUEST.value() && e.contentUTF8().contains("InvalidCredentialsException")) {
                throw new InvalidCredentialsException(e.contentUTF8());
            }
            if(e.status() == HttpStatus.NOT_FOUND.value() && e.contentUTF8().contains("UserNotFoundException")) {
                throw new UserNotFoundException(e.contentUTF8());
            }
            throw new RuntimeException(e);
        };
    }
}
