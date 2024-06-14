package com.munsun.deal.mapping;

import com.munsun.deal.dto.request.LoanStatementRequestDto;
import com.munsun.deal.models.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mapping(target = "passport.series", source = "passportSeries")
    @Mapping(target = "passport.number", source = "passportNumber")
    Client toClient(LoanStatementRequestDto loanStatement);
}
