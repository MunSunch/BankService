package com.munsun.calculator.dto.request;

import com.munsun.calculator.dto.request.enums.Gender;
import com.munsun.calculator.dto.request.enums.MaritalStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ScoringDataDto(
        BigDecimal amount,
        Integer term,
        String firstname,
        String lastName,
        String middleName,
        Gender gender,
        String email,
        LocalDate birthdate,
        String passportSeries,
        String passportNumber,
        LocalDate passportIssueDate,
        String passportIssueBranch,
        MaritalStatus maritalStatus,
        Integer dependentAmount,
        EmploymentDto employment,
        String accountNumber,
        Boolean isInsuranceEnabled,
        Boolean isSalaryClient
) {}