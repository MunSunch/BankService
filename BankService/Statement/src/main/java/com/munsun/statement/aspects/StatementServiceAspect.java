package com.munsun.statement.aspects;

import com.munsun.statement.dto.LoanOfferDto;
import com.munsun.statement.dto.LoanStatementRequestDto;
import com.munsun.statement.exceptions.PrescoringException;
import com.munsun.statement.exceptions.StatementNotFoundException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class StatementServiceAspect {
    @Pointcut("execution(public * com.munsun.statement.services.StatementService+.getLoanOffers(..))")
    public void executionGetLoanOffersMethods() {}

    @Pointcut("execution(public * com.munsun.statement.services.StatementService+.selectLoanOffer(..))")
    public void executionSelectLoanOfferMethods() {}

    @Before("executionGetLoanOffersMethods() && args(loanStatement)")
    public void loggingGetLoanOffersMethods(JoinPoint point, LoanStatementRequestDto loanStatement) {
        log.debug("execution={}, loanStatement={}", point.getSignature(), loanStatement);
        log.info("execution={}...", point.getSignature());
    }

    @Before("executionSelectLoanOfferMethods() && args(loanOfferDto)")
    public void loggingSelectLoanOfferMethods(JoinPoint point, LoanOfferDto loanOfferDto) {
        log.debug("execution={}, loanOfferDto={}", point.getSignature(), loanOfferDto);
        log.info("execution={}...", point.getSignature());
    }

    @AfterReturning(value = "executionSelectLoanOfferMethods()")
    public void loggingResultSelectLoanOfferMethods(JoinPoint point) {
        log.debug("execution={}", point.getSignature());
        log.info("execution={} success", point.getSignature());
    }

    @AfterReturning(value = "executionGetLoanOffersMethods()", returning = "loanOffers")
    public void loggingResultGetLoanOffersMethods(JoinPoint point, List<LoanOfferDto> loanOffers) {
        log.debug("execution={}, loanOffers={}", point.getSignature(), loanOffers);
        log.info("execution={} success", point.getSignature());
    }

    @AfterThrowing(value = "executionGetLoanOffersMethods() && args(loanStatement)", throwing = "e")
    public void createPrescoringException(JoinPoint point, LoanStatementRequestDto loanStatement, ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream()
                .map(v -> v.getMessage())
                .collect(Collectors.joining(";"));
        log.debug("execution={}, prescoring error = {}, loanStatement={}", point.getSignature(),
                message, loanStatement);
        log.warn("Prescoring error = {}", message);
        throw new PrescoringException(message);
    }

    @AfterThrowing(value = "executionGetLoanOffersMethods() || executionSelectLoanOfferMethods()", throwing = "e")
    public void createClientException(FeignException e) {
        log.debug("Error client's: status={}, body={}", e.status(), e.contentUTF8());
        log.warn("Error client's: status={}, body={}", e.status(), e.contentUTF8());
    }

    @AfterThrowing(value = "executionSelectLoanOfferMethods()", throwing = "ex")
    public void loggingStatementNotFoundException(JoinPoint point, StatementNotFoundException ex) {
        log.warn("execution={}, statement not found! statement id={}", point.getSignature(), ex.getMessage());
    }
}
