package com.munsun.gateway.aspects;

import com.munsun.gateway.dto.request.LoanStatementRequestDto;
import com.munsun.gateway.dto.response.LoanOfferDto;
import com.munsun.gateway.exceptions.StatementClientException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class StatementClientAspect {
    @Pointcut("execution(* com.munsun.gateway.clients.StatementClient+.getLoanOffers(..))")
    private void executionGetLoanOffersMethod() {}

    @Pointcut("execution(* com.munsun.gateway.clients.StatementClient+.selectOffer(..))")
    private void executionSelectOfferMethod() {}

    @Pointcut("execution(* com.munsun.gateway.clients.StatementClient+.*(..))")
    private void executionAnyMethod() {}

    @Before(value = "executionGetLoanOffersMethod() && args(loanStatement)")
    public void loggingBeforeGetLoanOffersMethod(JoinPoint point, LoanStatementRequestDto loanStatement) {
        log.debug("execution={}, send request to POST /statement body={}", point.getSignature(), loanStatement);
        log.info("send request to POST /statement body={}", loanStatement);
    }

    @AfterThrowing(value = "executionAnyMethod()", throwing = "e")
    public void loggingThrownExceptionAnyMethod(JoinPoint point, FeignException e) throws StatementClientException {
        log.debug("execution={}, thrown feign exception={}", point.getSignature(), e);
        log.info("thrown feign exception={}", point.getSignature(), e);
        int statusCode = e.status();
        String responseBody = e.contentUTF8();
        throw new StatementClientException(statusCode, responseBody);
    }

    @Before(value = "executionSelectOfferMethod() && args(loanOfferDto)")
    public void loggingBeforeSelectOfferMethod(JoinPoint point, LoanOfferDto loanOfferDto) {
        log.debug("execution={}, send request to POST /statement/offer body={}", point.getSignature(), loanOfferDto);
        log.info("send request to POST /statement/offer body={}", loanOfferDto);
    }
}
