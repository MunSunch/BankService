package com.munsun.dossier.queries.payload;

import com.munsun.dossier.queries.payload.enums.Theme;

import java.util.UUID;

public record EmailMessageWithCreditDto(
        String address,
        Theme theme,
        UUID statementId,
        CreditDto creditDto
) {}