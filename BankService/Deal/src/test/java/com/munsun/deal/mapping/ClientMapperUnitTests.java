package com.munsun.deal.mapping;

import com.munsun.deal.utils.TestUtils;
import com.munsun.deal.dto.LoanStatementRequestDto;
import com.munsun.deal.models.Client;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {ClientMapperImpl.class})
public class ClientMapperUnitTests {
    @Autowired
    private ClientMapper mapper;

    @DisplayName("Test map LoanStatementRequestDto to Client")
    @Test
    public void givenTestLoanStatementRequestAndExpectedClient_whenMapLoanStatementToClient_thenEqualsTrueExpectedAndActualClient() {
        LoanStatementRequestDto loanStatementRequest = TestUtils.getLoanStatementRequestDto();
        Client expectedClient = TestUtils.getClientTransient();

        Client actualClient = mapper.toClient(loanStatementRequest);

        assertThat(actualClient)
                .usingRecursiveComparison()
                .ignoringFields("clientId", "gender", "maritalStatus",
                        "dependentAmount", "employment", "accountNumber", "passport.passportUUID",
                        "passport.issueBranch", "passport.issueDate")
                .isEqualTo(expectedClient);
    }
}
