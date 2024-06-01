package com.munsun.calculator.services.impl.providers.impl;

import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.dto.response.CreditDto;
import com.munsun.calculator.dto.response.LoanOfferDto;
import com.munsun.calculator.dto.response.PaymentScheduleElementDto;
import com.munsun.calculator.dto.utils.SimpleScoringInfoDto;
import com.munsun.calculator.services.impl.providers.CreditCalculator;
import com.munsun.calculator.services.impl.providers.impl.filters.impl.soft.InsuranceSoftScoringFilter;
import com.munsun.calculator.services.impl.providers.impl.filters.impl.soft.SalaryClientSoftScoringFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Profile("default")
public class CreditCalculatorWithAnnuityPayments implements CreditCalculator {
    @Value("${service.calculator.round}")
    private Integer countDigitAfterPoint;

    @Override
    public List<LoanOfferDto> generateLoanOffer(BigDecimal amount, Integer term, List<SimpleScoringInfoDto> listInfo) {
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

    @Override
    public CreditDto calculate(ScoringDataDto scoringDataDto, BigDecimal newRate, BigDecimal otherService) {
        BigDecimal monthlyRate = getMonthlyRate(newRate);
        BigDecimal totalAmount = scoringDataDto.amount().add(otherService);
        BigDecimal monthlyPayment = getMonthlyPayment(totalAmount, scoringDataDto.term(), newRate);
        List<PaymentScheduleElementDto> schedule = getSchedule(monthlyPayment, monthlyRate, totalAmount, scoringDataDto.term());
        BigDecimal psk = getPsk(schedule);

        return new CreditDto(
                scoringDataDto.amount(),
                scoringDataDto.term(),
                monthlyPayment.setScale(countDigitAfterPoint, RoundingMode.HALF_EVEN),
                newRate,
                psk,
                scoringDataDto.isInsuranceEnabled(),
                scoringDataDto.isSalaryClient(),
                schedule
        );
    }

    private BigDecimal getPsk(List<PaymentScheduleElementDto> schedule) {
        BigDecimal summaInterestRatePayment = schedule.stream()
                .map(PaymentScheduleElementDto::interestPayment)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal summaDebtPayment = schedule.stream()
                .map(PaymentScheduleElementDto::debtPayment)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return summaInterestRatePayment.add(summaDebtPayment);
    }

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

    private BigDecimal getMonthlyRate(BigDecimal newRate) {
        return newRate.divide(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(12), new MathContext(MathContext.DECIMAL128.getPrecision(), RoundingMode.HALF_EVEN));
    }
}
