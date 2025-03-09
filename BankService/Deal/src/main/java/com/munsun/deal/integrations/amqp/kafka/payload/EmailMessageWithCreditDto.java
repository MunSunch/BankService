package com.munsun.deal.integrations.amqp.kafka.payload;

import com.munsun.deal.dto.CreditDto;
import com.munsun.deal.integrations.amqp.kafka.payload.enums.Theme;

import java.util.UUID;

public record EmailMessageWithCreditDto(
        String address,
        Theme theme,
        UUID statementId,
        CreditDto creditDto
) {}