package com.munsun.dossier.aspects;

import com.munsun.dossier.kafka.payload.EmailMessageWithCreditDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.activation.DataSource;

@Slf4j
@Aspect
@Component
public class DocumentGeneratorAspect {
    @Pointcut("execution(* com.munsun.dossier.services.impl.providers.DocumentGenerator+.*(..))")
    private void executionDocumentGeneratorMethod() {}

    @Before("executionDocumentGeneratorMethod() && args(emailMessage)")
    public void loggingBeforeGenerateDocument(JoinPoint point, EmailMessageWithCreditDto emailMessage) {
        log.debug("generate document, execution={}, emailMessage={}", point.getSignature(), emailMessage);
        log.info("generate document, emailMessage={} ...", emailMessage);
    }

    @AfterReturning(value = "executionDocumentGeneratorMethod() && args(emailMessage)", returning = "dataSource", argNames = "point,dataSource,emailMessage")
    public void loggingResultGenerateDocument(JoinPoint point, DataSource dataSource, EmailMessageWithCreditDto emailMessage) {
        log.debug("success generate document, execution={}, emailMessage={}", point.getSignature(), emailMessage);
        log.info("success generate document, emailMessage={}, content={}", emailMessage, dataSource.getContentType());
    }
}
