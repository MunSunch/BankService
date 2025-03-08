package com.munsun.auth.utils;

import com.munsun.auth.dto.UserInfoDto;
import com.munsun.auth.entities.User;
import com.munsun.auth.entities.enums.Role;

import java.util.Base64;
import java.util.UUID;

public class MockDataUtils {
    public static String generateAccessToken_RoleAdmin_UsernameMunir() {
        return "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9BRE1JTiIsImlzcyI6ImF1dGgiLCJleHAiOjE3NDEyNjMxNTcsImlhdCI6MTc0MTI2Mjg1NywidXNlcm5hbWUiOiJNdW5pciJ9.1lqAS2My1BZZhckuUQUJ4QhCHeDD37Y5B_ZBLtDJsZI";
    }

    public static String generateAccessToken_RoleAdmin_UsernameMunir_Expired() {
        return "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9BRE1JTiIsImlzcyI6ImF1dGgiLCJleHAiOjE3NDEyNjMxNTcsImlhdCI6MTc0MTI2Mjg1NywidXNlcm5hbWUiOiJNdW5pciJ9.1lqAS2My1BZZhckuUQUJ4QhCHeDD37Y5B_ZBLtDJsZI";
    }

    public static User getUserEntityTransient_RoleUser_UsernameMunir() {
        return User.builder()
                .username("Munir")
                .password("123456")
                .role(Role.USER)
                .build();
    }

    public static User getUserEntityPrepared_RoleUser_UsernameMunir() {
        return User.builder()
                .username("Munir")
                .password(Base64.getEncoder().encodeToString("123456".getBytes()))
                .role(Role.USER)
                .build();
    }

    public static UserInfoDto getUserInfoDto_RoleUser_UsernameMunir() {
        return UserInfoDto.builder()
                .username("Munir")
                .password("123456")
                .build();
    }

    public static UserInfoDto getUserInfoDto_RoleUser_UsernameAndrey() {
        return UserInfoDto.builder()
                .username("Andrey")
                .password("123456")
                .build();
    }

    public static UserInfoDto getInvalidUserInfoDto_RoleUser_UsernameAndrey() {
        return UserInfoDto.builder()
                .username("Andrey")
                .password("1")
                .build();
    }

    public static UserInfoDto getUserInfoDto_RoleUser_UsernameMunir_IncorrectPassword() {
        return UserInfoDto.builder()
                .username("Munir")
                .password("qwerty")
                .build();
    }

    public static User getUserEntityPersistent_RoleUser_UsernameMunir() {
        return User.builder()
                .uuid(UUID.randomUUID())
                .username("Munir")
                .password("123456")
                .role(Role.USER)
                .build();
    }
}