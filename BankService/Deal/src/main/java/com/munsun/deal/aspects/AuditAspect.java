package com.munsun.deal.aspects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.munsun.deal.aspects.annotations.AuditAction;
import com.munsun.deal.integrations.amqp.kafka.payload.AuditActionPayload;
import com.munsun.deal.integrations.amqp.kafka.payload.enums.OperationType;
import com.munsun.deal.integrations.amqp.kafka.payload.enums.ServiceType;
import com.munsun.deal.integrations.amqp.kafka.producers.AuditProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {
    private final AuditProducer auditProducer;
    private final ObjectMapper objectMapper;

    @Pointcut("@annotation(com.munsun.deal.aspects.annotations.AuditAction)")
    private void methodWithAuditActionAnnotation(){}

    @Around("methodWithAuditActionAnnotation() & args()")
    public Object auditMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        log.info("Send audit, started, method name={}", methodName);
        auditProducer.sendAudit(new AuditActionPayload(OperationType.START, ServiceType.DEAL, objectMapper.writeValueAsString(joinPoint.getArgs())));
        try {
            var resultCallMethod = joinPoint.proceed();
            log.info("Send audit, success, method name={}, result={}", methodName, resultCallMethod);
            auditProducer.sendAudit(new AuditActionPayload(OperationType.SUCCESS, ServiceType.DEAL, objectMapper.writeValueAsString(joinPoint.getArgs())));
            return resultCallMethod;
        } catch (Throwable throwable) {
            log.error("Send audit, failed, method name={} error={}", methodName, throwable.getMessage());
            auditProducer.sendAudit(new AuditActionPayload(OperationType.FAILURE, ServiceType.DEAL, objectMapper.writeValueAsString(joinPoint.getArgs())));
            throw throwable;
        }
    }
}
