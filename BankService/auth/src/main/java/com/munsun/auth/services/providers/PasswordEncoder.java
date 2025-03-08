package com.munsun.auth.services.providers;

import jakarta.validation.constraints.NotNull;

public interface PasswordEncoder {
    String encode(@NotNull String password);
}
