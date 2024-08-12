package com.munsun.deal.aspects;

import com.munsun.deal.dto.request.LoanStatementRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class CalculatorFeignClientAspects {
    @Pointcut("execution(public * com.munsun.deal.services.impl.clients.CalculatorFeignClient+.getLoanOffers(..))")
    public void executionSendRequestGetLoanOffersMethod() {}

    @Before("executionSendRequestGetLoanOffersMethod() && args(loanStatementRequestDto)")
    public void loggingGetLoanOffersMethod(JoinPoint point, LoanStatementRequestDto loanStatementRequestDto) {
        log.info("Send request to calculator-mc: get loan offers...");
        log.debug("Send request={} to calculator-mc: get loan offers...", loanStatementRequestDto);
    }
}
