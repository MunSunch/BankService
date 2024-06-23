package com.munsun.deal.models;

import com.munsun.deal.models.enums.Gender;
import com.munsun.deal.models.enums.MaritalStatus;
import com.munsun.deal.models.json.Employment;
import com.munsun.deal.models.json.Passport;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "client_id")
    UUID clientId;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "middle_name")
    String middleName;

    @Column(name = "birth_date", columnDefinition = "timestamp")
    LocalDate birthdate;

    @Column(name = "email")
    String email;

    @Enumerated(EnumType.STRING)
    @Column(name="gender")
    Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name="marital_status")
    MaritalStatus maritalStatus;

    @Column(name = "dependent_amount")
    Integer dependentAmount;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "passport_id", columnDefinition = "jsonb")
    Passport passport;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "employment_id", columnDefinition = "jsonb")
    Employment employment;

    @Column(name = "account_number")
    String accountNumber;
}
