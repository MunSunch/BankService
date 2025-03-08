package com.munsun.auth.mapping;

import com.munsun.auth.dto.UserInfoDto;
import com.munsun.auth.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserInfoDto userInfoDto);
}
