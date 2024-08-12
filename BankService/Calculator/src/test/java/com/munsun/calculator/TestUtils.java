package com.munsun.calculator;

import com.munsun.calculator.dto.request.EmploymentDto;
import com.munsun.calculator.dto.request.LoanStatementRequestDto;
import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.dto.request.enums.EmploymentStatus;
import com.munsun.calculator.dto.request.enums.Gender;
import com.munsun.calculator.dto.request.enums.MaritalStatus;
import com.munsun.calculator.dto.request.enums.Position;
import com.munsun.calculator.dto.response.CreditDto;
import com.munsun.calculator.dto.response.LoanOfferDto;
import com.munsun.calculator.dto.response.PaymentScheduleElementDto;
import com.munsun.calculator.dto.utils.RateAndOtherServiceDto;
import com.munsun.calculator.dto.utils.SimpleScoringInfoDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TestUtils {
    private static final String nameSalaryFilter = "SalaryClientSoftScoringFilter";
    private static final String nameInsuranceFilter = "InsuranceSoftScoringFilter";
    public static ScoringDataDto getScoringDataDto() {
        return new ScoringDataDto(
                BigDecimal.valueOf(10_000),
                12,
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
                        Position.MID_MANAGER,
                        19,
                        4
                ),
                "79873022923",
                false,
                false);
    }

    public static ScoringDataDto getScoringDataDtoNotBinaryGender() {
        return new ScoringDataDto(
                BigDecimal.valueOf(10_000),
                12,
                "Munir",
                "Sunchalyaev",
                "Raisovich",
                Gender.NON_BINARY,
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
                        Position.MID_MANAGER,
                        19,
                        4
                ),
                "79873022923",
                false,
                false);
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
                        ))
        );
    }

    public static ScoringDataDto getScoringDataDtoLessMinAge() {
        return new ScoringDataDto(
                BigDecimal.valueOf(10_000),
                12,
                "Munir",
                "Sunchalyaev",
                "Raisovich",
                Gender.MALE,
                "msunchalyaev@gmail.com",
                LocalDate.of(2015, 5, 26),
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
                        Position.MID_MANAGER,
                        19,
                        4
                ),
                "79873022923",
                false,
                false);
    }

    public static ScoringDataDto getScoringDataDtoGreaterMaxAge() {
        return new ScoringDataDto(
                BigDecimal.valueOf(10_000),
                12,
                "Munir",
                "Sunchalyaev",
                "Raisovich",
                Gender.MALE,
                "msunchalyaev@gmail.com",
                LocalDate.of(1900, 5, 26),
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
                        Position.MID_MANAGER,
                        19,
                        4
                ),
                "79873022923",
                false,
                false);
    }

    public static ScoringDataDto getScoringDataDtoUnemployedWorkStatus() {
        return new ScoringDataDto(
                BigDecimal.valueOf(10_000),
                12,
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
                        EmploymentStatus.UNEMPLOYED,
                        "123456789011231212",
                        BigDecimal.valueOf(41_000),
                        Position.MID_MANAGER,
                        19,
                        4
                ),
                "79873022923",
                false,
                false);
    }

    public static ScoringDataDto getScoringDataDtoLessTotalWorkExperience() {
        return new ScoringDataDto(
                BigDecimal.valueOf(10_000),
                12,
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
                        Position.MID_MANAGER,
                        6,
                        4
                ),
                "79873022923",
                false,
                false);
    }

    public static ScoringDataDto getScoringDataDtoLessCurrentWorkExperience() {
        return new ScoringDataDto(
                BigDecimal.valueOf(10_000),
                12,
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
                        Position.MID_MANAGER,
                        19,
                        2
                ),
                "79873022923",
                false,
                false);
    }

    public static ScoringDataDto getScoringDataDtoLessAmount() {
        return new ScoringDataDto(
                BigDecimal.valueOf(10_000_000),
                12,
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
                        BigDecimal.valueOf(10_000),
                        Position.MID_MANAGER,
                        19,
                        4
                ),
                "79873022923",
                false,
                false);
    }

    public static ScoringDataDto getScoringDataDtoGreaterMaxAgeFemale() {
        return new ScoringDataDto(
                BigDecimal.valueOf(10_000_000),
                12,
                "Munir",
                "Sunchalyaev",
                "Raisovich",
                Gender.FEMALE,
                "msunchalyaev@gmail.com",
                LocalDate.of(1940, 5, 26),
                "1234",
                "567890",
                LocalDate.of(2014, 8, 10),
                "ГУ МВД РОССИИ",
                MaritalStatus.SINGLE,
                27,
                new EmploymentDto(
                        EmploymentStatus.SELF_EMPLOYED,
                        "123456789011231212",
                        BigDecimal.valueOf(10_000),
                        Position.MID_MANAGER,
                        19,
                        4
                ),
                "79873022923",
                false,
                false);
    }

    public static ScoringDataDto getScoringDataDtoLessMinAgeFemale() {
        return new ScoringDataDto(
                BigDecimal.valueOf(10_000_000),
                12,
                "Munir",
                "Sunchalyaev",
                "Raisovich",
                Gender.FEMALE,
                "msunchalyaev@gmail.com",
                LocalDate.of(2010, 5, 26),
                "1234",
                "567890",
                LocalDate.of(2014, 8, 10),
                "ГУ МВД РОССИИ",
                MaritalStatus.SINGLE,
                27,
                new EmploymentDto(
                        EmploymentStatus.SELF_EMPLOYED,
                        "123456789011231212",
                        BigDecimal.valueOf(10_000),
                        Position.MID_MANAGER,
                        19,
                        4
                ),
                "79873022923",
                false,
                false);
    }

    public static ScoringDataDto getScoringDataDtoOfTopManager() {
        return new ScoringDataDto(
                BigDecimal.valueOf(10_000),
                12,
                "Munir",
                "Sunchalyaev",
                "Raisovich",
                Gender.MALE,
                "msunchalyaev@gmail.com",
                LocalDate.of(1990, 5, 26),
                "1234",
                "567890",
                LocalDate.of(2014, 8, 10),
                "ГУ МВД РОССИИ",
                MaritalStatus.SINGLE,
                27,
                new EmploymentDto(
                        EmploymentStatus.BUSINESS_OWNER,
                        "123456789011231212",
                        BigDecimal.valueOf(41_000),
                        Position.TOP_MANAGER,
                        19,
                        4
                ),
                "79873022923",
                false,
                false);
    }

    public static ScoringDataDto getScoringDataDtoMiddleManager() {
        return new ScoringDataDto(
                BigDecimal.valueOf(10_000),
                12,
                "Munir",
                "Sunchalyaev",
                "Raisovich",
                Gender.FEMALE,
                "msunchalyaev@gmail.com",
                LocalDate.of(2005, 5, 26),
                "1234",
                "567890",
                LocalDate.of(2014, 8, 10),
                "ГУ МВД РОССИИ",
                MaritalStatus.MARRIED,
                27,
                new EmploymentDto(
                        EmploymentStatus.SELF_EMPLOYED,
                        "123456789011231212",
                        BigDecimal.valueOf(41_000),
                        Position.MID_MANAGER,
                        19,
                        4
                ),
                "79873022923",
                false,
                false);
    }

    public static ScoringDataDto getScoringDataDtoMilf() {
        return new ScoringDataDto(
                BigDecimal.valueOf(10_000),
                12,
                "Munir",
                "Sunchalyaev",
                "Raisovich",
                Gender.FEMALE,
                "msunchalyaev@gmail.com",
                LocalDate.of(1980, 5, 26),
                "1234",
                "567890",
                LocalDate.of(2014, 8, 10),
                "ГУ МВД РОССИИ",
                MaritalStatus.MARRIED,
                27,
                new EmploymentDto(
                        EmploymentStatus.SELF_EMPLOYED,
                        "123456789011231212",
                        BigDecimal.valueOf(41_000),
                        Position.MID_MANAGER,
                        19,
                        4
                ),
                "79873022923",
                false,
                false);
    }

    public static ScoringDataDto getScoringDataDtoHomeless() {
        return new ScoringDataDto(
                BigDecimal.valueOf(10_000),
                12,
                "Munir",
                "Sunchalyaev",
                "Raisovich",
                Gender.FEMALE,
                "msunchalyaev@gmail.com",
                LocalDate.of(2000, 5, 26),
                "1234",
                "567890",
                LocalDate.of(2014, 8, 10),
                "ГУ МВД РОССИИ",
                MaritalStatus.SINGLE,
                0,
                new EmploymentDto(
                        EmploymentStatus.UNKNOWN,
                        "123456789011231212",
                        BigDecimal.ZERO,
                        Position.WORKER,
                        19,
                        0
                ),
                "79873022923",
                false,
                false);
    }

    public static ScoringDataDto getScoringDataDtoMiddleManagerWithInsurance() {
        return new ScoringDataDto(
                BigDecimal.valueOf(10_000),
                12,
                "Munir",
                "Sunchalyaev",
                "Raisovich",
                Gender.FEMALE,
                "msunchalyaev@gmail.com",
                LocalDate.of(2005, 5, 26),
                "1234",
                "567890",
                LocalDate.of(2014, 8, 10),
                "ГУ МВД РОССИИ",
                MaritalStatus.MARRIED,
                27,
                new EmploymentDto(
                        EmploymentStatus.SELF_EMPLOYED,
                        "123456789011231212",
                        BigDecimal.valueOf(41_000),
                        Position.MID_MANAGER,
                        19,
                        4
                ),
                "79873022923",
                true,
                true);
    }

    public static LoanStatementRequestDto getLoanStatementRequestDto() {
        return new LoanStatementRequestDto(
                new BigDecimal("10000"),
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

    public static List<LoanOfferDto> getDifferentPaymentListLoanOffersDtoAmount10_000Term12() {
        return List.of(
                getDifferentPaymentLoanOfferDtoAmount10_000Term12NotSalaryClentAndNotInsurance(),
                getDifferentPaymentLoanOfferDtoAmount10_000Term12SalaryClient(),
                getDifferentPaymentLoanOfferDtoAmount10_000Term12Insurance(),
                getDifferentPaymentLoanOfferDtoAmount10_000Term12()
        );
    }

    public static LoanOfferDto getDifferentPaymentLoanOfferDtoAmount10_000Term12NotSalaryClentAndNotInsurance() {
        return new LoanOfferDto(
                UUID.randomUUID(),
                new BigDecimal("10000"),
                new BigDecimal("11086.03"),
                12,
                new BigDecimal("920.00"),
                new BigDecimal("20"),
                false,
                false
        );
    }

    public static LoanOfferDto getDifferentPaymentLoanOfferDtoAmount10_000Term12SalaryClient() {
        return new LoanOfferDto(
                UUID.randomUUID(),
                new BigDecimal("10000"),
                new BigDecimal("10977.44"),
                12,
                new BigDecimal("910.00"),
                new BigDecimal("18"),
                false,
                true
        );
    }

    public static LoanOfferDto getDifferentPaymentLoanOfferDtoAmount10_000Term12Insurance() {
        return new LoanOfferDto(
                UUID.randomUUID(),
                new BigDecimal("10000"),
                new BigDecimal("22063.60"),
                12,
                new BigDecimal("1800.00"),
                new BigDecimal("19"),
                true,
                false
        );
    }

    public static LoanOfferDto getDifferentPaymentLoanOfferDtoAmount10_000Term12() {
        return new LoanOfferDto(
                UUID.randomUUID(),
                new BigDecimal("10000"),
                new BigDecimal("21846.36"),
                12,
                new BigDecimal("1800.00"),
                new BigDecimal("17"),
                true,
                true
        );
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

    public static LoanStatementRequestDto getLoanStatementRequestDtoInvalidAmount() {
        return new LoanStatementRequestDto(
                new BigDecimal("20000"),
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

    public static LoanStatementRequestDto getLoanStatementRequestDtoInvalidTerm() {
        return new LoanStatementRequestDto(
                new BigDecimal("50000"),
                3,
                "Munir",
                "Sunchalyaev",
                "Raisovich",
                "msunchalyaev@gmail.com",
                LocalDate.of(1998, 5, 26),
                "4618",
                "469798"
        );
    }

    public static LoanStatementRequestDto getLoanStatementRequestDtoInvalidFirstName() {
        return new LoanStatementRequestDto(
                new BigDecimal("50000"),
                12,
                "Мунир",
                "Sunchalyaev",
                "Raisovich",
                "msunchalyaev@gmail.com",
                LocalDate.of(1998, 5, 26),
                "4618",
                "469798"
        );
    }

    public static LoanStatementRequestDto getLoanStatementRequestDtoInvalidLastName() {
        return new LoanStatementRequestDto(
                new BigDecimal("50000"),
                12,
                "Minur",
                "S",
                "Raisovich",
                "msunchalyaev@gmail.com",
                LocalDate.of(1998, 5, 26),
                "4618",
                "469798"
        );
    }

    public static LoanStatementRequestDto getLoanStatementRequestDtoInvalidMiddleName() {
        return new LoanStatementRequestDto(
                new BigDecimal("50000"),
                12,
                "Minur",
                "Sunchalyaev",
                "Раисович",
                "msunchalyaev@gmail.com",
                LocalDate.of(1998, 5, 26),
                "4618",
                "469798"
        );
    }

    public static LoanStatementRequestDto getLoanStatementRequestDtoInvalidEmail() {
        return new LoanStatementRequestDto(
                new BigDecimal("50000"),
                12,
                "Minur",
                "Sunchalyaev",
                "Raisovich",
                "msunchalyaev_gmail.com",
                LocalDate.of(1998, 5, 26),
                "4618",
                "469798"
        );
    }

    public static LoanStatementRequestDto getLoanStatementRequestDtoInvalidBirthdate() {
        return new LoanStatementRequestDto(
                new BigDecimal("50000"),
                12,
                "Minur",
                "Sunchalyaev",
                "Raisovich",
                "msunchalyaev@gmail.com",
                LocalDate.of(2015, 5, 26),
                "4618",
                "469798"
        );
    }

    public static LoanStatementRequestDto getLoanStatementRequestDtoInvalidPassportSeries() {
        return new LoanStatementRequestDto(
                new BigDecimal("50000"),
                12,
                "Minur",
                "Sunchalyaev",
                "Raisovich",
                "msunchalyaev@gmail.com",
                LocalDate.of(1998, 5, 26),
                "4618469798",
                "469798"
        );
    }

    public static LoanStatementRequestDto getLoanStatementRequestDtoInvalidPassportNumber() {
        return new LoanStatementRequestDto(
                new BigDecimal("50000"),
                12,
                "Minur",
                "Sunchalyaev",
                "Raisovich",
                "msunchalyaev@gmail.com",
                LocalDate.of(1998, 5, 26),
                "4618",
                "46979"
        );
    }

    public static List<SimpleScoringInfoDto> getSimpleScoringInfoDto() {
        return List.of(
                getSimpleScoringInfoDtoNotSalaryClientAndNotInsurance(),
                getSimpleScoringInfoDtoSalaryClient(),
                getSimpleScoringInfoDtoInsurance(),
                getSimpleScoringInfoDtoSalaryAndInsurance()
        );
    }

    public static SimpleScoringInfoDto getSimpleScoringInfoDtoSalaryClient() {
        return new SimpleScoringInfoDto(
                        Map.of(nameSalaryFilter, true,
                                nameInsuranceFilter, false),
                        new RateAndOtherServiceDto(new BigDecimal("18"), BigDecimal.ZERO)
                );
    }

    public static SimpleScoringInfoDto getSimpleScoringInfoDtoNotSalaryClientAndNotInsurance() {
        return new SimpleScoringInfoDto(
                Map.of(nameSalaryFilter, false,
                        nameInsuranceFilter, false),
                new RateAndOtherServiceDto(new BigDecimal("20"), BigDecimal.ZERO)
        );
    }

    public static SimpleScoringInfoDto getSimpleScoringInfoDtoInsurance() {
        return new SimpleScoringInfoDto(
                Map.of(nameSalaryFilter, false,
                        nameInsuranceFilter, true),
                new RateAndOtherServiceDto(new BigDecimal("19"), new BigDecimal("10000"))
        );
    }

    public static SimpleScoringInfoDto getSimpleScoringInfoDtoSalaryAndInsurance() {
        return new SimpleScoringInfoDto(
                Map.of(nameSalaryFilter, true,
                        nameInsuranceFilter, true),
                new RateAndOtherServiceDto(new BigDecimal("17"), new BigDecimal("10000"))
        );
    }
}