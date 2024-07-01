package com.munsun.dossier.aspects;

import com.munsun.dossier.queries.payload.EmailMessage;
import com.munsun.dossier.queries.payload.EmailMessageWithCreditDto;
import com.munsun.dossier.queries.payload.EmailMessageWithSesCode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class DossierListenersAspect {
    @Pointcut("execution(public * com.munsun.dossier.queries.listeners.DossierListeners.*(..))")
    private void executionInDossierListenerMethods() {}

    @Before("executionInDossierListenerMethods() && (args(emailMessage))")
    public void loggingListenerEmailMessageMethods(JoinPoint point, EmailMessage emailMessage) {
        log.debug("execution={}, message={}", point.getSignature(), emailMessage);
        log.info("Listen message={}", emailMessage);
    }

    @Before("executionInDossierListenerMethods() && args(emailMessageWithSesCode))")
    public void loggingListenerEmailMessageWithSesCodeMethods(JoinPoint point, EmailMessageWithSesCode emailMessageWithSesCode) {
        log.debug("execution={}, message={}", point.getSignature(), emailMessageWithSesCode);
        log.info("Listen message={}", emailMessageWithSesCode);
    }

    @Before("executionInDossierListenerMethods() && args(emailMessageWithCreditDto))")
    public void loggingListenerEmailMessageMethods(JoinPoint point, EmailMessageWithCreditDto emailMessageWithCreditDto) {
        log.debug("execution={}, message={}", point.getSignature(), emailMessageWithCreditDto);
        log.info("Listen message={}", emailMessageWithCreditDto);
    }
}
