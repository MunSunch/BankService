package com.munsun.deal.integrations.amqp.kafka.payload;

import com.munsun.deal.integrations.amqp.kafka.payload.enums.Theme;

import java.util.UUID;

public record EmailMessage(
        String address,
        Theme theme,
        UUID statementId
) {}