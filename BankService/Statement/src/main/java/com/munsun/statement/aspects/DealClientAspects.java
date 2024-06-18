package com.munsun.statement.aspects;

import com.munsun.statement.dto.LoanOfferDto;
import com.munsun.statement.dto.LoanStatementRequestDto;
import com.munsun.statement.exceptions.StatementNotFoundException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class DealClientAspects {
    @Pointcut("execution(public * com.munsun.statement.clients.DealClient+.selectLoanOffer(..))")
    public void executionSelectLoanOfferMethod() {}

    @Pointcut("execution(public * com.munsun.statement.clients.DealClient+.getLoanOffers(..))")
    public void executionGetLoanOffersMethod() {}

    @Before("executionGetLoanOffersMethod() && args(loanStatement)")
    public void loggingRequestGetLoanOffersToDealMC(JoinPoint point, LoanStatementRequestDto loanStatement) {
        log.debug("execution={}, loanOfferDto={}", point.getSignature(), loanStatement);
        log.info("send request to deal-mc for get possible loan offers loanStatement={}", loanStatement);
    }

    @Before("executionSelectLoanOfferMethod() && args(loanOfferDto)")
    public void loggingRequestSelectLoanOfferToDealMC(JoinPoint point, LoanOfferDto loanOfferDto) {
        log.debug("execution={}, loanOfferDto={}", point.getSignature(), loanOfferDto);
        log.info("send request to deal-mc for select loan offer={}", loanOfferDto);
    }

    @After("executionSelectLoanOfferMethod()")
    public void loggingResponseSelectLoanOfferToDealMC(JoinPoint point) {
        log.debug("execution {}, get response to deal-mc for select loan offer...success", point.getSignature());
        log.info("get response to deal-mc for select loan offer...success");
    }

    @AfterThrowing(value = "executionSelectLoanOfferMethod() && args(loanOfferDto)", throwing = "e")
    public void createStatementNotFoundException(LoanOfferDto loanOfferDto, FeignException e) {
        if(e.status()== HttpStatus.NOT_FOUND.value()) {
            log.warn("Statement not found! Statement id = {}", loanOfferDto.statementId());
            throw new StatementNotFoundException(loanOfferDto.statementId());
        }
    }
}
