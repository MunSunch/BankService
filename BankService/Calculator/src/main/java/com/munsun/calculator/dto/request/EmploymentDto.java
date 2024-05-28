package com.munsun.calculator.dto.request;

import com.munsun.calculator.dto.request.enums.EmploymentStatus;
import com.munsun.calculator.dto.request.enums.Position;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record EmploymentDto(
        EmploymentStatus employmentStatus,
        String employerINN,
        BigDecimal salary,
        Position position,
        Integer workExperienceTotal,
        Integer workExperienceCurrent
) {}