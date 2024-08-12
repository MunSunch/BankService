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
public class ClientMapperAspects {
    @Pointcut("execution(public * com.munsun.deal.mapping.ClientMapper+.toClient(..))")
    public void executionToClientMethod() {}

    @Before("executionToClientMethod() && args(loanStatement)")
    public void loggingMapToClientMethod(JoinPoint point, LoanStatementRequestDto loanStatement) {
        log.info("Map LoanStatementRequestDto to Client...");
        log.debug("Map LoanStatementRequestDto={} to Client...", loanStatement);
    }
}
