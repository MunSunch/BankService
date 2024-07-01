package com.munsun.deal.queries.payload;

import com.munsun.deal.dto.response.CreditDto;
import com.munsun.deal.queries.payload.enums.Theme;

import java.util.UUID;

public record EmailMessageWithCreditDto(
        String address,
        Theme theme,
        UUID statementId,
        CreditDto creditDto
) {}