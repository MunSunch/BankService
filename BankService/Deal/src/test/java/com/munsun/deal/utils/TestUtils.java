package com.munsun.deal.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.munsun.deal.dto.EmploymentDto;
import com.munsun.deal.dto.FinishRegistrationRequestDto;
import com.munsun.deal.dto.LoanStatementRequestDto;
import com.munsun.deal.dto.ScoringDataDto;
import com.munsun.deal.dto.CreditDto;
import com.munsun.deal.dto.ErrorMessageDto;
import com.munsun.deal.dto.LoanOfferDto;
import com.munsun.deal.dto.PaymentScheduleElementDto;
import com.munsun.deal.models.Client;
import com.munsun.deal.models.Credit;
import com.munsun.deal.models.Statement;
import com.munsun.deal.models.enums.*;
import com.munsun.deal.models.json.Passport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TestUtils {
    private final ObjectMapper mapper;

    public static final String LOAN_OFFERS_ENDPOINT_CALCULATOR = "/v1/calculator/offers";
    public static final String LOAN_OFFERS_ENDPOINT_DEAL = "/v1/deal/statement";
    public static final String CALC_CREDIT_ENDPOINT_CALCULATOR = "/v1/calculator/calc";

    public static ScoringDataDto getScoringDataDto() {
        return new ScoringDataDto(
                BigDecimal.valueOf(10_000),
                12,
                "Munir",
                "Sunchalyaev",
                "Raisovich",
                ScoringDataDto.GenderEnum.MALE,
                "msunchalyaev@gmail.com",
                LocalDate.of(1998, 5, 26),
                "4618",
                "321978",
                LocalDate.of(2014, 8, 12),
                "ГУ МВД ПО САРАТОВСКОЙ ОБЛАСТИ",
                ScoringDataDto.MaritalStatusEnum.SINGLE,
                0,
                new EmploymentDto(
                        EmploymentDto.EmploymentStatusEnum.SELF_EMPLOYED,
                        "1234567890123456",
                        BigDecimal.valueOf(100_000),
                        EmploymentDto.PositionEnum.MID_MANAGER,
                        20,
                        18
                ),
                "79873022923",
                false,
                true);
    }

    public static LoanStatementRequestDto getLoanStatementRequestDto() {
        return new LoanStatementRequestDto(
                new BigDecimal("33000"),
                12,
                "Munir",
                "Sunchalyaev",
                "Raisovich",
                "msunchalyaev@gmail.com",
                LocalDate.of(1998, 5, 26),
                "4618",
                "469798"
        );
    }

    public static Client getClientTransient() {
        return Client.builder()
                .firstName("Munir")
                .lastName("Sunchalyaev")
                .middleName("Raisovich")
                .birthdate(LocalDate.of(1998, 5, 26))
                .email("msunchalyaev@gmail.com")
                .passport(Passport.builder()
                        .series("4618")
                        .number("469798")
                        .build())
                .build();
    }

    public static Statement getStatementTransient() {
        return Statement.builder()
                .appliedOffer(getLoanOfferDtoPersistent(UUID.randomUUID()))
                .code("test")
                .status(ApplicationStatus.PREAPPROVAL)
                .creationDate(LocalDate.now())
                .signDate(LocalDate.now())
                .build();
    }

    public static Client getClientPersistent() {
        return Client.builder()
                .clientId(UUID.randomUUID())
                .firstName("Munir")
                .lastName("Sunchalyaev")
                .middleName("Raisovich")
                .birthdate(LocalDate.of(1998, 5, 26))
                .email("msunchalyaev@gmail.com")
                .passport(Passport.builder()
                        .series("4618")
                        .number("321978")
                        .build())
                .build();
    }

    public static ScoringDataDto getScoringDataDtoInvalidAmount() {
        return new ScoringDataDto(
                BigDecimal.valueOf(10_000),
                12,
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
                        19,
                        4
                ),
                "79873022923",
                false,
                false);
    }

    public static LoanStatementRequestDto getLoanStatementRequestDtoInvalidAmount() {
        return new LoanStatementRequestDto(
                new BigDecimal("12000"),
                12,
                "Munir",
                "Sunchalyaev",
                "Raisovich",
                "msunchalyaev@gmail.com",
                LocalDate.of(1998, 5, 26),
                "4618",
                "469798"
        );
    }

    public static Statement getStatementPersistent() {
        UUID id = UUID.randomUUID();
        return Statement.builder()
                .statementId(UUID.randomUUID())
                .client(getClientPersistent())
                .appliedOffer(getLoanOfferDtoPersistent(id))
                .code("test")
                .status(ApplicationStatus.PREAPPROVAL)
                .creationDate(LocalDate.now())
                .signDate(LocalDate.now())
                .build();
    }

    private static LoanOfferDto getLoanOfferDtoPersistent(UUID id) {
        return new LoanOfferDto().toBuilder()
                .statementId(id)
                .requestedAmount(new BigDecimal("10000"))
                .totalAmount(new BigDecimal("12000"))
                .term(12)
                .monthlyPayment(new BigDecimal("1100"))
                .rate(new BigDecimal("12"))
                .isInsuranceEnabled(false)
                .isSalaryClient(true)
                .build();
    }


    public static FinishRegistrationRequestDto getFinishRegistrationRequestDto() {
        return new FinishRegistrationRequestDto(
                FinishRegistrationRequestDto.GenderEnum.MALE,
                FinishRegistrationRequestDto.MaritalStatusEnum.SINGLE,
                0,
                LocalDate.of(2014, 8, 12),
                "ГУ МВД ПО САРАТОВСКОЙ ОБЛАСТИ",
                new EmploymentDto(
                        EmploymentDto.EmploymentStatusEnum.SELF_EMPLOYED,
                        "1234567890123456",
                        new BigDecimal("100000"),
                        EmploymentDto.PositionEnum.MID_MANAGER,
                        20,
                        18
                ),
                "79873022923"
        );
    }

    public static CreditDto getCreditDto() {
        return new CreditDto(
                new BigDecimal("10000"),
                12,
                new BigDecimal("926.35"),
                new BigDecimal("20"),
                new BigDecimal("11116.14"),
                false,
                false,
                List.of(new PaymentScheduleElementDto(
                                1,
                                LocalDate.of(2024, 6, 27),
                                new BigDecimal("926.35"),
                                new BigDecimal("166.67"),
                                new BigDecimal("759.68"),
                                new BigDecimal("9240.32")
                        ),
                        new PaymentScheduleElementDto(
                                2,
                                LocalDate.of(2024, 7, 27),
                                new BigDecimal("926.35"),
                                new BigDecimal("154.01"),
                                new BigDecimal("772.34"),
                                new BigDecimal("8467.98")
                        ),
                        new PaymentScheduleElementDto(
                                3,
                                LocalDate.of(2024, 8, 27),
                                new BigDecimal("926.35"),
                                new BigDecimal("141.13"),
                                new BigDecimal("785.21"),
                                new BigDecimal("7682.77")
                        ),
                        new PaymentScheduleElementDto(
                                4,
                                LocalDate.of(2024, 9, 27),
                                new BigDecimal("926.35"),
                                new BigDecimal("128.05"),
                                new BigDecimal("798.30"),
                                new BigDecimal("6884.47")
                        ),
                        new PaymentScheduleElementDto(
                                5,
                                LocalDate.of(2024, 10, 27),
                                new BigDecimal("926.35"),
                                new BigDecimal("114.74"),
                                new BigDecimal("811.60"),
                                new BigDecimal("6072.87")
                        ),
                        new PaymentScheduleElementDto(
                                6,
                                LocalDate.of(2024, 11, 27),
                                new BigDecimal("926.35"),
                                new BigDecimal("101.21"),
                                new BigDecimal("825.13"),
                                new BigDecimal("5247.74")
                        ),
                        new PaymentScheduleElementDto(
                                7,
                                LocalDate.of(2024, 12, 27),
                                new BigDecimal("926.35"),
                                new BigDecimal("87.46"),
                                new BigDecimal("838.88"),
                                new BigDecimal("4408.85")
                        ),
                        new PaymentScheduleElementDto(
                                8,
                                LocalDate.of(2025, 1, 27),
                                new BigDecimal("926.35"),
                                new BigDecimal("73.48"),
                                new BigDecimal("852.86"),
                                new BigDecimal("3555.99")
                        ),
                        new PaymentScheduleElementDto(
                                9,
                                LocalDate.of(2025, 2, 27),
                                new BigDecimal("926.35"),
                                new BigDecimal("59.27"),
                                new BigDecimal("867.08"),
                                new BigDecimal("2688.91")
                        ),
                        new PaymentScheduleElementDto(
                                10,
                                LocalDate.of(2025, 3, 27),
                                new BigDecimal("926.35"),
                                new BigDecimal("44.82"),
                                new BigDecimal("881.53"),
                                new BigDecimal("1807.38")
                        ),
                        new PaymentScheduleElementDto(
                                11,
                                LocalDate.of(2025, 4, 27),
                                new BigDecimal("926.35"),
                                new BigDecimal("30.12"),
                                new BigDecimal("896.22"),
                                new BigDecimal("911.16")
                        ),
                        new PaymentScheduleElementDto(
                                12,
                                LocalDate.of(2025, 5, 27),
                                new BigDecimal("926.35"),
                                new BigDecimal("15.19"),
                                new BigDecimal("911.16"),
                                new BigDecimal("0.00")
                        )
                )
        );
    }

    public static Credit getCredit() {
        var creditDto = getCreditDto();
        return Credit.builder()
                .psk(creditDto.getPsk())
                .rate(creditDto.getRate())
                .term(creditDto.getTerm())
                .amount(creditDto.getAmount())
                .insuranceEnabled(creditDto.getIsInsuranceEnabled())
                .monthlyPayment(creditDto.getMonthlyPayment())
                .salaryClient(creditDto.getIsSalaryClient())
                .paymentSchedule(creditDto.getPaymentSchedule())
                .build();
    }

    public static List<LoanOfferDto> getAnnuitentPaymentListLoanOffersDtoAmount10_000Term12() {
        return List.of(
                getAnnuitentPaymentLoanOfferDtoAmount10_000Term12(),
                getAnnuitentPaymentLoanOfferDtoAmount10_000Term12SalaryClient(),
                getAnnuitentPaymentLoanOfferDtoAmount10_000Term12Insurance(),
                getAnnuitentPaymentLoanOfferDtoAmount10_000Term12NotSalaryClentAndNotInsurance()
        );
    }

    public static LoanOfferDto getAnnuitentPaymentLoanOfferDtoAmount10_000Term12NotSalaryClentAndNotInsurance() {
        return new LoanOfferDto(
                UUID.randomUUID(),
                new BigDecimal("10000"),
                new BigDecimal("11116.20"),
                12,
                new BigDecimal("926.35"),
                new BigDecimal("20"),
                false,
                false
        );
    }

    public static LoanOfferDto getAnnuitentPaymentLoanOfferDtoAmount10_000Term12SalaryClient() {
        return new LoanOfferDto(
                UUID.randomUUID(),
                new BigDecimal("10000"),
                new BigDecimal("11001.60"),
                12,
                new BigDecimal("916.80"),
                new BigDecimal("18"),
                false,
                true
        );
    }

    public static LoanOfferDto getAnnuitentPaymentLoanOfferDtoAmount10_000Term12Insurance() {
        return new LoanOfferDto(
                UUID.randomUUID(),
                new BigDecimal("10000"),
                new BigDecimal("22117.56"),
                12,
                new BigDecimal("1843.13"),
                new BigDecimal("19"),
                true,
                false
        );
    }

    public static LoanOfferDto getAnnuitentPaymentLoanOfferDtoAmount10_000Term12() {
        return new LoanOfferDto(
                UUID.randomUUID(),
                new BigDecimal("10000"),
                new BigDecimal("21889.20"),
                12,
                new BigDecimal("1824.10"),
                new BigDecimal("17"),
                true,
                true
        );
    }

    public static ErrorMessageDto getErrorMessageInvalidAmount() {
        return new ErrorMessageDto("amount: prescoring error");
    }

    public static ErrorMessageDto getErrorMessageScoringError() {
        return new ErrorMessageDto("scoring error");
    }

    public static LoanOfferDto getLoanOffer(UUID uuidStatement) {
        return new LoanOfferDto(
                uuidStatement,
                new BigDecimal("10000"),
                new BigDecimal("21889.20"),
                12,
                new BigDecimal("1824.10"),
                new BigDecimal("17"),
                true,
                true
        );
    }
}
