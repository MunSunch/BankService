package com.munsun.gateway.dto.response;

import com.munsun.gateway.dto.request.enums.EmploymentPosition;
import com.munsun.gateway.dto.request.enums.EmploymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record Employment(
     UUID employmentUUID,
     EmploymentStatus status,
     String inn,
     BigDecimal salary,
     EmploymentPosition position,
     Integer workExperienceTotal,
     Integer workExperienceCurrent
) {}