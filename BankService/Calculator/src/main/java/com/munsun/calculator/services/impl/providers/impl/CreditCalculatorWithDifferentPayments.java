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
@Profile("differentiated_payment")
public class CreditCalculatorWithDifferentPayments implements CreditCalculator {
    @Value("${service.calculator.round}")
    private Integer countDigitAfterPoint;

    @Override
    public List<LoanOfferDto> generateLoanOffer(BigDecimal amount, Integer term, List<SimpleScoringInfoDto> listInfo) {
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

    @Override
    public CreditDto calculate(ScoringDataDto scoringDataDto, BigDecimal newRate, BigDecimal otherService) {
        BigDecimal totalAmount = scoringDataDto.amount().add(otherService);
        BigDecimal debtPayment = totalAmount.divide(BigDecimal.valueOf(scoringDataDto.term()), new MathContext(MathContext.DECIMAL128.getPrecision(), RoundingMode.HALF_EVEN));
        BigDecimal ratePercents = getRatePercents(newRate);
        var schedule = getSchedule(totalAmount, scoringDataDto.term(), ratePercents, debtPayment);
        BigDecimal psk = getPsk(schedule);
        BigDecimal averageMonthlyPayment = getAverageMonthlyPayment(schedule).setScale(countDigitAfterPoint, RoundingMode.HALF_EVEN);

        return new CreditDto(
                scoringDataDto.amount(),
                scoringDataDto.term(),
                averageMonthlyPayment,
                newRate,
                psk,
                scoringDataDto.isInsuranceEnabled(),
                scoringDataDto.isSalaryClient(),
                schedule
        );
    }

    private BigDecimal getAverageMonthlyPayment(List<PaymentScheduleElementDto> schedule) {
        return schedule.stream()
                .map(paymentScheduleElementDto -> paymentScheduleElementDto.totalPayment())
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(schedule.size()), new MathContext(countDigitAfterPoint, RoundingMode.HALF_EVEN));
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

    private BigDecimal getRatePercents(BigDecimal newRate) {
        return newRate.divide(BigDecimal.valueOf(100));
    }
}
