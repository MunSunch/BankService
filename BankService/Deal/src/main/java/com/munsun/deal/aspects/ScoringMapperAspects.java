package com.munsun.deal.aspects;

import com.munsun.deal.dto.request.FinishRegistrationRequestDto;
import com.munsun.deal.dto.response.CreditDto;
import com.munsun.deal.models.Statement;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ScoringMapperAspects {
    @Pointcut("execution(public * com.munsun.deal.mapping.ScoringMapper+.toScoringDataDto(..))")
    public void executionToScoringDataDtoMethod() {}

    @Before("executionToScoringDataDtoMethod() && args(statement, finishRegistration)")
    public void loggingMapToScoringDataDtoMethod(JoinPoint point, Statement statement, FinishRegistrationRequestDto finishRegistration) {
        log.info("Map Statement entity and FinishRegistrationDto to ScoringDataDto...");
        log.debug("Map Statement entity={} and FinishRegistrationDto={} to ScoringDataDto...", statement, finishRegistration);
    }
}
