package com.munsun.calculator.aspects;

import com.munsun.calculator.dto.request.LoanStatementRequestDto;
import com.munsun.calculator.dto.request.ScoringDataDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class CalculatorControllerAspect {
    @Pointcut("execution(public * com.munsun.calculator.controllers.CalculatorController.fullCalculateLoanParametersAndScoring(*))")
    public void executeFullCalculateEndpoint() {};

    @Pointcut("execution(public * com.munsun.calculator.controllers.CalculatorController.calculatePossibleLoanTerms(*))")
    public void executeCalculatePossibleLoanEndpoint() {};

    @Around("executeFullCalculateEndpoint() && args(scoringDataDto)")
    public Object speedMeasurementProcess(ProceedingJoinPoint point, ScoringDataDto scoringDataDto) throws Throwable {
        log.info("Request: POST /calc; body={}", scoringDataDto);
        long start = System.currentTimeMillis();
        Object object = point.proceed();
        long end = System.currentTimeMillis();
        log.debug("measuring the processing speed: {} ms.", end-start);
        log.info("Response: POST /calc; body={}", object);
        return object;
    }

    @Around("executeCalculatePossibleLoanEndpoint() && args(loanStatementRequestDto)")
    public Object loggingCalculatePossibleLoanEndpoint(ProceedingJoinPoint point, LoanStatementRequestDto loanStatementRequestDto) throws Throwable {
        log.info("Request: POST /offers");
        log.debug("Request: POST /offers; body={}", loanStatementRequestDto);
        Object object = point.proceed();
        log.debug("Response: POST /offers; body={}", object);
        log.info("Response: POST /offers");
        return object;
    }
}
