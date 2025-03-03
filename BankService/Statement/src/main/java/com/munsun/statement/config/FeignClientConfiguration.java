package com.munsun.statement.config;

import com.munsun.statement.exceptions.StatementNotFoundException;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class FeignClientConfiguration {
    @Bean
    public ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            FeignException exception = FeignException.errorStatus(methodKey, response);
            if (response.status() == HttpStatus.NOT_FOUND.value()) {
                return new StatementNotFoundException(response.reason());
            }
            return exception;
        };
    }
}
