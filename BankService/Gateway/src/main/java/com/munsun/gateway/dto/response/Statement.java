package com.munsun.gateway.dto.response;

import com.munsun.gateway.dto.response.enums.ApplicationStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record Statement(
        UUID statementId,
        Client client,
        Credit credit,
        ApplicationStatus status,
        LocalDate creationDate,
        LoanOfferDto appliedOffer,
        LocalDate signDate,
        String code,
        List<StatusHistory> statusHistory
) {}