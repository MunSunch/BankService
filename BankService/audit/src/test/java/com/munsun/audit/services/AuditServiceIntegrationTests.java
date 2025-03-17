package com.munsun.audit.services;

import com.munsun.audit.repositories.AuditRepository;
import com.munsun.audit.utils.LoadTestApplicationContext;
import com.munsun.audit.utils.MockDataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.test.context.EmbeddedKafka;

import static org.assertj.core.api.Assertions.assertThat;

@EmbeddedKafka(topics = "audit")
public class AuditServiceIntegrationTests extends LoadTestApplicationContext {
    @Autowired
    private AuditService auditService;
    @Autowired
    private AuditRepository auditRepository;

    @DisplayName("Test get logs")
    @Test
    public void givenAuditLogs_whenGetLogs_thenReturnExpectedCountLogs() {
        var expectedCountLogs = 3;
        auditRepository.save(MockDataUtils.getMockAuditAction_DealStarted());
        auditRepository.save(MockDataUtils.getMockAuditAction_DealSuccess());
        auditRepository.save(MockDataUtils.getMockAuditAction_DealFailure());
        auditRepository.save(MockDataUtils.getMockAuditAction_StatementFailure());
        auditRepository.save(MockDataUtils.getMockAuditAction_StatementStarted());

        var actualListLogs = auditService.getLogs(MockDataUtils.getMockAuditActionDto_Deal(),
                                          Pageable.ofSize(10).withPage(0));

        assertThat(actualListLogs)
                .isNotNull()
                .hasSize(expectedCountLogs);
    }
}
