package com.munsun.dossier.kafka.payload;

import com.munsun.dossier.kafka.payload.enums.Theme;

import java.util.UUID;

public record EmailMessageWithCreditDto(
        String address,
        Theme theme,
        UUID statementId,
        CreditDto creditDto
) {}