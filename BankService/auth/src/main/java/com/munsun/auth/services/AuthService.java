package com.munsun.auth.services;

import com.munsun.auth.dto.SecurityInfoDto;
import com.munsun.auth.dto.UserInfoDto;
import com.munsun.auth.entities.User;
import com.munsun.auth.entities.enums.Role;
import com.munsun.auth.exceptions.InvalidCredentialsException;
import com.munsun.auth.exceptions.UserCreateException;
import com.munsun.auth.exceptions.UserNotFoundException;
import com.munsun.auth.mapping.UserMapper;
import com.munsun.auth.repositories.UserRepository;
import com.munsun.auth.services.providers.impl.JwtProvider;
import com.munsun.auth.services.providers.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtProvider jwtProvider;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SecurityInfoDto login(UserInfoDto userInfoDto) {
        User user = userRepository.findByUsername(userInfoDto.getUsername())
                .orElseThrow(() -> new UserNotFoundException(String.format("User with username=%s not found", userInfoDto.getUsername())));
        var encodedPassword = passwordEncoder.encode(userInfoDto.getPassword());
        if(!user.getPassword().equals(encodedPassword)) {
            throw new InvalidCredentialsException("Login/password incorrect");
        }
        Role role = user.getRole();
        return SecurityInfoDto.builder()
                .accessToken(jwtProvider.createToken(user.getUsername(), role))
                .build();
    }

    public void register(UserInfoDto userInfoDto) {
        if(userRepository.existsByUsername(userInfoDto.getUsername())) {
            throw new UserCreateException(String.format("User with username=%s already exists", userInfoDto.getUsername()));
        }
        var userEntity = userMapper.toEntity(userInfoDto);
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userEntity.setRole(Role.USER);
        userRepository.save(userEntity);
    }

    public void validate(String token) {
        jwtProvider.validateToken(token);
    }
}