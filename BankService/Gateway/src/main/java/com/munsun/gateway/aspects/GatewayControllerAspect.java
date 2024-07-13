package com.munsun.gateway.aspects;

import com.munsun.gateway.dto.request.FinishRegistrationRequestDto;
import com.munsun.gateway.dto.request.LoanStatementRequestDto;
import com.munsun.gateway.dto.response.LoanOfferDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Aspect
@Component
public class GatewayControllerAspect {
    @Pointcut("execution(* com.munsun.gateway.controllers.GatewayController.createLoanOfferStatement(..))")
    private void createLoanOfferStatementMethod() {}

    @Pointcut("execution(* com.munsun.gateway.controllers.GatewayController.selectLoanOffer(..))")
    private void selectLoanOfferMethod() {}

    @Pointcut("execution(* com.munsun.gateway.controllers.GatewayController.finishRegistration(..))")
    private void finishRegistrationMethod() {}

    @Pointcut("execution(* com.munsun.gateway.controllers.GatewayController.createDocument(..))")
    private void createDocumentMethod() {}

    @Pointcut("execution(* com.munsun.gateway.controllers.GatewayController.sendDocument(..))")
    private void sendDocumentMethod() {}

    @Pointcut("execution(* com.munsun.gateway.controllers.GatewayController.confirmCodeLoanOffer(..))")
    private void confirmCodeLoanOfferMethod() {}

    @Before(value = "sendDocumentMethod() && args(statementId, confirmCode)", argNames = "point,statementId,confirmCode")
    public void loggingBeforeConfirmCodeLoanOfferMethod(JoinPoint point, String statementId, String confirmCode) {
        log.info("execution={}, Request: POST /document/{}/code?confirmCode={}", point.getSignature(), statementId, confirmCode);
        log.info("Request: POST /document/{}/code?confirmCode={}", statementId, confirmCode);
    }

    @After(value = "sendDocumentMethod() && args(statementId, confirmCode)", argNames = "point,statementId,confirmCode")
    public void loggingAfterConfirmCodeLoanOfferMethod(JoinPoint point, String statementId, String confirmCode) {
        log.info("execution={}, Response: POST /document/{}/code?confirmCode={}", point.getSignature(), statementId, confirmCode);
        log.info("Response: POST /document/{}/code?confirmCode={}", statementId, confirmCode);
    }

    @Before(value = "sendDocumentMethod() && args(statementId)")
    public void loggingBeforeSendDocumentMethod(JoinPoint point, String statementId) {
        log.info("execution={}, Request: POST /document/{}/sign", point.getSignature(), statementId);
        log.info("Request: POST /document/{}/sign", statementId);
    }

    @After(value = "sendDocumentMethod() && args(statementId)")
    public void loggingAfterSendDocumentMethod(JoinPoint point, String statementId) {
        log.info("execution={}, Response: POST /document/{}/sign", point.getSignature(), statementId);
        log.info("Response: POST /document/{}/sign", statementId);
    }

    @Before(value = "createDocumentMethod() && args(statementId)")
    public void loggingBeforeCreateDocumentMethod(JoinPoint point, String statementId) {
        log.info("execution={}, Request: POST /document/{}", point.getSignature(), statementId);
        log.info("Request: POST /document/{}", statementId);
    }

    @After(value = "createDocumentMethod() && args(statementId)")
    public void loggingAfterCreateDocumentMethod(JoinPoint point, String statementId) {
        log.info("execution={}, Response: POST /document/{}", point.getSignature(), statementId);
        log.info("Response: POST /document/{}", statementId);
    }

    @Before(value = "finishRegistrationMethod() && args(statementId, finishRegistration)", argNames = "point,statementId,finishRegistration")
    public void loggingBeforeFinishRegistrationMethod(JoinPoint point, String statementId, FinishRegistrationRequestDto finishRegistration) {
        log.info("execution={}, Request: POST /statement/registration/{} body={}", point.getSignature(), statementId, finishRegistration);
        log.info("Request: POST /statement/registration/{} body={}", statementId, finishRegistration);
    }

    @After(value = "finishRegistrationMethod()")
    public void loggingAfterFinishRegistrationMethod(JoinPoint point) {
        log.info("execution={}, Response: POST /api/statement", point.getSignature());
        log.info("Response: POST /api/statement");
    }

    @Before("selectLoanOfferMethod() && args(loanOfferDto))")
    public void loggingBeforeSelectLoanOfferMethod(JoinPoint point, String loanOfferDto) {
        log.info("execution={}, Request: POST /statement/select body={}", point.getSignature(), loanOfferDto);
        log.info("Request: POST /statement/select body={}", loanOfferDto);
    }

    @After(value = "selectLoanOfferMethod()")
    public void loggingAfterSelectLoanOfferMethod(JoinPoint point) {
        log.info("execution={}, Response: POST /api/statement", point.getSignature());
        log.info("Response: POST /api/statement");
    }

    @Before("createLoanOfferStatementMethod() && args(loanStatement))")
    public void loggingBeforeCreateLoanOfferStatementMethod(JoinPoint point, LoanStatementRequestDto loanStatement) {
        log.info("execution={}, Request: POST /api/statement body={}", point.getSignature(), loanStatement);
        log.info("Request: POST /api/statement body={}", loanStatement);
    }

    @AfterReturning(value = "createLoanOfferStatementMethod()", returning = "listLoanOffers")
    public void loggingResultCreateLoanOfferStatementMethod(JoinPoint point, List<LoanOfferDto> listLoanOffers) {
        log.info("execution={}, Response: POST /api/statement body={}", point.getSignature(), listLoanOffers);
        log.info("Response: POST /api/statement body={}", listLoanOffers);
    }
}
