package com.munsun.deal.aspects;

import com.munsun.deal.dto.request.FinishRegistrationRequestDto;
import com.munsun.deal.dto.request.LoanStatementRequestDto;
import com.munsun.deal.dto.response.LoanOfferDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Slf4j
@Aspect
@Component
public class DealControllerAspect {
    @Pointcut("execution(public * com.munsun.deal.controllers.DealRestController.calculateLoanOffers(..))")
    public void executionGetLoanOffers() {}

    @Pointcut("execution(public * com.munsun.deal.controllers.DealRestController.selectLoanOffer(..))")
    public void executionSelectLoanOffer() {}

    @Pointcut("execution(public * com.munsun.deal.controllers.DealRestController.calculateCredit(..))")
    public void executionCalculateCredit() {}

    @Pointcut("execution(public * com.munsun.deal.controllers.DealRestController.createDocument(..))")
    public void executionCreateDocument() {}

    @Pointcut("execution(public * com.munsun.deal.controllers.DealRestController.sendCodeDocument(..))")
    public void executionSendCodeDocument() {}

    @Pointcut("execution(public * com.munsun.deal.controllers.DealRestController.signCodeDocument(..))")
    public void executionSignCodeDocument() {}

    @Pointcut("execution(public * com.munsun.deal.controllers.DealRestController.updateStatusDocument())")
    public void executionUpdateStatusStatement() {}

    @Before("executionCreateDocument() && args(statementId)")
    public void loggingCreateDocumentEndpoint(JoinPoint point, UUID statementId) {
        log.debug("execution={}, Request: POST /document/{}/send", point.getSignature(), statementId);
        log.info("Request: POST /document/{}/send", statementId);
    }

    @Before("executionSendCodeDocument() && args(statementId)")
    public void loggingSendCodeDocumentEndpoint(JoinPoint point, UUID statementId) {
        log.debug("execution={}, Request: POST /document/{}/sign", point.getSignature(), statementId);
        log.info("Request: POST /document/{}/sign", statementId);
    }

    @Before("executionSignCodeDocument() && args(statementId, sesCode)")
    public void loggingSignCodeDocumentEndpoint(JoinPoint point, UUID statementId, String sesCode) {
        log.debug("execution={}, POST /document/{}/code?sesCode={}", point.getSignature(), statementId, sesCode);
        log.info("POST /document/{}/code?sesCode={}", statementId, sesCode);
    }

    @Before("executionUpdateStatusStatement() && args(statementId)")
    public void loggingUpdateStatementEndpoint(JoinPoint point, UUID statementId) {
        log.debug("execution={}, PUT /admin/statement/{}/status", point.getSignature(), statementId);
        log.info("PUT /admin/statement/{}/status", statementId);
    }

    @Around("executionGetLoanOffers() && args(loanStatement)")
    public Object loggingGetLoanOfferEndpoint(ProceedingJoinPoint point, LoanStatementRequestDto loanStatement) throws Throwable {
        log.info("Request: POST /v1/deal/statement");
        log.debug("Request: POST /v1/deal/statement body={}", loanStatement);
        Object object = point.proceed();
        log.debug("Response: POST /v1/deal/statement body={}", object);
        log.info("Response: POST /v1/deal/statement");
        return object;
    }

    @Around("executionSelectLoanOffer() && args(loanOffer)")
    public Object loggingSelectLoanOfferEndpoint(ProceedingJoinPoint point, LoanOfferDto loanOffer) throws Throwable {
        log.info("Request: POST /v1/deal/offer/select");
        log.debug("Request: POST /v1/deal/offer/select body={}", loanOffer);
        Object object = point.proceed();
        log.debug("Response: POST /v1/deal/offer/select body={}", object);
        log.info("Response: POST /v1/deal/offer/select");
        return object;
    }

    @Around(value = "executionCalculateCredit() && args(finishRegistration, statementId)",
            argNames = "point,finishRegistration,statementId")
    public Object loggingCalculateCreditEndpoint(ProceedingJoinPoint point, FinishRegistrationRequestDto finishRegistration, String statementId) throws Throwable {
        log.info("Request: POST /v1/deal/calculate/{}", statementId);
        log.debug("Request: POST /v1/deal/calculate/{} body={}", statementId, finishRegistration);
        Object object = point.proceed();
        log.debug("Response: POST /v1/deal/calculate/{} body={}", statementId, object);
        log.info("Response: POST /v1/deal/calculate/{}", statementId);
        return object;
    }
}