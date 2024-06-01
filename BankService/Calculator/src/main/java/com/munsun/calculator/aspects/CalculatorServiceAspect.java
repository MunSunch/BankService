package com.munsun.calculator.aspects;

import com.munsun.calculator.dto.request.ScoringDataDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class CalculatorServiceAspect {
    @Pointcut("execution(* com.munsun.calculator.services.CalculatorService+.calculateCredit(*))")
    public void executionCalculateCredit() {}

    @Pointcut("execution(* com.munsun.calculator.services.CalculatorService+.calculateLoan(*))")
    public void executionCalculateLoan() {}

    @Around("executionCalculateCredit() && args(scoringDataDto)")
    public Object loggingCalculateCredit(ProceedingJoinPoint point, ScoringDataDto scoringDataDto) throws Throwable {
        log.info("Formation of loan");
        log.debug("Formation of loan, scoringDataDto={}", scoringDataDto);
        Object object = point.proceed();
        log.info("Loan has been formed");
        log.debug("Loan has been formed: {}", object);
        return object;
    }

    @Around("executionCalculateLoan()")
    public Object loggingCalculateLoan(ProceedingJoinPoint point) throws Throwable {
        log.info("Formation of loans");
        Object object = point.proceed();
        log.info("Loans has been formed");
        log.debug("Loans has been formed: {}", object);
        return object;
    }
}
