package com.munsun.calculator.aspects;

import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.dto.utils.RateAndOtherServiceDto;
import com.munsun.calculator.exceptions.ScoringException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Aspect
@Component
public class DefaultScoringProviderAspect {
    @Pointcut("execution(* com.munsun.calculator.services.impl.providers.impl.DefaultScoringProvider.fullScoring(*))")
    public void executionFullScoring() {}

    @Pointcut("execution(public boolean com.munsun.calculator.services.impl.providers.impl.filters.ScoringHardFilter+.check(*))")
    public void executionHardFilter() {}

    @Pointcut("execution(public com.munsun.calculator.dto.utils.RateAndOtherServiceDto com.munsun.calculator.services.impl.providers.impl.filters.ScoringSoftFilter+.check(*))")
    public void executionSoftFilter() {}

    @Before("executionFullScoring() && args(scoringDataDto, rate)")
    public void loggingBeforeScoring(ScoringDataDto scoringDataDto, BigDecimal rate) {
        log.info("Scoring started...");
        log.debug("Scoring data={}, rate={}", scoringDataDto, rate);
    }

    @AfterReturning(pointcut = "executionHardFilter()", returning = "status")
    public void loggingResultScoringHardFilter(JoinPoint point, boolean status) {
        String nameFilter = point.getSignature().getDeclaringType().getSimpleName();
        if(status) {
            log.info("{}: {}", nameFilter, status);
        } else {
            log.warn("{}: {}", nameFilter, status);
        }
    }

    @AfterReturning(pointcut = "executionSoftFilter()", returning = "dto")
    public void loggingResultScoringHardFilter(JoinPoint point, RateAndOtherServiceDto dto) {
        String nameFilter = point.getSignature().getDeclaringType().getSimpleName();
        log.info("{}: change rate={}, other services={}", nameFilter, dto.newRate(), dto.otherService());
    }

    @AfterReturning(
            value = "executionFullScoring() && args(scoringDataDto)",
            returning = "result", argNames = "scoringDataDto,result")
    public void loggingResultScoringProvider(ScoringDataDto scoringDataDto, RateAndOtherServiceDto result) {
        log.info("Result scoring data={} is {}, other service={}", scoringDataDto, result.newRate(), result.otherService());
    }

    @AfterThrowing(
            pointcut="executionFullScoring()",
            throwing="ex")
    public void doRecoveryActions(ScoringException ex) {
        log.error(ex.getMessage());
    }
}
