package com.munsun.deal.repositories;

import com.munsun.deal.utils.PostgresContainer;
import com.munsun.deal.utils.TestUtils;
import com.munsun.deal.models.Client;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ClientRepositoryUnitTests extends PostgresContainer {
    @Autowired
    private ClientRepository clientRepository;

    @DisplayName("Test save client")
    @Test
    public void givenClientTransient_whenSaveClient_thenFindByIdReturnClientPersistent() {
        Client client = TestUtils.getClientTransient();

        UUID savedClientId = clientRepository.save(client).getClientId();
        Optional<Client> savedClient = clientRepository.findById(savedClientId);

        assertThat(savedClient)
                .isPresent().get()
                .usingRecursiveComparison()
                .ignoringFields("clientId")
                .isEqualTo(client);
        assertThat(savedClient)
                .get()
                .extracting(Client::getClientId)
                .isNotNull();
    }
}
