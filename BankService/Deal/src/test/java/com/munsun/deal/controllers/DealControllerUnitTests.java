package com.munsun.deal.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.munsun.deal.dto.ErrorMessageDto;
import com.munsun.deal.dto.LoanStatementRequestDto;
import com.munsun.deal.dto.TypePayments;
import com.munsun.deal.services.DealService;
import com.munsun.deal.utils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class DealControllerUnitTests {
    @MockBean
    private DealService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @DisplayName("Test throw prescoring error")
    @ParameterizedTest
    @MethodSource("loanStatementRequestDtoInvalidFactory")
    public void givenRequestWithInvalidAmount_whenSendRequestWithAnnuityPayment_thenReturnErrorMessageStatus400(LoanStatementRequestDto requestDto) throws Exception {
        var response = mockMvc.perform(post(TestUtils.LOAN_OFFERS_ENDPOINT_DEAL+"?typePayment=ANNUITY")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(TestUtils.getLoanStatementRequestDtoInvalidAmount())))
                .andExpect(status().isBadRequest())
                .andReturn();

        var result = mapper.readValue(response.getResponse().getContentAsString(), ErrorMessageDto.class);

        assertThat(result)
                .isNotNull();
        verify(service, never()).getLoanOffers(any(), any());
    }

    public static Stream<LoanStatementRequestDto> loanStatementRequestDtoInvalidFactory() {
        return Stream.of(
                TestUtils.getLoanStatementRequestDtoInvalidAmount(),
                TestUtils.getLoanStatementRequestDtoInvalidBirthDate(),
                TestUtils.getLoanStatementRequestDtoInvalidMiddleName(),
                TestUtils.getLoanStatementRequestDtoInvalidTerm(),
                TestUtils.getLoanStatementRequestDtoInvalidPassportSeries(),
                TestUtils.getLoanStatementRequestDtoInvalidPassportNumber(),
                TestUtils.getLoanStatementRequestDtoInvalidFirstName(),
                TestUtils.getLoanStatementRequestDtoInvalidLastName()
        );
    }

    @DisplayName("Test throw prescoring error")
    @ParameterizedTest
    @MethodSource("loanStatementRequestDtoInvalidFactory")
    public void givenRequestWithInvalidAmount_whenSendRequestWithDifferentPayment_thenReturnErrorMessageStatus400(LoanStatementRequestDto requestDto) throws Exception {
        var response = mockMvc.perform(post(TestUtils.LOAN_OFFERS_ENDPOINT_DEAL+"?typePayment=DIFFERENTIAL")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(TestUtils.getLoanStatementRequestDtoInvalidAmount())))
                .andExpect(status().isBadRequest())
                .andReturn();

        var result = mapper.readValue(response.getResponse().getContentAsString(), ErrorMessageDto.class);

        assertThat(result)
                .isNotNull();
        verify(service, never()).getLoanOffers(any(), any());
    }
}
