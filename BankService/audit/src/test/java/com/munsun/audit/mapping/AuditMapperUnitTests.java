package com.munsun.audit.mapping;

import com.munsun.audit.dto.AuditActionRequestDto;
import com.munsun.audit.dto.OperationType;
import com.munsun.audit.dto.ServiceType;
import com.munsun.audit.entities.AuditAction;
import com.munsun.audit.integrations.kafka.payload.AuditActionPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AuditMapperImpl.class)
public class AuditMapperUnitTests {
    @Autowired
    private AuditMapper mapper;

    @DisplayName("Test mapping from auditActionPayload in AuditAction")
    @Test
    public void givenAuditActionPayload_whenMapToEntity_thenReturnAuditAction() {
        var auditActionPayload = new AuditActionPayload(OperationType.START, ServiceType.DEAL, "Test message{id=1, message=test}");
        var expectedAuditAction = AuditAction.builder()
                .type(OperationType.START)
                .service(ServiceType.DEAL)
                .message("Test message{id=1, message=test}")
                .build();

        var actualAuditAction = mapper.toEntity(auditActionPayload);

        assertThat(actualAuditAction)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields(AuditAction.Fields.uuid)
                .isEqualTo(expectedAuditAction);
    }

    @DisplayName("Test mapping from auditActionDto in AuditAction")
    @Test
    public void givenAuditActionDto_whenMapToEntity_thenReturnAuditAction() {
        var auditActionDto = new AuditActionRequestDto(null, OperationType.START, ServiceType.DEAL);
        var expectedAuditAction = AuditAction.builder()
                .type(OperationType.START)
                .service(ServiceType.DEAL)
                .message("Test message{id=1, message=test}")
                .build();

        var actualAuditAction = mapper.toEntity(auditActionDto);

        assertThat(actualAuditAction)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields(AuditAction.Fields.uuid, AuditAction.Fields.message)
                .isEqualTo(expectedAuditAction);
    }
}