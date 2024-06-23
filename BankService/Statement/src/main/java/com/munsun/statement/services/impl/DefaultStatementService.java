package com.munsun.statement.services.impl;

import com.munsun.statement.clients.DealClient;
import com.munsun.statement.dto.LoanOfferDto;
import com.munsun.statement.dto.LoanStatementRequestDto;
import com.munsun.statement.dto.annotations.groups.Prescoring;
import com.munsun.statement.services.StatementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
@Validated(Prescoring.class)
public class DefaultStatementService implements StatementService {
    private final DealClient client;
    @Override
    public List<LoanOfferDto> getLoanOffers(@Valid LoanStatementRequestDto loanStatement) {
        return client.getLoanOffers(loanStatement);
    }

    @Override
    public void selectLoanOffer(LoanOfferDto loanOfferDto) {
        client.selectLoanOffer(loanOfferDto);
    }
}
