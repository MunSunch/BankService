package com.munsun.audit.repositories;

import com.munsun.audit.dto.OperationType;
import com.munsun.audit.dto.ServiceType;
import com.munsun.audit.entities.AuditAction;
import com.munsun.audit.utils.LoadTestApplicationContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class AuditRepositoryIntegrationTests extends LoadTestApplicationContext {
    @Autowired
    private AuditRepository auditRepository;

    @DisplayName("Test save auditAction")
    @Test
    public void givenAuditActionTransient_whenSave_thenReturnAuditActionPersist() {
        var auditAction = AuditAction.builder()
                .type(OperationType.START)
                .service(ServiceType.DEAL)
                .message("Test message{id=1, message=test}")
                .build();

        var actual = auditRepository.save(auditAction);

        assertThat(actual)
                .isNotNull()
                .extracting(AuditAction.Fields.uuid)
                .isNotNull();
    }
}
