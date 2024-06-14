package com.munsun.deal.models.json;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Passport {
    UUID passportUUID;
    String series;
    String number;
    String issueBranch;
    LocalDate issueDate;
}
