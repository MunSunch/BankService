package com.munsun.deal.kafka.payload;

import com.munsun.deal.dto.CreditDto;
import com.munsun.deal.kafka.payload.enums.Theme;

import java.util.UUID;

public record EmailMessageWithCreditDto(
        String address,
        Theme theme,
        UUID statementId,
        CreditDto creditDto
) {}