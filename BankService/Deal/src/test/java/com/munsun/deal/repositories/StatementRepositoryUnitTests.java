package com.munsun.deal.repositories;

import com.munsun.deal.utils.PostgresContainer;
import com.munsun.deal.utils.TestUtils;
import com.munsun.deal.models.Client;
import com.munsun.deal.models.Statement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StatementRepositoryUnitTests extends PostgresContainer {
    @Autowired
    private StatementRepository repository;

    @DisplayName("Test save transient statement with transient client")
    @Test
    public void givenStatementTransientWithClientTransient_whenSaveStatement_thenReturnSavedStatementWithSavedClient() {
        Statement testStatement = TestUtils.getStatementTransient();
            testStatement.setClient(TestUtils.getClientPersistent());

        UUID savedStatementId = repository.save(testStatement).getStatementId();
        Optional<Statement> savedStatement = repository.findById(savedStatementId);

        assertThat(savedStatement)
                .isPresent()
                .get()
                .extracting(Statement::getStatementId)
                .isNotNull();
        assertThat(savedStatement.get().getClient())
                .isNotNull()
                .extracting(Client::getClientId)
                .isNotNull();
    }
}
