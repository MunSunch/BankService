package com.munsun.dossier.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Aspect
@Component
public class DossierFeignClientAspect {
    @Pointcut("execution(* com.munsun.dossier.services.impl.clients.DossierFeignClient+.*(..))")
    private void executionDossierFeignClientMethod() {}

    @Before("executionDossierFeignClientMethod() && args(statementId)")
    public void loggingBeforeUpdateStatus(JoinPoint point, UUID statementId) {
        log.debug("send request for update status statement uuid={}, exection={}", statementId, point.getSignature());
        log.info("send request for update status statement uuid={}", statementId);
    }
}
