package com.munsun.deal.aspects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
@ConditionalOnExpression("${app.metrics.enabled}")
public class MetricsAspect {
    private final StatementCounter statementCounter;

    @AfterReturning("execution(* com.munsun.deal.services.DealService+.selectLoanOffer(..))")
    public void setCounterStatementApproved() {
        log.info("update status Statement to Approved");
        statementCounter.incrementStatementApproved();
    }

    @AfterReturning("execution(* com.munsun.deal.services.DealService+.signDocuments(..))")
    public void setCounterStatementCreditIssued() {
        log.info("update status Statement to Credit Issued");
        statementCounter.incrementStatementCreditIssued();
    }
}