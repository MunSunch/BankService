package com.munsun.gateway.controllers;

import com.munsun.gateway.clients.DealClient;
import com.munsun.gateway.clients.StatementClient;
import com.munsun.gateway.controllers.annotations.*;
import com.munsun.gateway.dto.request.FinishRegistrationRequestDto;
import com.munsun.gateway.dto.request.LoanStatementRequestDto;
import com.munsun.gateway.dto.response.LoanOfferDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GatewayController {
    private final DealClient dealClient;
    private final StatementClient statementClient;

    @PostMapping("/statement")
    @CreateLoanOffersStatementSwaggerDescription
    public String createLoanOfferStatement(@RequestBody LoanStatementRequestDto loanStatement) {
        return statementClient.getLoanOffers(loanStatement);
    }

    @PostMapping("/statement/select")
    @SelectLoanOfferSwaggerDescription
    public void selectLoanOffer(@RequestBody LoanOfferDto loanOfferDto) {
        statementClient.selectOffer(loanOfferDto);
    }

    @PostMapping("/statement/registration/{statementId}")
    @FinishRegistrationSwaggerDescription
    public void finishRegistration(@PathVariable String statementId,
                                   @RequestBody FinishRegistrationRequestDto finishRegistration)
    {
        dealClient.finishRegistration(statementId, finishRegistration);
    }

    @PostMapping("/document/{statementId}")
    @CreateDocumentSwaggerDescription
    public void createDocument(@PathVariable String statementId) {
        dealClient.createDocument(statementId);
    }

    @PostMapping("/document/{statementId}/sign")
    @SendDocumentSwaggerDescription
    public void sendDocument(@PathVariable String statementId) {
        dealClient.sendDocument(statementId);
    }

    @PostMapping("/document/{statementId}/code")
    @ConfirmCodeLoanOfferSwaggerDescription
    public void confirmCodeLoanOffer(@PathVariable String statementId,
                                     @RequestParam String confirmCode)
    {
        dealClient.confirmLoanOffer(statementId, confirmCode);
    }
}
