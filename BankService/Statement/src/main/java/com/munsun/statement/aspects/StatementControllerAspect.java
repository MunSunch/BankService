package com.munsun.statement.aspects;

import com.munsun.statement.dto.LoanOfferDto;
import com.munsun.statement.dto.LoanStatementRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Aspect
@Component
public class StatementControllerAspect {
    @Pointcut("execution(public * com.munsun.statement.controllers.StatementController.getLoanOffers(..))")
    public void executionGetLoanOffersEndpoint() {}

    @Pointcut("execution(public * com.munsun.statement.controllers.StatementController.selectLoanOffer(..))")
    public void executionSelectLoanOfferEndpoint() {}

    @Before("executionGetLoanOffersEndpoint() && args(loanStatement)")
    public void loggingGetLoanOffersEndpoint(JoinPoint point, LoanStatementRequestDto loanStatement) throws Throwable {
        log.debug("execution {}, request: POST /v1/statement body={}", point.getSignature(), loanStatement);
        log.info("Request: POST /v1/statement");
    }

    @Before("executionSelectLoanOfferEndpoint() && args(loanOfferDto)")
    public void loggingSelectLoanOfferEndpoint(JoinPoint point, LoanOfferDto loanOfferDto) throws Throwable {
        log.debug("execution {}, request: POST /v1/statement/offer body={}", point.getSignature(), loanOfferDto);
        log.info("Request: POST /v1/statement/offer");
    }

    @AfterReturning(value = "executionGetLoanOffersEndpoint()", returning = "loanOfferDto")
    public void loggingResultExecuteGetLoanOffersEndpoint(JoinPoint point, List<LoanOfferDto> loanOfferDto) {
        log.debug("execution {}, response: POST /v1/statement body={}", point.getSignature(), loanOfferDto);
        log.info("Response: POST /v1/statement");
    }

    @AfterReturning(value = "executionSelectLoanOfferEndpoint()", returning = "loanOfferDto")
    public void loggingResultExecuteSelectLoanOfferEndpoint(JoinPoint point, List<LoanOfferDto> loanOfferDto) {
        log.debug("execution {}, response: POST /v1/statement/offer body={}", point.getSignature(), loanOfferDto);
        log.info("Response: POST /v1/statement/offer");
    }

    @AfterThrowing(value = "executionSelectLoanOfferEndpoint() || executionGetLoanOffersEndpoint()",
            throwing = "ex")
    public void loggingThrowingException(JoinPoint point, Exception ex) {
        log.warn("execution {}, throwing exception={}", point.getSignature(), ex.getMessage());
    }
}
