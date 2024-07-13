package com.munsun.deal.aspects;

import com.munsun.deal.dto.request.FinishRegistrationRequestDto;
import com.munsun.deal.dto.request.LoanStatementRequestDto;
import com.munsun.deal.dto.response.LoanOfferDto;
import com.munsun.deal.models.Statement;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Aspect
@Component
public class DealServiceAspect {
    @Pointcut("execution(public * com.munsun.deal.services.DealService+.calculateCredit(..))")
    private void executionCalculateCreditMethod() {}

    @Pointcut("execution(public * com.munsun.deal.services.DealService+.selectLoanOffer(..))")
    private void executionSelectLoanOffer() {}

    @Pointcut("execution(public * com.munsun.deal.services.DealService+.getLoanOffers(..))")
    private void executionGetLoanOffers() {}

    @Pointcut("execution(public * com.munsun.deal.services.DealService+.prepareDocuments(..))")
    private void executionPrepareDocuments() {}

    @Pointcut("execution(public * com.munsun.deal.services.DealService+.createSignCodeDocuments(..))")
    private void executionCreateSignCodeDocuments() {}

    @Pointcut("execution(public * com.munsun.deal.services.DealService+.signDocuments(..))")
    private void executionSignDocument() {}

    @Pointcut("execution(public * com.munsun.deal.services.DealService+.updateStatus(..))")
    private void executionUpdateStatusDocument() {}

    @Pointcut("execution(public * com.munsun.deal.services.DealService+.getStatement(..))")
    private void executionGetStatement() {}

    @Before("executionGetStatement() && args(statementId)")
    public void loggingBeforeGetStatementMethod(JoinPoint point, UUID statementId) {
        log.info("Get statement uuid={}", statementId);
        log.debug("execution={}, get statement uuid={}", point.getSignature(), statementId);
    }

    @AfterThrowing(value = "executionGetStatement() && args(statementId)", throwing = "e")
    public void loggingThrowGetStatementMethod(JoinPoint point, UUID statementId, Exception e) {
        log.info("Thrown exception={}, statement uuid={}", e, statementId);
        log.debug("execution={}, Thrown exception={}, statement uuid={}", point.getSignature(), e, statementId);
    }

    @AfterReturning(value = "executionGetStatement() && args(statementId)", returning = "statement", argNames = "point,statementId,statement")
    public void loggingResultGetStatementMethod(JoinPoint point, UUID statementId, Statement statement) {
        log.info("Return statement={} by uuid={}", statement, statementId);
        log.debug("execution={}, Return statement={} by uuid={}", point.getSignature(), statementId, statement);
    }

    @After("executionUpdateStatusDocument() && args(statementId)")
    public void loggingUpdateStatusDocumentMethod(JoinPoint point, UUID statementId) {
        log.info("Update status statement uuid={}", statementId);
        log.debug("execution={}, update status statement uuid={}", point.getSignature(), statementId);
    }

    @After("executionSignDocument() && args(statementId, sesCode)")
    public void loggingSignDocumentMethod(JoinPoint point, UUID statementId, String sesCode) {
        log.info("Documents are signed ses code={}, statement uuid={}", sesCode, statementId);
        log.debug("execution={}, documents are signed ses code={}, statement uuid={}", point.getSignature(), sesCode, statementId);
    }

    @After("executionCreateSignCodeDocuments() && args(statementId)")
    public void loggingCreateSignCodeDocumentMethod(JoinPoint point, UUID statementId) {
        log.info("Documents have been created and sent, statement uuid={}", statementId);
        log.debug("execution={}, documents have been successfully created and sent, statement uuid={}", point.getSignature(), statementId);
    }

    @After("executionPrepareDocuments() && args(statementId)")
    public void loggingPrepareDocumentMethod(JoinPoint point, UUID statementId) {
        log.info("Documents are being created, statement uuid={}", statementId);
        log.debug("execution={}, documents are being created, statement uuid={}", point.getSignature(), statementId);
    }

    @Before("executionCalculateCreditMethod() && args(statementId, finishRegistration)")
    public void loggingCalculateCreditMethod(JoinPoint point, String statementId, FinishRegistrationRequestDto finishRegistration) {
        log.info("Calculate credit...");
        log.debug("Calculate credit statement id={}, finishRegistration={}", statementId, finishRegistration);
    }

    @Before("executionSelectLoanOffer() && args(loanOffer)")
    public void loggingSelectLoanOfferMethod(JoinPoint point, LoanOfferDto loanOffer) {
        log.info("Select loan offer id = {}", loanOffer.statementId());
        log.debug("Select loan offer={}", loanOffer);
    }

    @Around("executionGetLoanOffers() && args(loanStatement)")
    public Object loggingGetLoanOffersMethod(ProceedingJoinPoint point, LoanStatementRequestDto loanStatement) throws Throwable {
        log.info("Get loan offers...");
        log.debug("Get loan offers, loanStatement = {}", loanStatement);
        Object result = point.proceed();
        log.info("Get loan offers success");
        log.debug("Get loan offers success, offers = {}", result);
        return result;
    }
}
