package com.munsun.statement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.munsun.statement.dto.annotations.DiffPresentAndCurrent;
import com.munsun.statement.dto.annotations.groups.Prescoring;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record LoanStatementRequestDto(
        @NotNull
        @Min(value = 30_000, message = "amount<30_000", groups = Prescoring.class)
        BigDecimal amount,
        @NotNull
        @Min(value = 6, message = "term<6", groups = Prescoring.class)
        Integer term,
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "valid characters: a-z, A-Z; length from 2 to 30", groups = Prescoring.class)
        String firstName,
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "valid characters: a-z, A-Z; length from 2 to 30", groups = Prescoring.class)
        String lastName,
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "valid characters: a-z, A-Z; length from 2 to 30", groups = Prescoring.class)
        String middleName,
        @NotEmpty
        @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "valid format: {a-z A-Z 0-9 _!#$%&'*+/=?`{|}~^.-}@{a-z A-Z 0-9 .-}", groups = Prescoring.class)
        String email,
        @NotNull
        @DiffPresentAndCurrent(years=18, message = "years < 18", groups = Prescoring.class)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate birthdate,
        @NotBlank
        @Pattern(regexp = "^\\d{4}$", message = "valid characters: 0-9; valid length = 4", groups = Prescoring.class)
        String passportSeries,
        @NotBlank
        @Pattern(regexp = "^\\d{6}$", message = "alid characters: 0-9; valid length = 6", groups = Prescoring.class)
        String passportNumber
) {}