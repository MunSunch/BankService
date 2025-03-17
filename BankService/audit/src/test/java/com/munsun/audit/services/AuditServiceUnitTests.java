package com.munsun.audit.services;

import com.munsun.audit.dto.OperationType;
import com.munsun.audit.dto.ServiceType;
import com.munsun.audit.integrations.kafka.payload.AuditActionPayload;
import com.munsun.audit.mapping.AuditMapper;
import com.munsun.audit.repositories.AuditRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = AuditService.class)
public class AuditServiceUnitTests {
    @MockBean
    private AuditRepository auditRepository;
    @MockBean
    private AuditMapper mapper;
    @Autowired
    private AuditService auditService;

    @DisplayName("Test create new log in correct order")
    @Test
    public void givenCallCreate_whenCreate_thenCorrectOrderCallsMapperAndRepository() {
        auditService.createNewLog(new AuditActionPayload(OperationType.START, ServiceType.DEAL, "test"));

        var inOrder = Mockito.inOrder(auditRepository, mapper);
            inOrder.verify(mapper).toEntity(any(AuditActionPayload.class));
            inOrder.verify(auditRepository).save(any());
    }
}
