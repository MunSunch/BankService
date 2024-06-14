package com.munsun.deal.models.json;

import com.munsun.deal.models.enums.ChangeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class StatusHistory {
    String status;
    LocalDate time;
    ChangeType type;
}
