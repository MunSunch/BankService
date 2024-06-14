package com.munsun.deal.models.json;

import com.munsun.deal.models.enums.EmploymentPosition;
import com.munsun.deal.models.enums.EmploymentStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

public class Employment {
    UUID employmentUUID;

    EmploymentStatus status;

    String inn;

    BigDecimal salary;

    EmploymentPosition position;

    Integer workExperienceTotal;

    Integer workExperienceCurrent;
}
