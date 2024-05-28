package com.munsun.calculator.aspects;

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

    @Around("executionCalculateCredit()")
    public Object loggingCalculateCredit(ProceedingJoinPoint point) throws Throwable {
        log.info("Formation of loan");
        Object object = point.proceed();
        log.info("Loan has been formed");
        return object;
    }
}
