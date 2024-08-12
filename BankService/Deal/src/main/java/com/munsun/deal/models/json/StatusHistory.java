package com.munsun.deal.models.json;

import com.munsun.deal.models.enums.ChangeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatusHistory {
    private String status;
    private LocalDate time;
    private ChangeType type;
}
