package com.munsun.auth.services.providers;

import com.auth0.jwt.JWT;
import com.munsun.auth.entities.enums.Role;
import com.munsun.auth.exceptions.InvalidCredentialsException;
import com.munsun.auth.services.providers.impl.JwtProvider;
import com.munsun.auth.utils.MockDataUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = JwtProvider.class)
public class JwtProviderUnitTests {
    @Autowired
    private JwtProvider jwtProvider;

    @DisplayName("Test generate jwt token with valid format")
    @Test
    public void givenValidUsernameMunirAndRoleAdmin_whenGenerateToken_thenValidToken() {
        String expectedFormat = String.format("^%s [A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+$", jwtProvider.getTokenType());

        String actualToken = jwtProvider.createToken("Munir", Role.ADMIN);

        Assertions.assertTrue(actualToken.matches(expectedFormat));
    }

    @DisplayName("Test generate jwt token with username and role")
    @MethodSource("generateTokenWithUsernameAndRoleArgs")
    @ParameterizedTest
    public void givenValidUsernameMunirAndRoleUser_whenGenerateToken_thenGetReturnJwtTokenWithRoleAdminAndUsernameMunir(String expectedUsername, Role expectedRole) {
        String token = jwtProvider.createToken(expectedUsername, expectedRole)
                .substring(jwtProvider.getTokenType().length()+1);

        String actualRole = JWT.decode(token)
                .getClaim("role").asString();
        String actualUsername = JWT.decode(token)
                .getClaim("username").asString();

        Assertions.assertAll(
                () -> assertThat(expectedRole.getRole()).isNotNull().isEqualTo(actualRole),
                () -> assertThat(expectedUsername).isNotNull().isEqualTo(actualUsername)
        );
    }

    public static Stream<Arguments> generateTokenWithUsernameAndRoleArgs() {
        return Stream.of(
                Arguments.of("Munir", Role.ADMIN),
                Arguments.of("Munir", Role.USER)
        );
    }

    @DisplayName("Test validate jwt token with expired time")
    @Test
    public void givenExpiredToken_whenValidate_thenException() {
        String expiredToken = MockDataUtils.generateAccessToken_RoleAdmin_UsernameMunir_Expired();

        assertThatThrownBy(() -> {
            jwtProvider.validateToken(expiredToken);
        }).isInstanceOf(InvalidCredentialsException.class);
    }

    @DisplayName("Token validation test with unsupported token type")
    @Test
    public void givenTokenWithUnsupportedType_whenValidate_thenThrowInvalidCredentialsException() {
        String tokenWithUnsupportedType = MockDataUtils.generateAccessToken_RoleAdmin_UsernameMunir()
                .replaceFirst("Bearer", "Unsupported");

        assertThatThrownBy(() ->
                jwtProvider.validateToken(tokenWithUnsupportedType)
        ).isInstanceOf(InvalidCredentialsException.class);
    }
}
