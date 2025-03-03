package com.munsun.dossier.kafka.payload;

import com.munsun.dossier.kafka.payload.enums.Theme;

import java.util.UUID;

public record EmailMessageWithSesCode(
        String address,
        Theme theme,
        UUID statementId,
        UUID sesCodeConfirm
) {}