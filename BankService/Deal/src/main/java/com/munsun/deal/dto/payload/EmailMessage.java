package com.munsun.deal.dto.payload;

import com.munsun.deal.dto.payload.enums.Theme;

import java.util.UUID;

public record EmailMessage(
        String address,
        Theme theme,
        UUID statementId,
        String description
) {}