package com.munsun.dossier.kafka.payload;

import java.math.BigDecimal;
import java.util.List;

public record CreditDto(
        BigDecimal amount,
        Integer term,
        BigDecimal monthlyPayment,
        BigDecimal rate,
        BigDecimal psk,
        Boolean isInsuranceEnabled,
        Boolean isSalaryClient,
        List<PaymentScheduleElementDto> paymentSchedule
) {}