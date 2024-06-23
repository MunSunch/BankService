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

@Slf4j
@Aspect
@Component
public class DealServiceAspect {
    @Pointcut("execution(public * com.munsun.deal.services.DealService+.calculateCredit(..))")
    private void executionCalculateCreditMethod() {}

    @Pointcut("execution(public * com.munsun.deal.services.DealService+.selectLoanOffer(..))")
    private void executionSelectLoanOffer() {}

    @Pointcut("execution(public * com.munsun.deal.services.DealService+.getLoanOffers(..))")
    private void executionGetLoanOffers() {}

    @Before("executionCalculateCreditMethod() && args(statementId, finishRegistration)")
    public void loggingCalculateCreditMethod(JoinPoint point, String statementId, FinishRegistrationRequestDto finishRegistration) {
        log.info("Calculate credit...");
        log.debug("Calculate credit statement id={}, finishRegistration={}", statementId, finishRegistration);
    }

    @Before("executionSelectLoanOffer() && args(loanOffer)")
    public void loggingSelectLoanOfferMethod(JoinPoint point, LoanOfferDto loanOffer) {
        log.info("Select loan offer id = {}", loanOffer.statementId());
        log.debug("Select loan offer={}", loanOffer);
    }

    @Around("executionGetLoanOffers() && args(loanStatement)")
    public Object loggingGetLoanOffersMethod(ProceedingJoinPoint point, LoanStatementRequestDto loanStatement) throws Throwable {
        log.info("Get loan offers...");
        log.debug("Get loan offers, loanStatement = {}", loanStatement);
        Object result = point.proceed();
        log.info("Get loan offers success");
        log.debug("Get loan offers success, offers = {}", result);
        return result;
    }
}
