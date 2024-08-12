package com.munsun.deal.dto.request;

import com.munsun.deal.models.enums.Gender;
import com.munsun.deal.models.enums.MaritalStatus;

import java.time.LocalDate;

public record FinishRegistrationRequestDto(
        Gender gender,
        MaritalStatus maritalStatus,
        Integer dependentAmount,
        LocalDate passportIssueDate,
        String passportIssueBranch,
        EmploymentDto employment,
        String accountNumber
) {
}