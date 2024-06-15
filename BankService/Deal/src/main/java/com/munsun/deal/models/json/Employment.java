package com.munsun.deal.models.json;

import com.munsun.deal.models.enums.EmploymentPosition;
import com.munsun.deal.models.enums.EmploymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employment {
    private UUID employmentUUID;
    private EmploymentStatus status;
    private String inn;
    private BigDecimal salary;
    private EmploymentPosition position;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;
}