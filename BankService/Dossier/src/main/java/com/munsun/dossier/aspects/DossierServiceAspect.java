package com.munsun.dossier.aspects;

import com.munsun.dossier.kafka.payload.EmailMessage;
import com.munsun.dossier.kafka.payload.EmailMessageWithCreditDto;
import com.munsun.dossier.kafka.payload.EmailMessageWithSesCode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class DossierServiceAspect {
    @Pointcut("execution(* com.munsun.dossier.services.DossierService+.*(..))")
    private void executionDossierServiceMethod() {}

    @Before("executionDossierServiceMethod() && args(message)")
    public void loggingSendMessageEmail(JoinPoint point, EmailMessage message) {
        log.debug("execution={}, send message to email={}", point.getSignature(), message);
        log.info("send message to email={}", message);
    }

    @Before("executionDossierServiceMethod() && args(message)")
    public void loggingSendMessageEmail(JoinPoint point, EmailMessageWithCreditDto message) {
        log.debug("execution={}, send message to email={}", point.getSignature(), message);
        log.info("send message to email={}", message);
    }

    @Before("executionDossierServiceMethod() && args(message)")
    public void loggingSendMessageEmail(JoinPoint point, EmailMessageWithSesCode message) {
        log.debug("execution={}, send message to email={}", point.getSignature(), message);
        log.info("send message to email={}", message);
    }
}
