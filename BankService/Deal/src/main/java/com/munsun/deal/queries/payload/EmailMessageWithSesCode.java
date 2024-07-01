package com.munsun.deal.queries.payload;

import com.munsun.deal.queries.payload.enums.Theme;

import java.util.UUID;

public record EmailMessageWithSesCode(
        String address,
        Theme theme,
        UUID statementId,
        UUID sesCodeConfirm
) {}