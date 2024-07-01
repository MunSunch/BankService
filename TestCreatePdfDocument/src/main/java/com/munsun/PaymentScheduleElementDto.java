package com.munsun;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentScheduleElementDto(
        int i,
        LocalDate localDate,
        BigDecimal decimal,
        BigDecimal decimal1,
        BigDecimal decimal2,
        BigDecimal decimal3
) {}