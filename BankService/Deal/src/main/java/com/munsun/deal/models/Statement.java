package com.munsun.deal.models;

import com.munsun.deal.dto.LoanOfferDto;
import com.munsun.deal.dto.TypePayments;
import com.munsun.deal.models.enums.ApplicationStatus;
import com.munsun.deal.models.enums.ChangeType;
import com.munsun.deal.models.json.StatusHistory;
import jakarta.persistence.*;
import liquibase.sql.Sql;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "statements")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class Statement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "statement_id")
    UUID statementId;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    Client client;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="credit_id", referencedColumnName = "credit_id")
    Credit credit;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_status")
    ApplicationStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_payments")
    TypePayments typePayments;

    @Column(name = "creation_date", columnDefinition = "timestamp")
    LocalDate creationDate;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "applied_offer", columnDefinition = "text")
    LoanOfferDto appliedOffer;

    @Column(name = "sign_date", columnDefinition = "timestamp")
    LocalDate signDate;

    @Column(name = "ses_code")
    String code;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "status_history", columnDefinition = "jsonb")
    List<StatusHistory> statusHistory;

    public void setStatus(ApplicationStatus status, ChangeType type) {
        this.status = status;
        addStatusHistory(StatusHistory.builder()
                .type(ChangeType.AUTOMATIC)
                .time(LocalDate.now())
                .type(type)
                .build());
    }

    public void addStatusHistory(StatusHistory status) {
        if(statusHistory == null) {
            statusHistory = new ArrayList<>();
        }
        statusHistory.add(status);
    }
}
