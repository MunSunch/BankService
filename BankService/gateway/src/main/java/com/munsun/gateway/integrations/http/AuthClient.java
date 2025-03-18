package com.munsun.gateway.integrations.http;

import com.munsun.gateway.config.AuthClientConfig;
import com.munsun.gateway.dto.SecurityInfoDto;
import com.munsun.gateway.dto.UserInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "${services.auth.name}", configuration = AuthClientConfig.class)
public interface AuthClient {
    @PostMapping("/auth/v1/validate")
    void validateToken(@RequestParam(name = "token") String token);

    @PostMapping("/auth/v1/login")
    SecurityInfoDto login(@RequestBody UserInfoDto userInfoDto);

    @PostMapping("/auth/v1/registration")
    SecurityInfoDto register(@RequestBody UserInfoDto userInfoDto);
}
