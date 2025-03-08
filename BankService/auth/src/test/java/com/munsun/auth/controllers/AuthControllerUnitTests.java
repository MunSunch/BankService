package com.munsun.auth.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.munsun.auth.exceptions.InvalidCredentialsException;
import com.munsun.auth.services.AuthService;
import com.munsun.auth.utils.MockDataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class AuthControllerUnitTests {
    @MockBean
    private AuthService authService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Test login not valid user")
    @Test
    public void givenNotValidUser_whenLogin_thenReturnErrorDtoStatus400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(MockDataUtils.getInvalidUserInfoDto_RoleUser_UsernameAndrey())))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Test register not valid user")
    @Test
    public void givenNotValidUser_whenRegister_thenReturnErrorDtoStatus400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(MockDataUtils.getInvalidUserInfoDto_RoleUser_UsernameAndrey())))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Test handle InvalidCredentialsException")
    @Test
    public void givenServiceThrowInvalidCredentialsException_whenRequestLogin_thenReturnErrorDtoStatus400() throws Exception {
        when(authService.login(any())).thenThrow(InvalidCredentialsException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(MockDataUtils.getUserInfoDto_RoleUser_UsernameMunir())))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Test handle RuntimeException")
    @Test
    public void givenServiceThrowRuntimeException_whenRequestLogin_thenReturnErrorDtoStatus500() throws Exception {
        when(authService.login(any())).thenThrow(RuntimeException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(MockDataUtils.getUserInfoDto_RoleUser_UsernameMunir())))
                .andExpect(status().isInternalServerError());
    }
}