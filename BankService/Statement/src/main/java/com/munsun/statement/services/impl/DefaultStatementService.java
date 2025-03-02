package com.munsun.statement.services.impl;

import com.munsun.statement.clients.DealClient;
import com.munsun.statement.dto.LoanOfferDto;
import com.munsun.statement.dto.LoanStatementRequestDto;
import com.munsun.statement.dto.TypePayments;
import com.munsun.statement.services.StatementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultStatementService implements StatementService {
    private final DealClient client;

    @Override
    public List<LoanOfferDto> getLoanOffers(TypePayments typePayment, LoanStatementRequestDto loanStatement) {
        return client.getLoanOffers(typePayment, loanStatement).getBody();
    }

    @Override
    public void selectLoanOffer(TypePayments typePayment, LoanOfferDto loanOfferDto) {
        client.selectLoanOffer(typePayment, loanOfferDto);
    }
}
