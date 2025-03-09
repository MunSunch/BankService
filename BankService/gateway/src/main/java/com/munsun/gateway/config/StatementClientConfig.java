package com.munsun.gateway.config;

import com.munsun.gateway.exceptions.StatementNotFoundException;
import feign.FeignException;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class StatementClientConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            FeignException e = FeignException.errorStatus(methodKey, response);
            if(e.status() == HttpStatus.NOT_FOUND.value() && e.contentUTF8().toLowerCase().contains("statement not found")) {
                throw new StatementNotFoundException(e.contentUTF8());
            }
            throw new RuntimeException(e);
        };
    }
}
