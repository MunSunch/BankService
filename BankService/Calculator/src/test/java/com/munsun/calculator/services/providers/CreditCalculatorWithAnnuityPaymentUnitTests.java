package com.munsun.calculator.services.providers;

import com.munsun.calculator.TestUtils;
import com.munsun.calculator.dto.request.EmploymentDto;
import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.dto.request.enums.EmploymentStatus;
import com.munsun.calculator.dto.request.enums.Gender;
import com.munsun.calculator.dto.request.enums.MaritalStatus;
import com.munsun.calculator.dto.request.enums.Position;
import com.munsun.calculator.dto.response.CreditDto;
import com.munsun.calculator.dto.response.LoanOfferDto;
import com.munsun.calculator.dto.response.PaymentScheduleElementDto;
import com.munsun.calculator.dto.utils.SimpleScoringInfoDto;
import com.munsun.calculator.services.impl.providers.CreditCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("default")
public class CreditCalculatorWithAnnuityPaymentUnitTests {
    @Autowired
    private CreditCalculator calculator;

    @DisplayName("Test calculate credit on correct of calculations")
    @Test
    public void givenData_whenCalculate_thenCreditPermanentMonthlyPaymentAndLastRemainderZero() {
        ScoringDataDto testData = TestUtils.getScoringDataDto();
        BigDecimal newRate = new BigDecimal("12");
        BigDecimal otherService = BigDecimal.ZERO;
        BigDecimal expectedMonthlyPayment = new BigDecimal("888.49");

        CreditDto credit = calculator.calculate(testData, newRate, otherService);
        PaymentScheduleElementDto lastPayment = credit.paymentSchedule().get(credit.paymentSchedule().size()-1);

        assertThat(credit.monthlyPayment())
                .isEqualTo(expectedMonthlyPayment);
        assertThat(credit.paymentSchedule())
                .filteredOn(monthlySchedule -> monthlySchedule.totalPayment().equals(expectedMonthlyPayment));
        assertThat(lastPayment)
                .extracting(payment -> lastPayment.remainingDebt())
                .isEqualTo(new BigDecimal("0.00"));
    }

    @DisplayName("Test calculate credit")
    @ParameterizedTest
    @CsvSource({"1000, 12", "10_000, 18", "100_000, 24", "1_000_000, 30", "10_000_000, 35"})
    public void givenAmountAndTermCreditAndRateAndOtherService_whenCalculateCredit_thenLastRemainderZero(long amount, int term) {
        BigDecimal newRate = new BigDecimal("12");
        BigDecimal otherService = BigDecimal.ZERO;
        BigDecimal expectedRemainingDebt = new BigDecimal("0.00");
        ScoringDataDto scoringDataDto = new ScoringDataDto(
                BigDecimal.valueOf(amount),
                term,
                "Munir",
                "Sunchalyaev",
                "Raisovich",
                Gender.MALE,
                "msunchalyaev@gmail.com",
                LocalDate.of(1998, 5, 26),
                "1234",
                "567890",
                LocalDate.of(2014, 8, 10),
                "ГУ МВД РОССИИ",
                MaritalStatus.SINGLE,
                27,
                new EmploymentDto(
                        EmploymentStatus.SELF_EMPLOYED,
                        "123456789011231212",
                        BigDecimal.valueOf(41_000),
                        Position.MIDDLE_MANAGER,
                        12,
                        4
                ),
                "79873022923",
                false,
                false);

        CreditDto credit = calculator.calculate(scoringDataDto, newRate, otherService);
        PaymentScheduleElementDto lastPayment = credit.paymentSchedule().get(credit.paymentSchedule().size()-1);

        assertThat(lastPayment)
                .extracting(PaymentScheduleElementDto::remainingDebt)
                .isEqualTo(expectedRemainingDebt);
    }

    @DisplayName("Test generate loan offer without insurance and not client of the bank")
    @Test
    public void givenDataWithNotSalaryClientAndNotInsuranceForCredit_whenGenerateOffer_thenReturnLoanOffer() {
        LoanOfferDto expected = TestUtils.getAnnuitentPaymentLoanOfferDtoAmount10_000Term12NotSalaryClentAndNotInsurance();
        SimpleScoringInfoDto info = TestUtils.getSimpleScoringInfoDtoNotSalaryClientAndNotInsurance();

        LoanOfferDto actual = calculator.generateLoanOffer(expected.requestedAmount(), expected.term(), List.of(info))
                .get(0);

        assertThat(actual)
                .extracting(LoanOfferDto::isSalaryClient,
                        LoanOfferDto::isInsuranceEnabled,
                        LoanOfferDto::monthlyPayment,
                        LoanOfferDto::requestedAmount,
                        LoanOfferDto::totalAmount,
                        LoanOfferDto::term,
                        LoanOfferDto::rate)
                .containsExactly(expected.isSalaryClient(),
                        expected.isInsuranceEnabled(),
                        expected.monthlyPayment(),
                        expected.requestedAmount(),
                        expected.totalAmount(),
                        expected.term(),
                        expected.rate());
    }

