package com.munsun.calculator.aspects;

import com.munsun.calculator.dto.request.ScoringDataDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Aspect
@Component
public class CreditCalculatorAspect {
    @Pointcut("execution(public com.munsun.calculator.dto.response.CreditDto com.munsun.calculator.services.impl.providers.CreditCalculator+.calculate(..))")
    public void executionCalculateCredit() {}

    @Around(value = "executionCalculateCredit() && args(scoringDataDto, newRate, otherService)",
            argNames = "point,newRate,scoringDataDto,otherService")
    public Object loggingCalculateCredit(ProceedingJoinPoint point,
                                         BigDecimal newRate,
                                         ScoringDataDto scoringDataDto,
                                         BigDecimal otherService) throws Throwable {
        log.info("Calculate credit for INN={}, amount={}, term={}, rate={}, other service={}",
                scoringDataDto.employment().employerINN(),
                scoringDataDto.amount(),
                scoringDataDto.term(),
                newRate,
                otherService);
        Object object = point.proceed();
        log.debug("Credit has been formed: ".concat(object.toString()));
        log.info("Credit has been formed");
        return object;
    }
}
