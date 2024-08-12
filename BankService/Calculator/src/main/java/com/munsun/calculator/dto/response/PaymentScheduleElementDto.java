package com.munsun.calculator.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentScheduleElementDto(
        Integer number,
        LocalDate date,
        BigDecimal totalPayment,    // месячный платеж
        BigDecimal interestPayment, // проценты за месяц
        BigDecimal debtPayment, // долг основной = месяный платеж - проценты за месяц
        BigDecimal remainingDebt    // остаток всего долга
) {}