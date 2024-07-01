package com.munsun.dossier.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class DossierEmailSenderAspect {
    @Pointcut("execution(* com.munsun.dossier.services.DossierEmailSender+.send(..))")
    public void executionSendMethod(){}

    @Before("executionSendMethod() && args(message)")
    public void loggingSendMethod(JoinPoint point, SimpleMailMessage message) {
        log.debug("execution {}, message={}", point.getSignature(), message);
        log.info("Send email message={}", message);
    }
}
