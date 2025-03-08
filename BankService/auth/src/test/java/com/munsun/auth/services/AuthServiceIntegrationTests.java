package com.munsun.auth.services;

import com.munsun.auth.dto.SecurityInfoDto;
import com.munsun.auth.entities.User;
import com.munsun.auth.entities.enums.Role;
import com.munsun.auth.exceptions.InvalidCredentialsException;
import com.munsun.auth.exceptions.UserCreateException;
import com.munsun.auth.exceptions.UserNotFoundException;
import com.munsun.auth.repositories.UserRepository;
import com.munsun.auth.services.providers.impl.Base64PasswordEncoder;
import com.munsun.auth.utils.MockDataUtils;
import com.munsun.auth.utils.PostgresContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@Transactional
@SpringBootTest
public class AuthServiceIntegrationTests extends PostgresContainer {
    @Autowired
    private Base64PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;

    @DisplayName("Test login not exists user")
    @Test
    public void givenNotExistsUser_whenLogin_thenThrowUserNotFoundException() {
        var notExistsUser = MockDataUtils.getUserInfoDto_RoleUser_UsernameMunir();

        assertThatThrownBy(() -> authService.login(notExistsUser))
                .isInstanceOf(UserNotFoundException.class);
    }

    @DisplayName("Test login already exists user")
    @Test
    public void givenSavedUser_whenLogin_thenReturnSecurityInfoDto() {
        var user = MockDataUtils.getUserEntityTransient_RoleUser_UsernameMunir();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        var actualSecurityInfoDto = authService.login(MockDataUtils.getUserInfoDto_RoleUser_UsernameMunir());

        assertThat(actualSecurityInfoDto)
                .isNotNull()
                .extracting(SecurityInfoDto.Fields.accessToken)
                .isNotNull();
    }

    @DisplayName("Test login incorrect password")
    @Test
    public void givenSavedUser_whenLoginWithIncorrectPassword_thenThrowInvalidCredentialsException() {
        var user = MockDataUtils.getUserEntityTransient_RoleUser_UsernameMunir();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        assertThatThrownBy(() -> authService.login(MockDataUtils.getUserInfoDto_RoleUser_UsernameMunir_IncorrectPassword()))
                .isInstanceOf(InvalidCredentialsException.class);
    }

    @DisplayName("Test register already exists user")
    @Test
    public void givenSavedUser_whenRegister_thenThrowUserCreateException() {
        userRepository.save(MockDataUtils.getUserEntityTransient_RoleUser_UsernameMunir());

        assertThatThrownBy(() ->
            authService.register(MockDataUtils.getUserInfoDto_RoleUser_UsernameMunir())
        ).isInstanceOf(UserCreateException.class);
    }

    @DisplayName("Test register not exists user")
    @Test
    public void givenUserInfoDto_whenRegister_thenUserIsSaved() {
         authService.register(MockDataUtils.getUserInfoDto_RoleUser_UsernameMunir());

        Assertions.assertDoesNotThrow(() -> {
                    User user = userRepository.findByUsername("Munir").get();
                    assertThat(user.getUsername()).isEqualTo("Munir");
                    assertThat(user.getPassword()).isNotNull();
                    assertThat(user.getRole()).isEqualTo(Role.USER);
                }
        );
    }

    @DisplayName("Test register user with role USER")
    @Test
    public void givenUserInfoDto_whenRegister_thenSavedUserWithRoleUser() {
        var userInfoDto = MockDataUtils.getUserInfoDto_RoleUser_UsernameMunir();
        var expectedRole = Role.USER;

        authService.register(userInfoDto);

        var actual = userRepository.findByUsername(userInfoDto.getUsername());
        assertThat(actual)
                .isPresent().get()
                .extracting(User.Fields.role)
                .isEqualTo(expectedRole);
    }
}
