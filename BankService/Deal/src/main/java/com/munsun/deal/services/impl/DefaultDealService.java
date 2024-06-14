package com.munsun.deal.services.impl;

import com.munsun.deal.dto.request.FinishRegistrationRequestDto;
import com.munsun.deal.dto.request.LoanStatementRequestDto;
import com.munsun.deal.dto.request.ScoringDataDto;
import com.munsun.deal.dto.response.CreditDto;
import com.munsun.deal.dto.response.LoanOfferDto;
import com.munsun.deal.exceptions.StatementNotFoundException;
import com.munsun.deal.mapping.ClientMapper;
import com.munsun.deal.mapping.CreditMapper;
import com.munsun.deal.mapping.ScoringMapper;
import com.munsun.deal.models.Client;
import com.munsun.deal.models.Credit;
import com.munsun.deal.models.Statement;
import com.munsun.deal.models.enums.ApplicationStatus;
import com.munsun.deal.models.enums.ChangeType;
import com.munsun.deal.models.enums.CreditStatus;
import com.munsun.deal.repositories.ClientRepository;
import com.munsun.deal.repositories.CreditRepository;
import com.munsun.deal.repositories.StatementRepository;
import com.munsun.deal.services.DealService;
import com.munsun.deal.services.impl.clients.CalculatorFeignClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultDealService implements DealService {
    private final ClientRepository clientRepository;
    private final StatementRepository statementRepository;
    private final CreditRepository creditRepository;

    private final ClientMapper clientMapper;
    private final ScoringMapper scoringMapper;
    private final CreditMapper creditMapper;

    private final CalculatorFeignClient calculatorClient;

    @Override
    public List<LoanOfferDto> getLoanOffers(LoanStatementRequestDto loanStatement) {
        Client client = clientMapper.toClient(loanStatement);
            client.getPassport().setPassportUUID(UUID.randomUUID());
        Statement statement = new Statement();
            statement.setCreationDate(LocalDate.now());
            statement.setStatus(ApplicationStatus.PREAPPROVAL, ChangeType.AUTOMATIC);
            statement.setClient(clientRepository.save(client));
        statementRepository.save(statement);
        List<LoanOfferDto> offers;
        try {
            offers = calculatorClient.getLoanOffers(loanStatement);
        } catch (FeignException e) {
            if(e.status() == 400 && e.contentUTF8().contains("prescoring")) {
                statement.setStatus(ApplicationStatus.CC_DENIED, ChangeType.AUTOMATIC);
                statementRepository.save(statement);
            }
            throw e;
        }
        return offers.stream()
                .map(oldOffer -> new LoanOfferDto(statement.getStatementId(), oldOffer))
                .collect(Collectors.toList());
    }

    @Override
    public void calculateCredit(String statementId, FinishRegistrationRequestDto finishRegistration) {
        Statement statement = statementRepository.findById(UUID.fromString(statementId))
                .orElseThrow(() -> new StatementNotFoundException(statementId));
        ScoringDataDto scoringDataDto = scoringMapper.toScoringDataDto(statement, finishRegistration);
        CreditDto creditDto;
        try {
            creditDto = calculatorClient.getCredit(scoringDataDto);
            statement.setStatus(ApplicationStatus.CC_APPROVED, ChangeType.AUTOMATIC);
        } catch (FeignException e) {
            if(e.status() == 500 && e.contentUTF8().contains("scoring")) {
                statement.setStatus(ApplicationStatus.CC_DENIED, ChangeType.AUTOMATIC);
                statementRepository.save(statement);
            }
            throw e;
        }
        Credit credit = creditMapper.toCredit(creditDto);
            credit.setStatus(CreditStatus.CALCULATED);
        creditRepository.save(credit);
    }

    @Override
    public void selectLoanOffer(LoanOfferDto loanOffer) {
        UUID statementUUID = loanOffer.statementId();
        Statement statement = statementRepository.findById(statementUUID)
                .orElseThrow(() -> new StatementNotFoundException(loanOffer.statementId().toString()));
        statement.setAppliedOffer(loanOffer);
        statement.setStatus(ApplicationStatus.APPROVED, ChangeType.AUTOMATIC);
        statementRepository.save(statement);
    }
}