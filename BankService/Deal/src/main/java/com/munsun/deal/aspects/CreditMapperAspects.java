package com.munsun.deal.aspects;

import com.munsun.deal.dto.response.CreditDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class CreditMapperAspects {
    @Pointcut("execution(public * com.munsun.deal.mapping.CreditMapper+.toCredit(..))")
    public void executionToCreditMethod() {}

    @Before("executionToCreditMethod() && args(creditDto)")
    public void loggingMapToCreditMethod(JoinPoint point, CreditDto creditDto) {
        log.info("Map CreditDto to Credit entity...");
        log.debug("Map CreditDto={} to Credit entity...", creditDto);
    }
}
