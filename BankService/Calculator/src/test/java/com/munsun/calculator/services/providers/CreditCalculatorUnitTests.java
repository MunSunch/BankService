package com.munsun.calculator.services.providers;

import com.munsun.calculator.dto.*;
import com.munsun.calculator.utils.TestUtils;
import com.munsun.calculator.services.impl.utils.SimpleScoringInfoDto;
import com.munsun.calculator.services.impl.providers.CreditCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CreditCalculatorUnitTests {
    @Autowired
    private CreditCalculator calculator;

    @DisplayName("Test calculate credit on correct of calculations")
    @Test
    public void givenData_whenCalculate_thenCreditPermanentMonthlyPaymentAndLastRemainderZero() {
        ScoringDataDto testData = TestUtils.getScoringDataDto();
        BigDecimal newRate = new BigDecimal("12");
        BigDecimal otherService = BigDecimal.ZERO;
        BigDecimal expectedMonthlyPayment = new BigDecimal("888.49");

        CreditDto credit = calculator.calculateWithAnnuietyPayments(testData, newRate, otherService);
        PaymentScheduleElementDto lastPayment = credit.getPaymentSchedule().get(credit.getPaymentSchedule().size()-1);

        assertThat(credit.getMonthlyPayment())
                .isEqualTo(expectedMonthlyPayment);
        assertThat(credit.getPaymentSchedule())
                .filteredOn(monthlySchedule -> monthlySchedule.getTotalPayment().equals(expectedMonthlyPayment));
        assertThat(lastPayment)
                .extracting(payment -> lastPayment.getRemainingDebt())
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
                ScoringDataDto.GenderEnum.MALE,
                "msunchalyaev@gmail.com",
                LocalDate.of(1998, 5, 26),
                "1234",
                "567890",
                LocalDate.of(2014, 8, 10),
                "ГУ МВД РОССИИ",
                ScoringDataDto.MaritalStatusEnum.SINGLE,
                27,
                new EmploymentDto(
                        EmploymentDto.EmploymentStatusEnum.SELF_EMPLOYED,
                        "123456789011231212",
                        BigDecimal.valueOf(41_000),
                        EmploymentDto.PositionEnum.MID_MANAGER,
                        12,
                        4
                ),
                "79873022923",
                false,
                false);

        CreditDto credit = calculator.calculateWithAnnuietyPayments(scoringDataDto, newRate, otherService);
        PaymentScheduleElementDto lastPayment = credit.getPaymentSchedule().get(credit.getPaymentSchedule().size()-1);

        assertThat(lastPayment)
                .extracting(PaymentScheduleElementDto::getRemainingDebt)
                .isEqualTo(expectedRemainingDebt);
    }

    @DisplayName("Test generate loan offer without insurance and not client of the bank")
    @Test
    public void givenDataWithNotSalaryClientAndNotInsuranceForCredit_whenGenerateOffer_thenReturnLoanOffer() {
        LoanOfferDto expected = TestUtils.getAnnuitentPaymentLoanOfferDtoAmount10_000Term12NotSalaryClentAndNotInsurance();
        SimpleScoringInfoDto info = TestUtils.getSimpleScoringInfoDtoNotSalaryClientAndNotInsurance();

        LoanOfferDto actual = calculator.generateLoanOfferWithAnnuietyPayments(expected.getRequestedAmount(), expected.getTerm(), List.of(info))
                .get(0);

        assertThat(actual)
                .extracting(LoanOfferDto::getIsSalaryClient,
                        LoanOfferDto::getIsInsuranceEnabled,
                        LoanOfferDto::getMonthlyPayment,
                        LoanOfferDto::getRequestedAmount,
                        LoanOfferDto::getTotalAmount,
                        LoanOfferDto::getTerm,
                        LoanOfferDto::getRate)
                .containsExactly(expected.getIsSalaryClient(),
                        expected.getIsInsuranceEnabled(),
                        expected.getMonthlyPayment(),
                        expected.getRequestedAmount(),
                        expected.getTotalAmount(),
                        expected.getTerm(),
                        expected.getRate());
    }

    @DisplayName("Test generate loan offer without insurance, client of the bank")
    @Test
    public void givenDataWithSalaryClientAndNotInsuranceForCredit_whenGenerateOffer_thenReturnLoanOffer() {
        LoanOfferDto expected = TestUtils.getAnnuitentPaymentLoanOfferDtoAmount10_000Term12SalaryClient();
        SimpleScoringInfoDto info = TestUtils.getSimpleScoringInfoDtoSalaryClient();

        LoanOfferDto actual = calculator.generateLoanOfferWithAnnuietyPayments(expected.getRequestedAmount(), expected.getTerm(), List.of(info))
                .get(0);

        assertThat(actual)
                .extracting(LoanOfferDto::getIsSalaryClient,
                        LoanOfferDto::getIsInsuranceEnabled,
                        LoanOfferDto::getMonthlyPayment,
                        LoanOfferDto::getRequestedAmount,
                        LoanOfferDto::getTotalAmount,
                        LoanOfferDto::getTerm,
                        LoanOfferDto::getRate)
                .containsExactly(expected.getIsSalaryClient(),
                        expected.getIsInsuranceEnabled(),
                        expected.getMonthlyPayment(),
                        expected.getRequestedAmount(),
                        expected.getTotalAmount(),
                        expected.getTerm(),
                        expected.getRate());
    }

    @DisplayName("Test generate loan offer with insurance, not client of the bank")
    @Test
    public void givenDataWithNotSalaryClientAndInsuranceEnabledForCredit_whenGenerateOffer_thenReturnLoanOffer() {
        LoanOfferDto expected = TestUtils.getAnnuitentPaymentLoanOfferDtoAmount10_000Term12Insurance();
        SimpleScoringInfoDto info = TestUtils.getSimpleScoringInfoDtoInsurance();

        LoanOfferDto actual = calculator.generateLoanOfferWithAnnuietyPayments(expected.getRequestedAmount(), expected.getTerm(), List.of(info))
                .get(0);

        assertThat(actual)
                .extracting(LoanOfferDto::getIsSalaryClient,
                        LoanOfferDto::getIsInsuranceEnabled,
                        LoanOfferDto::getMonthlyPayment,
                        LoanOfferDto::getRequestedAmount,
                        LoanOfferDto::getTotalAmount,
                        LoanOfferDto::getTerm,
                        LoanOfferDto::getRate)
                .containsExactly(expected.getIsSalaryClient(),
                        expected.getIsInsuranceEnabled(),
                        expected.getMonthlyPayment(),
                        expected.getRequestedAmount(),
                        expected.getTotalAmount(),
                        expected.getTerm(),
                        expected.getRate());
    }

    @DisplayName("Test generate loan offer with insurance and client of the bank")
    @Test
    public void givenDataWithSalaryClientAndInsuranceEnabledForCredit_whenGenerateOffer_thenReturnLoanOffer() {
        LoanOfferDto expected = TestUtils.getAnnuitentPaymentLoanOfferDtoAmount10_000Term12();
        SimpleScoringInfoDto info = TestUtils.getSimpleScoringInfoDtoSalaryAndInsurance();

        LoanOfferDto actual = calculator.generateLoanOfferWithAnnuietyPayments(expected.getRequestedAmount(), expected.getTerm(), List.of(info))
                .get(0);

        assertThat(actual)
                .extracting(LoanOfferDto::getIsSalaryClient,
                        LoanOfferDto::getIsInsuranceEnabled,
                        LoanOfferDto::getMonthlyPayment,
                        LoanOfferDto::getRequestedAmount,
                        LoanOfferDto::getTotalAmount,
                        LoanOfferDto::getTerm,
                        LoanOfferDto::getRate)
                .containsExactly(expected.getIsSalaryClient(),
                        expected.getIsInsuranceEnabled(),
                        expected.getMonthlyPayment(),
                        expected.getRequestedAmount(),
                        expected.getTotalAmount(),
                        expected.getTerm(),
                        expected.getRate());
    }
}
