package com.munsun.deal.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.munsun.deal.dto.response.ErrorMessageDto;
import com.munsun.deal.services.DealService;
import com.munsun.deal.utils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @Test
    public void givenRequestWithInvalidAmount_whenSendRequest_thenReturnErrorMessageStatus400() throws Exception {
        var response = mockMvc.perform(post(TestUtils.LOAN_OFFERS_ENDPOINT_DEAL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(TestUtils.getLoanStatementRequestDtoInvalidAmount())))
                .andExpect(status().isBadRequest())
                .andReturn();
        ErrorMessageDto result = mapper.readValue(response.getResponse().getContentAsString(), ErrorMessageDto.class);

        assertThat(result.message().contains("prescoring"))
                .isTrue();
        verify(service, never()).getLoanOffers(any());
    }
}
