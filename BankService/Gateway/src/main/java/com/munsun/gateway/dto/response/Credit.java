package com.munsun.gateway.dto.response;

import com.munsun.gateway.dto.response.enums.CreditStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record Credit(
        UUID creditId,
        BigDecimal amount,
        Integer term,
        BigDecimal monthlyPayment,
        BigDecimal rate,
        BigDecimal psk,
        List<PaymentScheduleElementDto> paymentSchedule,
        Boolean insuranceEnabled,
        Boolean salaryClient,
        CreditStatus status
) {}