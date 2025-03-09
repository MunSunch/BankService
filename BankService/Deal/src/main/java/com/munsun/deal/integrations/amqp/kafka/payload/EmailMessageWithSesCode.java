package com.munsun.deal.integrations.amqp.kafka.payload;

import com.munsun.deal.integrations.amqp.kafka.payload.enums.Theme;

import java.util.UUID;

public record EmailMessageWithSesCode(
        String address,
        Theme theme,
        UUID statementId,
        UUID sesCodeConfirm
) {}