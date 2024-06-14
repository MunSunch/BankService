package com.munsun.deal.mapping;

import com.munsun.deal.dto.response.CreditDto;
import com.munsun.deal.dto.response.PaymentScheduleElementDto;
import com.munsun.deal.models.Credit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CreditMapper {
    @Mapping(target = "insuranceEnabled", source = "isInsuranceEnabled")
    @Mapping(target = "salaryClient", source = "isSalaryClient")
    Credit toCredit(CreditDto creditDto);

    default String toString(List<PaymentScheduleElementDto> schedule) {
        return schedule.stream()
                .map(PaymentScheduleElementDto::toString)
                .collect(Collectors.joining(","));
    }
}
