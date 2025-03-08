package com.munsun.auth.entities;

import com.munsun.auth.entities.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import java.util.UUID;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users", schema = "auth")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@FieldNameConstants
public class User {
    @Id
    @Column(name = "uuid")
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID uuid;

    @Column(name = "username", unique = true, nullable = false)
    String username;

    @Column(name = "password", unique = false, nullable = false)
    String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", unique = false, nullable = false)
    Role role;
}
