package com.munsun.auth.services.providers;

import com.munsun.auth.services.providers.impl.Base64PasswordEncoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Base64PasswordEncoder.class)
public class Base64PasswordEncoderUnitTests {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("Test encode password to base64")
    @ParameterizedTest
    @MethodSource("passwordArgFactory")
    public void givenValidPassword_whenEncode_thenValidEncodedPassword(String password, String base64Password) {
        String expectedPassword = "dGVzdF9wYXNzd29yZCExMjM0cXdlcnR5";
        String testPassword = "test_password!1234qwerty";

        var actualPassword = passwordEncoder.encode(testPassword);

        assertThat(expectedPassword)
                .isNotNull()
                .isEqualTo(actualPassword);
    }

    public static Stream<Arguments> passwordArgFactory() {
        return Stream.of(
                Arguments.of("test_password!1234qwerty", "dGVzdF9wYXNzd29yZCExMjM0cXdlcnR5"),
                Arguments.of("test_password", "dGVzdF9wYXNzd29yZA=="),
                Arguments.of("", "KCkoKSgp"));
    }

}