    @DisplayName("Test generate loan offer without insurance, client of the bank")
    @Test
    public void givenDataWithSalaryClientAndNotInsuranceForCredit_whenGenerateOffer_thenReturnLoanOffer() {
        LoanOfferDto expected = TestUtils.getAnnuitentPaymentLoanOfferDtoAmount10_000Term12SalaryClient();
        SimpleScoringInfoDto info = TestUtils.getSimpleScoringInfoDtoSalaryClient();

        LoanOfferDto actual = calculator.generateLoanOffer(expected.requestedAmount(), expected.term(), List.of(info))
                .get(0);

        assertThat(actual)
                .extracting(LoanOfferDto::isSalaryClient,
                        LoanOfferDto::isInsuranceEnabled,
                        LoanOfferDto::monthlyPayment,
                        LoanOfferDto::requestedAmount,
                        LoanOfferDto::totalAmount,
                        LoanOfferDto::term,
                        LoanOfferDto::rate)
                .containsExactly(expected.isSalaryClient(),
                        expected.isInsuranceEnabled(),
                        expected.monthlyPayment(),
                        expected.requestedAmount(),
                        expected.totalAmount(),
                        expected.term(),
                        expected.rate());
    }

    @DisplayName("Test generate loan offer with insurance, not client of the bank")
    @Test
    public void givenDataWithNotSalaryClientAndInsuranceEnabledForCredit_whenGenerateOffer_thenReturnLoanOffer() {
        LoanOfferDto expected = TestUtils.getAnnuitentPaymentLoanOfferDtoAmount10_000Term12Insurance();
        SimpleScoringInfoDto info = TestUtils.getSimpleScoringInfoDtoInsurance();

        LoanOfferDto actual = calculator.generateLoanOffer(expected.requestedAmount(), expected.term(), List.of(info))
                .get(0);

        assertThat(actual)
                .extracting(LoanOfferDto::isSalaryClient,
                        LoanOfferDto::isInsuranceEnabled,
                        LoanOfferDto::monthlyPayment,
                        LoanOfferDto::requestedAmount,
                        LoanOfferDto::totalAmount,
                        LoanOfferDto::term,
                        LoanOfferDto::rate)
                .containsExactly(expected.isSalaryClient(),
                        expected.isInsuranceEnabled(),
                        expected.monthlyPayment(),
                        expected.requestedAmount(),
                        expected.totalAmount(),
                        expected.term(),
                        expected.rate());
    }

    @DisplayName("Test generate loan offer with insurance and client of the bank")
    @Test
    public void givenDataWithSalaryClientAndInsuranceEnabledForCredit_whenGenerateOffer_thenReturnLoanOffer() {
        LoanOfferDto expected = TestUtils.getAnnuitentPaymentLoanOfferDtoAmount10_000Term12();
        SimpleScoringInfoDto info = TestUtils.getSimpleScoringInfoDtoSalaryAndInsurance();

        LoanOfferDto actual = calculator.generateLoanOffer(expected.requestedAmount(), expected.term(), List.of(info))
                .get(0);

        assertThat(actual)
                .extracting(LoanOfferDto::isSalaryClient,
                        LoanOfferDto::isInsuranceEnabled,
                        LoanOfferDto::monthlyPayment,
                        LoanOfferDto::requestedAmount,
                        LoanOfferDto::totalAmount,
                        LoanOfferDto::term,
                        LoanOfferDto::rate)
                .containsExactly(expected.isSalaryClient(),
                        expected.isInsuranceEnabled(),
                        expected.monthlyPayment(),
                        expected.requestedAmount(),
                        expected.totalAmount(),
                        expected.term(),
                        expected.rate());
    }

    @DisplayName("Test generate list of loans")
    @Test
    public void givenListSimpleScoringInfoDto_whenGenerateLoanOffer_thenReturnListLoanOffers() {
        List<SimpleScoringInfoDto> info = TestUtils.getSimpleScoringInfoDto();
        List<LoanOfferDto> expectedOffers = TestUtils.getAnnuitentPaymentListLoanOffersDtoAmount10_000Term12();

        List<LoanOfferDto> actualOffers = calculator.generateLoanOffer(new BigDecimal("10000"), 12, info);

        assertThat(actualOffers)
                .isNotNull()
                .hasSize(expectedOffers.size())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("statementId")
                .isEqualTo(expectedOffers);
    }
}
