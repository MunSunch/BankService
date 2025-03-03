package com.munsun.deal.kafka.payload;

import com.munsun.deal.kafka.payload.enums.Theme;

import java.util.UUID;

public record EmailMessage(
        String address,
        Theme theme,
        UUID statementId
) {}