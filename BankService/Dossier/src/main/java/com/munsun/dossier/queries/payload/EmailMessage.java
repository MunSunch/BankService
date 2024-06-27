package com.munsun.dossier.queries.payload;

import com.munsun.dossier.queries.payload.enums.Theme;

import java.util.UUID;

public record EmailMessage(
        String address,
        Theme theme,
        UUID statementId,
        String description
) {}