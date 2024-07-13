package com.munsun.gateway.dto.response;

import com.munsun.gateway.dto.response.enums.ChangeType;

import java.time.LocalDate;

public record StatusHistory(
        String status,
        LocalDate time,
        ChangeType type
) {}