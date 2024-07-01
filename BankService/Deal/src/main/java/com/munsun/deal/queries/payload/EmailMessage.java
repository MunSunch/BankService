package com.munsun.deal.queries.payload;

import com.munsun.deal.queries.payload.enums.Theme;

import java.util.UUID;

public record EmailMessage(
        String address,
        Theme theme,
        UUID statementId
) {}