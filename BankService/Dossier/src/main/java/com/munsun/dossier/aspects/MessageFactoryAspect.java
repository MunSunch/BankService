package com.munsun.dossier.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class MessageFactoryAspect {
    @Pointcut("execution(* com.munsun.dossier.services.MessageFactory+.getMessage(..))")
    private void executionGetMessageMethod() {}

    @AfterReturning(value = "executionGetMessageMethod()", returning = "simpleMailMessage")
    public void loggingResultCreateMessage(JoinPoint point, SimpleMailMessage simpleMailMessage) {
        log.debug("execution={}, created message={}", point.getSignature(), simpleMailMessage);
        log.info("Create message={}", simpleMailMessage);
    }
}
