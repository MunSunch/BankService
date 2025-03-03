package com.munsun.calculator.services.impl.providers.impl;

import com.munsun.calculator.dto.CreditDto;
import com.munsun.calculator.dto.LoanOfferDto;
import com.munsun.calculator.dto.PaymentScheduleElementDto;
import com.munsun.calculator.dto.ScoringDataDto;
import com.munsun.calculator.utils.annotations.Generated;
import com.munsun.calculator.services.impl.utils.SimpleScoringInfoDto;
import com.munsun.calculator.services.impl.providers.CreditCalculator;
import com.munsun.calculator.services.impl.providers.impl.filters.impl.soft.InsuranceSoftScoringFilter;
import com.munsun.calculator.services.impl.providers.impl.filters.impl.soft.SalaryClientSoftScoringFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class DefaultCreditCalculator implements CreditCalculator {
    @Value("${service.calculator.round}")
    private Integer countDigitAfterPoint;

    @Override
    public CreditDto calculateWithAnnuietyPayments(ScoringDataDto scoringDataDto, BigDecimal newRate, BigDecimal otherService) {
        BigDecimal monthlyRate = getMonthlyRate(newRate);
        BigDecimal totalAmount = scoringDataDto.getAmount().add(otherService);
        BigDecimal monthlyPayment = getMonthlyPayment(totalAmount, scoringDataDto.getTerm(), newRate);
        List<PaymentScheduleElementDto> schedule = getSchedule(monthlyPayment, monthlyRate, totalAmount, scoringDataDto.getTerm());
        BigDecimal psk = getPsk(schedule);

        return new CreditDto(
                scoringDataDto.getAmount(),
                scoringDataDto.getTerm(),
                monthlyPayment.setScale(countDigitAfterPoint, RoundingMode.HALF_EVEN),
                newRate,
                psk,
                scoringDataDto.getIsInsuranceEnabled(),
                scoringDataDto.getIsSalaryClient(),
                schedule
        );
    }

    @Override
    public List<LoanOfferDto> generateLoanOfferWithAnnuietyPayments(BigDecimal amount, Integer term, List<SimpleScoringInfoDto> listInfo) {
        List<LoanOfferDto> loanOffers = new ArrayList<>();
        for(var info: listInfo) {
            BigDecimal totalAmount = amount.add(info.rateAndOtherServiceDto().otherService());
            BigDecimal newRate = info.rateAndOtherServiceDto().newRate();
            BigDecimal monthlyRate = getMonthlyRate(newRate);
            BigDecimal monthlyPayment = getMonthlyPayment(totalAmount, term, newRate).setScale(countDigitAfterPoint, RoundingMode.HALF_EVEN);
            List<PaymentScheduleElementDto> schedule = getSchedule(monthlyPayment, monthlyRate, totalAmount, term);
            BigDecimal psk = getPsk(schedule);

            boolean isSalaryClient = info.filters().get(SalaryClientSoftScoringFilter.class.getSimpleName());
            boolean isInsuranceEnabled = info.filters().get(InsuranceSoftScoringFilter.class.getSimpleName());

            loanOffers.add(new LoanOfferDto(
                    UUID.randomUUID(),
                    amount,
                    psk,
                    term,
                    monthlyPayment,
                    newRate,
                    isInsuranceEnabled,
                    isSalaryClient
            ));
        }
        return loanOffers;
    }

    @Generated
    private BigDecimal getPsk(List<PaymentScheduleElementDto> schedule) {
        BigDecimal summaInterestRatePayment = schedule.stream()
                .map(PaymentScheduleElementDto::getInterestPayment)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal summaDebtPayment = schedule.stream()
                .map(PaymentScheduleElementDto::getDebtPayment)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return summaInterestRatePayment.add(summaDebtPayment);
    }

    @Generated
    private List<PaymentScheduleElementDto> getSchedule(BigDecimal monthlyPayment, BigDecimal monthlyRate, BigDecimal totalAmount, Integer term) {
        List<PaymentScheduleElementDto> schedule = new ArrayList<>();
        BigDecimal remainingDebt = totalAmount;
        for (int i = 1; i <= term; i++) {
            LocalDate month = LocalDate.now().plusMonths(i);
            BigDecimal interestPayment = remainingDebt.multiply(monthlyRate);
            BigDecimal debtPayment = monthlyPayment.subtract(interestPayment);
            remainingDebt = remainingDebt.subtract(debtPayment);

            PaymentScheduleElementDto dto = new PaymentScheduleElementDto(
                    i,
                    month,
                    monthlyPayment.setScale(countDigitAfterPoint, RoundingMode.HALF_EVEN),
                    interestPayment.setScale(countDigitAfterPoint, RoundingMode.HALF_EVEN),
                    debtPayment.setScale(countDigitAfterPoint, RoundingMode.HALF_EVEN),
                    remainingDebt.setScale(countDigitAfterPoint, RoundingMode.HALF_EVEN)
            );
            schedule.add(dto);
        }
        return schedule;
    }

    @Generated
    private BigDecimal getMonthlyPayment(BigDecimal totalAmount, Integer term, BigDecimal newRate) {
        BigDecimal monthlyPercent = getMonthlyRate(newRate);
        return totalAmount.multiply(
                monthlyPercent.add(
                        monthlyPercent.divide(
                                BigDecimal.ONE.add(monthlyPercent).pow(term).subtract(BigDecimal.ONE),
                                new MathContext(MathContext.DECIMAL128.getPrecision(), RoundingMode.HALF_EVEN)
                        )
                )
        );
    }

    @Generated
    private BigDecimal getMonthlyRate(BigDecimal newRate) {
        return newRate.divide(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(12), new MathContext(MathContext.DECIMAL128.getPrecision(), RoundingMode.HALF_EVEN));
    }

    @Override
    public CreditDto calculateWithDifferentPayments(ScoringDataDto scoringDataDto, BigDecimal newRate, BigDecimal otherService) {
        BigDecimal totalAmount = scoringDataDto.getAmount().add(otherService);
        BigDecimal debtPayment = totalAmount.divide(BigDecimal.valueOf(scoringDataDto.getTerm()), new MathContext(MathContext.DECIMAL128.getPrecision(), RoundingMode.HALF_EVEN));
        BigDecimal ratePercents = getRatePercents(newRate);
        var schedule = getSchedule(totalAmount, scoringDataDto.getTerm(), ratePercents, debtPayment);
        BigDecimal psk = getPsk(schedule);
        BigDecimal averageMonthlyPayment = getAverageMonthlyPayment(schedule).setScale(countDigitAfterPoint, RoundingMode.HALF_EVEN);

        return new CreditDto(
                scoringDataDto.getAmount(),
                scoringDataDto.getTerm(),
                averageMonthlyPayment,
                newRate,
                psk,
                scoringDataDto.getIsInsuranceEnabled(),
                scoringDataDto.getIsSalaryClient(),
                schedule
        );
    }

    @Override
    public List<LoanOfferDto> generateLoanOfferWithDifferentPayments(BigDecimal amount, Integer term, List<SimpleScoringInfoDto> listInfo) {
        List<LoanOfferDto> loanOffers = new ArrayList<>();
        for(var info: listInfo) {
            BigDecimal totalAmount = amount.add(info.rateAndOtherServiceDto().otherService());
            BigDecimal newRate = info.rateAndOtherServiceDto().newRate();
            BigDecimal ratePercents = getRatePercents(newRate);
            BigDecimal debtPayment = totalAmount.divide(BigDecimal.valueOf(term), new MathContext(MathContext.DECIMAL128.getPrecision(), RoundingMode.HALF_EVEN));
            List<PaymentScheduleElementDto> schedule = getSchedule(totalAmount, term, ratePercents, debtPayment);
            BigDecimal psk = getPsk(schedule);
            BigDecimal averageMonthlyPayment = getAverageMonthlyPayment(schedule).setScale(countDigitAfterPoint, RoundingMode.HALF_EVEN);

            boolean isSalaryClient = info.filters().get(SalaryClientSoftScoringFilter.class.getSimpleName());
            boolean isInsuranceEnabled = info.filters().get(InsuranceSoftScoringFilter.class.getSimpleName());

            loanOffers.add(new LoanOfferDto(
                    UUID.randomUUID(),
                    amount,
                    psk,
                    term,
                    averageMonthlyPayment,
                    newRate,
                    isInsuranceEnabled,
                    isSalaryClient
            ));
        }
        return loanOffers;
    }

    @Generated
    private BigDecimal getAverageMonthlyPayment(List<PaymentScheduleElementDto> schedule) {
        return schedule.stream()
                .map(PaymentScheduleElementDto::getTotalPayment)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(schedule.size()), new MathContext(countDigitAfterPoint, RoundingMode.HALF_EVEN));
    }

    @Generated
    private List<PaymentScheduleElementDto> getSchedule(BigDecimal totalAmount, Integer term, BigDecimal ratePercents, BigDecimal debtPayment) {
        List<PaymentScheduleElementDto> schedule = new ArrayList<>();
        BigDecimal remainingDebt = totalAmount;
        for (int i = 1; i <= term; i++) {
            LocalDate currentDate = LocalDate.now().plusMonths(i);
            BigDecimal interestPayment = remainingDebt.multiply(ratePercents)
                    .multiply(BigDecimal.valueOf(currentDate.getDayOfMonth())
                            .divide(BigDecimal.valueOf(currentDate.getDayOfYear()),
                                    new MathContext(MathContext.DECIMAL128.getPrecision(), RoundingMode.HALF_EVEN)));
            BigDecimal monthlyPayment = debtPayment.add(interestPayment);
            remainingDebt = remainingDebt.subtract(debtPayment);

            PaymentScheduleElementDto dto = new PaymentScheduleElementDto(
                    i,
                    currentDate,
                    monthlyPayment.setScale(countDigitAfterPoint, RoundingMode.HALF_EVEN),
                    interestPayment.setScale(countDigitAfterPoint, RoundingMode.HALF_EVEN),
                    debtPayment.setScale(countDigitAfterPoint, RoundingMode.HALF_EVEN),
                    remainingDebt.setScale(countDigitAfterPoint, RoundingMode.HALF_EVEN)
            );
            schedule.add(dto);
        }
        return schedule;
    }

    @Generated
    private BigDecimal getRatePercents(BigDecimal newRate) {
        return newRate.divide(BigDecimal.valueOf(100));
    }
}
