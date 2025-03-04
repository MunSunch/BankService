package com.munsun.deal.config;

import com.munsun.deal.exceptions.PrescoringException;
import com.munsun.deal.exceptions.ScoringException;
import feign.FeignException;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            FeignException e = FeignException.errorStatus(methodKey, response);
            if(e.status() == 500 && e.contentUTF8().contains("scoring")) {
                throw new ScoringException(e.contentUTF8());
            }
            if(e.status() == 400 && e.contentUTF8().contains("prescoring")) {
                throw new PrescoringException(e.contentUTF8());
            }
            return e;
        };
    }
}
