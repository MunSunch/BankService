package com.munsun.auth.controllers;

import com.munsun.auth.dto.ErrorDto;
import com.munsun.auth.exceptions.UserCreateException;
import com.munsun.auth.exceptions.UserNotFoundException;
import com.munsun.auth.repositories.UserRepository;
import com.munsun.auth.utils.MockDataUtils;
import com.munsun.auth.utils.PostgresContainer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerIntegrationTests extends PostgresContainer {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestRestTemplate restTemplate;

    @DisplayName("Test login not exists user")
    @Test
    public void givenNotExistsUser_whenLogin_thenReturnErrorDtoStatus404() {
        var response = restTemplate.postForEntity("/login", MockDataUtils.getUserInfoDto_RoleUser_UsernameAndrey(), ErrorDto.class);

        assertAll(
                () -> assertThat(response.getStatusCode().value())
                        .isEqualTo(HttpStatus.NOT_FOUND.value()),
                () -> assertThat(response.getBody().getMessage())
                        .isNotBlank(),
                () -> assertThat(response.getBody().getHttpStatusCode().intValue())
                        .isEqualTo(HttpStatus.NOT_FOUND.value()),
                () -> assertThat(response.getBody().getException())
                        .isEqualTo(UserNotFoundException.class.getSimpleName())
        );
    }

    @DisplayName("Test register user")
    @Test
    public void givenUserInfoDto_whenRegister_thenReturnStatus200AndUserIsSaved() {
        var response = restTemplate.postForEntity("/registration", MockDataUtils.getUserInfoDto_RoleUser_UsernameMunir(), Void.class);
        var actualUser = userRepository.findByUsername("Munir");

        assertAll(
                () -> assertThat(response.getStatusCode().is2xxSuccessful()).isTrue(),
                () -> assertThat(actualUser).isPresent()
        );
    }

    @DisplayName("Test register already exists user")
    @Test
    public void givenSavedUserAndUserInfoDto_whenRegister_thenReturnStatus200AndUserIsSaved() {
        restTemplate.postForEntity("/registration", MockDataUtils.getUserInfoDto_RoleUser_UsernameMunir(), Void.class);
        var response = restTemplate.postForEntity("/registration", MockDataUtils.getUserInfoDto_RoleUser_UsernameMunir(), ErrorDto.class);

        assertAll(
                () -> assertThat(response.getStatusCode().is4xxClientError()).isTrue(),
                () -> assertThat(response.getBody().getHttpStatusCode())
                        .isNotNull()
                        .isEqualTo(HttpStatus.CONFLICT.value()),
                () -> assertThat(response.getBody().getException())
                        .isNotBlank()
                        .isEqualTo(UserCreateException.class.getSimpleName())
        );
    }
}
