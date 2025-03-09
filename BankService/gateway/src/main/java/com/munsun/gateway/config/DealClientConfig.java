package com.munsun.gateway.config;

import com.munsun.gateway.exceptions.InvalidSesCode;
import com.munsun.gateway.exceptions.PrescoringException;
import com.munsun.gateway.exceptions.ScoringException;
import com.munsun.gateway.exceptions.StatementNotFoundException;
import feign.FeignException;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import javax.script.ScriptException;

@Configuration
public class DealClientConfig {
    @Bean("dealClientErrorDecoder")
    public ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            FeignException e = FeignException.errorStatus(methodKey, response);
            if(e.status() == HttpStatus.NOT_FOUND.value() && e.contentUTF8().toLowerCase().contains("statement not found")) {
                throw new StatementNotFoundException(e.contentUTF8());
            }
            if(e.status()==HttpStatus.BAD_REQUEST.value() && e.contentUTF8().toLowerCase().contains("prescoring")) {
                throw new PrescoringException(e.contentUTF8());
            }
            if(e.status() == HttpStatus.INTERNAL_SERVER_ERROR.value() && e.contentUTF8().contains("scoring")) {
                throw new ScoringException(e.contentUTF8());
            }
            if(e.status() == HttpStatus.OK.value() && e.contentUTF8().contains("ses code")) {
                throw new InvalidSesCode(e.contentUTF8());
            }
            throw new RuntimeException(e);
        };
    }
}
