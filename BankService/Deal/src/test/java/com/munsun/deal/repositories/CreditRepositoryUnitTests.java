package com.munsun.deal.repositories;

import com.munsun.deal.models.Credit;
import com.munsun.deal.utils.PostgresContainer;
import com.munsun.deal.utils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CreditRepositoryUnitTests extends PostgresContainer {
    @Autowired
    private CreditRepository repository;

    @DisplayName("Test save credit")
    @Test
    public void givenCreditTransient_whenSaveCredit_thenFindByIdReturnSavedCredit() {
        Credit credit = TestUtils.getCredit();

        repository.save(credit);
        Optional<Credit> persistentCredit = repository.findById(credit.getCreditId());

        assertThat(persistentCredit)
                .isPresent().get()
                .extracting(Credit::getCreditId)
                .isNotNull();
    }
}
