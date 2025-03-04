package com.munsun.deal.services.impl;

import com.munsun.deal.dto.*;
import com.munsun.deal.exceptions.InvalidSesCode;
import com.munsun.deal.kafka.payload.enums.Theme;
import com.munsun.deal.exceptions.PrescoringException;
import com.munsun.deal.exceptions.ScoringException;
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
import com.munsun.deal.kafka.producers.DealProducer;
import com.munsun.deal.repositories.ClientRepository;
import com.munsun.deal.repositories.CreditRepository;
import com.munsun.deal.repositories.StatementRepository;
import com.munsun.deal.services.DealService;
import com.munsun.deal.services.impl.clients.CalculatorFeignClient;
import feign.FeignException;
import io.micrometer.core.annotation.Counted;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
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
    private final DealProducer dealProducer;

    @Override
    public List<LoanOfferDto> getLoanOffers(TypePayments typePayment, LoanStatementRequestDto loanStatement) {
        Client client = clientMapper.toClient(loanStatement);
            client.getPassport().setPassportUUID(UUID.randomUUID());
        clientRepository.save(client);

        Statement statement = new Statement();
            statement.setCreationDate(LocalDate.now());
            statement.setStatus(ApplicationStatus.PREAPPROVAL, ChangeType.AUTOMATIC);
            statement.setClient(client);
            statement.setTypePayments(typePayment);
        statementRepository.save(statement);

        List<LoanOfferDto> offers;
        try {
            offers = calculatorClient.getLoanOffers(typePayment, loanStatement);
        } catch (PrescoringException e) {
            statement.setStatus(ApplicationStatus.CC_DENIED, ChangeType.AUTOMATIC);
            statementRepository.save(statement);
            throw e;
        }
        return offers.stream()
                .map(oldOffer -> {
                    oldOffer.setStatementId(statement.getStatementId());
                    return oldOffer;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void calculateCredit(String statementId, FinishRegistrationRequestDto finishRegistration) {
        Statement statement = statementRepository.findById(UUID.fromString(statementId))
                .orElseThrow(() -> new StatementNotFoundException(statementId));
        if(statement.getAppliedOffer() == null) {
            throw new IllegalArgumentException("select loan offer!");
        }
        ScoringDataDto scoringDataDto = scoringMapper.toScoringDataDto(statement, finishRegistration);
        CreditDto creditDto;
        try {
            creditDto = calculatorClient.getCredit(statement.getTypePayments(), scoringDataDto);
            statement.setStatus(ApplicationStatus.CC_APPROVED, ChangeType.AUTOMATIC);
        } catch (ScoringException e) {
            statement.setStatus(ApplicationStatus.CC_DENIED, ChangeType.AUTOMATIC);
            statementRepository.save(statement);
            dealProducer.sendScoringException(statement.getClient().getEmail(), Theme.CC_DENIED, statement.getStatementId());
            throw e;
        }
        Credit credit = creditMapper.toCredit(creditDto);
            credit.setStatus(CreditStatus.CALCULATED);
        statement.setCredit(credit);
        statementRepository.save(statement);

        dealProducer.sendCreateDocumentsNotification(statement.getClient().getEmail(), Theme.CC_APPROVED, statement.getStatementId());
        dealProducer.sendCreateDocumentsNotification(statement.getClient().getEmail(), Theme.CREATED_DOCUMENTS, statement.getStatementId());
    }

    @Override
    public void selectLoanOffer(TypePayments typePayment, LoanOfferDto loanOffer) {
        UUID statementUUID = loanOffer.getStatementId();
        Statement statement = statementRepository.findById(statementUUID)
                .orElseThrow(() -> new StatementNotFoundException(loanOffer.getStatementId().toString()));
            statement.setAppliedOffer(loanOffer);
            statement.setStatus(ApplicationStatus.APPROVED, ChangeType.AUTOMATIC);
        statementRepository.save(statement);
        dealProducer.sendFinishRegistrationRequestNotification(statement.getClient().getEmail(),
                Theme.FINISH_REGISTRATION, statement.getStatementId());
    }

    @Override
    public void prepareDocuments(UUID statementId) {
        Statement statement = statementRepository.findById(statementId)
                .orElseThrow(() -> new StatementNotFoundException(statementId.toString()));
            statement.setStatus(ApplicationStatus.PREPARE_DOCUMENTS, ChangeType.MANUAL);
        CreditDto creditDto = creditMapper.toCreditDto(statement.getCredit());
        dealProducer.sendPrepareDocumentsNotification(statement.getClient().getEmail(), Theme.PREPARE_DOCUMENTS,
                statementId, creditDto);
        statementRepository.save(statement);
    }

    @Override
    public void updateStatus(UUID statementId) {
        Statement statement = statementRepository.findById(statementId)
                .orElseThrow(() -> new StatementNotFoundException(statementId.toString()));
            statement.setStatus(ApplicationStatus.DOCUMENTS_CREATED, ChangeType.AUTOMATIC);
        statementRepository.save(statement);
    }

    @Override
    public void createSignCodeDocuments(UUID statementId) {
        Statement statement = statementRepository.findById(statementId)
                .orElseThrow(() -> new StatementNotFoundException(statementId.toString()));
        UUID sesCode = UUID.randomUUID();
            statement.setCode(sesCode.toString());
        dealProducer.sendSignCodeDocumentsNotification(statement.getClient().getEmail(), Theme.SIGN_DOCUMENTS, statementId, sesCode);
        statementRepository.save(statement);
    }

    @Override
    public void signDocuments(UUID statementId, String sesCode) {
        Statement statement = statementRepository.findById(statementId)
                .orElseThrow(() -> new StatementNotFoundException(statementId.toString()));
        if(!sesCode.equals(statement.getCode())) {
            throw new InvalidSesCode("Invalid ses code="+sesCode);
        }
        statement.setSignDate(LocalDate.now());
        statement.setStatus(ApplicationStatus.DOCUMENT_SIGNED, ChangeType.AUTOMATIC);
        dealProducer.sendSuccessSignDocumentsNotification(statement.getClient().getEmail(),
                Theme.SIGN_DOCUMENTS, statementId);
        statement.setStatus(ApplicationStatus.CREDIT_ISSUED, ChangeType.AUTOMATIC);
        statementRepository.save(statement);
    }
}