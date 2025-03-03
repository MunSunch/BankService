package com.munsun.deal.mapping;

import com.munsun.deal.dto.CreditDto;
import com.munsun.deal.models.Credit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreditMapper {
    @Mapping(target = "insuranceEnabled", source = "isInsuranceEnabled")
    @Mapping(target = "salaryClient", source = "isSalaryClient")
    Credit toCredit(CreditDto creditDto);

    @Mapping(target = "isInsuranceEnabled", source = "insuranceEnabled")
    @Mapping(target = "isSalaryClient", source = "salaryClient")
    CreditDto toCreditDto(Credit credit);
}
