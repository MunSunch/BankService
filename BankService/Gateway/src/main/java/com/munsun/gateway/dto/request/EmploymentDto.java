package com.munsun.gateway.dto.request;

import com.munsun.gateway.dto.request.enums.EmploymentPosition;
import com.munsun.gateway.dto.request.enums.EmploymentStatus;

import java.math.BigDecimal;

public record EmploymentDto(
        EmploymentStatus employmentStatus,
        String employerINN,
        BigDecimal salary,
        EmploymentPosition position,
        Integer workExperienceTotal,
        Integer workExperienceCurrent
) {}