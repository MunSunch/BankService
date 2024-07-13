package com.munsun.gateway.aspects;

import com.munsun.gateway.dto.request.FinishRegistrationRequestDto;
import com.munsun.gateway.exceptions.DealClientException;
import com.munsun.gateway.exceptions.StatementClientException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class DealClientAspect {
    @Pointcut("execution(* com.munsun.gateway.clients.DealClient+.finishRegistration(..))")
    private void executionFinishRegistrationMethod() {}

    @Pointcut("execution(* com.munsun.gateway.clients.DealClient+.createDocument(..))")
    private void executionCreateDocumentMethod() {}

    @Pointcut("execution(* com.munsun.gateway.clients.DealClient+.sendDocument(..))")
    private void executionSendDocumentMethod() {}

    @Pointcut("execution(* com.munsun.gateway.clients.DealClient+.confirmLoanOffer(..))")
    private void executionConfirmLoanOfferMethod() {}

    @Pointcut("execution(* com.munsun.gateway.clients.DealClient+.*(..))")
    private void executionAnyMethod() {}

    @Before(value = "executionFinishRegistrationMethod() && args(statementId, finishRegistration)", argNames = "point,statementId,finishRegistration")
    public void loggingBeforeFinishRegistrationMethod(JoinPoint point, String statementId, FinishRegistrationRequestDto finishRegistration) {
        log.debug("execution={}, send request to POST /calculate/{} body={}", point.getSignature(),
                statementId, finishRegistration);
        log.info("send request to POST /calculate/{} body={}", statementId, finishRegistration);
    }

    @Before(value = "executionCreateDocumentMethod() && args(statementId)")
    public void loggingBeforeCreateDocumentMethod(JoinPoint point, String statementId) {
        log.debug("execution={}, send request to POST /document/{}/send", point.getSignature(), statementId);
        log.info("send request to POST /document/{}/send", statementId);
    }

    @Before(value = "executionSendDocumentMethod() && args(statementId)")
    public void loggingBeforeSendDocumentMethod(JoinPoint point, String statementId) {
        log.debug("execution={}, send request to POST /document/{}/sign", point.getSignature(), statementId);
        log.info("send request to POST /document/{}/sign", statementId);
    }

    @Before(value = "executionConfirmLoanOfferMethod() && args(statementId, confirmCode)")
    public void loggingBeforeSendDocumentMethod(JoinPoint point, String statementId, String confirmCode) {
        log.debug("execution={}, send request to POST /document/{}/code?confirmCode={}", point.getSignature(), statementId, confirmCode);
        log.info("send request to POST /document/{}/code?confirmCode={}", statementId, confirmCode);
    }

    @AfterThrowing(value = "executionAnyMethod()", throwing = "e")
    public void loggingThrownExceptionAnyMethod(JoinPoint point, FeignException e) throws DealClientException {
        log.debug("execution={}, thrown feign exception={}", point.getSignature(), e);
        log.info("thrown feign exception={}", point.getSignature(), e);
        int statusCode = e.status();
        String responseBody = e.contentUTF8();
        throw new DealClientException(statusCode, responseBody);
    }
}
