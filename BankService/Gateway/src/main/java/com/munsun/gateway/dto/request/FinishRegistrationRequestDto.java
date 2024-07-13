package com.munsun.gateway.dto.request;

import com.munsun.gateway.dto.request.enums.Gender;
import com.munsun.gateway.dto.request.enums.MaritalStatus;

import java.time.LocalDate;

public record FinishRegistrationRequestDto(
        Gender gender,
        MaritalStatus maritalStatus,
        Integer dependentAmount,
        LocalDate passportIssueDate,
        String passportIssueBranch,
        EmploymentDto employment,
        String accountNumber
) {}