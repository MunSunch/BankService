package com.munsun.deal.aspects;

import com.munsun.deal.dto.request.FinishRegistrationRequestDto;
import com.munsun.deal.dto.request.LoanStatementRequestDto;
import com.munsun.deal.dto.response.LoanOfferDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class DealControllerAspect {
    @Pointcut("execution(public * com.munsun.deal.controllers.DealRestController.calculateLoanOffers(..))")
    public void executionGetLoanOffers() {}

    @Pointcut("execution(public * com.munsun.deal.controllers.DealRestController.selectLoanOffer(..))")
    public void executionSelectLoanOffer() {}

    @Pointcut("execution(public * com.munsun.deal.controllers.DealRestController.calculateLoanOffers(..))")
    public void executionCalculateCredit() {}

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
    public Object loggingSelectLoanOfferEndpoint(ProceedingJoinPoint point, FinishRegistrationRequestDto finishRegistration, String statementId) throws Throwable {
        log.info("Request: POST /v1/deal/calculate/{}", statementId);
        log.debug("Request: POST /v1/deal/calculate/{} body={}", statementId, finishRegistration);
        Object object = point.proceed();
        log.debug("Response: POST /v1/deal/calculate/{} body={}", statementId, object);
        log.info("Response: POST /v1/deal/calculate/{}", statementId);
        return object;
    }
}