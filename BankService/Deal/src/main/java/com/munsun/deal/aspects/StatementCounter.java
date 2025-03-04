package com.munsun.deal.aspects;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnExpression("${app.metrics.enabled}")
public class StatementCounter {
    private final Counter counterStatementApproved;
    private final Counter counterStatementCreditIssued;

    @Autowired
    public StatementCounter(MeterRegistry meterRegistry) {
        this.counterStatementApproved = meterRegistry.counter("count_statements_status_approved");
        this.counterStatementCreditIssued = meterRegistry.counter("count_statements_status_credit_issued");
    }

    public void incrementStatementApproved() {
        counterStatementApproved.increment();
    }

    public void incrementStatementCreditIssued() {
        counterStatementCreditIssued.increment();
    }
}
