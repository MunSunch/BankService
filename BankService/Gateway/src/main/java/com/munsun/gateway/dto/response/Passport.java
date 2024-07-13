package com.munsun.gateway.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record Passport(
        UUID passportUUID,
        String series,
        String number,
        String issueBranch,
        LocalDate issueDate
) {}