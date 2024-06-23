package com.munsun.deal.dto.request;

import com.munsun.deal.models.enums.EmploymentPosition;
import com.munsun.deal.models.enums.EmploymentStatus;

import javax.swing.text.Position;
import java.math.BigDecimal;

public record EmploymentDto(
        EmploymentStatus employmentStatus,
        String employerINN,
        BigDecimal salary,
        EmploymentPosition position,
        Integer workExperienceTotal,
        Integer workExperienceCurrent
) {}