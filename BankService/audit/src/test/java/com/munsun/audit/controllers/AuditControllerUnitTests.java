package com.munsun.audit.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.munsun.audit.services.AuditService;
import com.munsun.audit.utils.MockDataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class AuditControllerUnitTests {
    @MockBean
    private AuditService auditService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Test throw validation exception when request to /audit/admin/v1/logs")
    @Test
    public void givenValidAuditActionDtoAndParameterSizeLessOne_whenRequestGetLogs_thenReturnErrorResponseExceptionStatus400() throws Exception {
        var auditActionDto = MockDataUtils.getMockAuditActionDto();

        mockMvc.perform(post("/audit/admin/v1/logs")
                .queryParam("page", "0")
                .queryParam("size", "0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(auditActionDto))
        ).andExpect(status().isBadRequest())
         .andExpect(jsonPath("$.exception").value("ConstraintViolationException"))
         .andExpect(jsonPath("$.statusCodeHttp").value(400))
         .andExpect(jsonPath("$.message").isNotEmpty())
         .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @DisplayName("Test throw validation exception when request to /audit/admin/v1/logs")
    @Test
    public void givenValidAuditActionDtoAndParameter_whenRequestGetLogs_thenReturnErrorResponseExceptionStatus400() throws Exception {
        when(auditService.getLogs(any(), any()))
                .thenThrow(new RuntimeException("Test error message"));

        var auditActionDto = MockDataUtils.getMockAuditActionDto();

        mockMvc.perform(post("/audit/admin/v1/logs")
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(auditActionDto))
                ).andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.exception").value("RuntimeException"))
                .andExpect(jsonPath("$.statusCodeHttp").value(500))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }
}
