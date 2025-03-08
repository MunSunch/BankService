package com.munsun.auth.mapping;

import com.munsun.auth.entities.User;
import com.munsun.auth.utils.MockDataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = UserMapperImpl.class)
public class UserMapperUnitTests {
    @Autowired
    private UserMapper userMapper;

    @DisplayName("Test mapping UserInfoDto to User entity")
    @Test
    public void givenUserInfoDto_whenMapToUser_thenValidUser() {
        var userInfoDto = MockDataUtils.getUserInfoDto_RoleUser_UsernameMunir();
        var expectedUserEntity = MockDataUtils.getUserEntityPersistent_RoleUser_UsernameMunir();

        var actual = userMapper.toEntity(userInfoDto);

        assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields(User.Fields.uuid, User.Fields.role)
                    .isEqualTo(expectedUserEntity);
    }

    @DisplayName("Test mapping null")
    @Test
    public void givenNull_whenMapToUser_thenReturnNull() {
        var actual = userMapper.toEntity(null);

        assertThat(actual)
                .isNull();
    }
}
