package com.munsun.gateway.dto.response;

import com.munsun.gateway.dto.request.enums.MaritalStatus;
import com.munsun.gateway.dto.response.enums.Gender;

import java.time.LocalDate;
import java.util.UUID;

public record Client(
        UUID clientId,
        String lastName,
        String firstName,
        String middleName,
        LocalDate birthdate,
        String email,
        Gender gender,
        MaritalStatus maritalStatus,
        Integer dependentAmount,
        Passport passport,
        Employment employment,
        String accountNumber
) {}