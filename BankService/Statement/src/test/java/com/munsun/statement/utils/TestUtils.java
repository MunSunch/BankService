package com.munsun.statement.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.munsun.statement.dto.ErrorMessageDto;
import com.munsun.statement.dto.LoanOfferDto;
import com.munsun.statement.dto.LoanStatementRequestDto;
import com.munsun.statement.exceptions.StatementNotFoundException;
import feign.FeignException;
import feign.Response;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class TestUtils {
    private static ObjectMapper mapper = new ObjectMapper();
    public static final String LOAN_OFFERS_ENDPOINT_DEAL = "/v1/deal/statement";
    public static final String LOAN_OFFERS_ENDPOINT_STATEMENT = "/v1/statement";
    public static final String SELECT_LOAN_OFFER_ENDPOINT_DEAL = "/v1/deal/offer/select";
    public static final String SELECT_LOAN_OFFER_ENDPOINT_STATEMENT = "/v1/statement/offer";

    public static FeignException getFeignClientResponsePrescoringExceptionAmount() throws JsonProcessingException {
        return FeignException.errorStatus(HttpMethod.POST.name(), Response.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .body(mapper.writeValueAsString(new ErrorMessageDto("amount: prescoring error")).getBytes())
                .build());
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

    public static byte[] getListLoanOffersJSON() throws JsonProcessingException {
        return mapper.writeValueAsBytes(getAnnuitentPaymentListLoanOffersDtoAmount10_000Term12());
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

    public static LoanStatementRequestDto getLoanStatementRequestDto() {
        return new LoanStatementRequestDto(
                new BigDecimal("30000"),
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

    public static byte[] getErrorMessageWithStatementNotFoundJSON() throws JsonProcessingException {
        return mapper.writeValueAsBytes(new ErrorMessageDto("Statement not found"));
    }

    public static byte[] getErrorMessageWithUnknownExceptionJSON() throws JsonProcessingException {
        return mapper.writeValueAsBytes(new ErrorMessageDto("Server's error"));
    }

    public static Object getLoanStatementRequestDtoInvalidNullAmount() {
        return new LoanStatementRequestDto(
                null,
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

    public static Object getLoanStatementRequestDtoInvalidNullTerm() {
        return new LoanStatementRequestDto(
                new BigDecimal("30000"),
                null,
                "Munir",
                "Sunchalyaev",
                "Raisovich",
                "msunchalyaev@gmail.com",
                LocalDate.of(1998, 5, 26),
                "4618",
                "469798"
        );
    }

    public static Object getLoanStatementRequestDtoInvalidNullFirstName() {
        return new LoanStatementRequestDto(
                new BigDecimal("30000"),
                12,
                "",
                "Sunchalyaev",
                "Raisovich",
                "msunchalyaev@gmail.com",
                LocalDate.of(1998, 5, 26),
                "4618",
                "469798"
        );
    }

    public static Object getLoanStatementRequestDtoInvalidNullLastName() {
        return new LoanStatementRequestDto(
                new BigDecimal("30000"),
                12,
                "Munir",
                "",
                "Raisovich",
                "msunchalyaev@gmail.com",
                LocalDate.of(1998, 5, 26),
                "4618",
                "469798"
        );
    }

    public static Object getLoanStatementRequestDtoInvalidNullMiddleName() {
        return new LoanStatementRequestDto(
                new BigDecimal("30000"),
                12,
                "Munir",
                "Sunchalyaev",
                "",
                "msunchalyaev@gmail.com",
                LocalDate.of(1998, 5, 26),
                "4618",
                "469798"
        );
    }

    public static Object getLoanStatementRequestDtoInvalidNullEmail() {
        return new LoanStatementRequestDto(
                new BigDecimal("30000"),
                12,
                "Munir",
                "Sunchalyaev",
                "Raisovich",
                null,
                LocalDate.of(1998, 5, 26),
                "4618",
                "469798"
        );
    }

    public static Object getLoanStatementRequestDtoInvalidNullBirthdate() {
        return new LoanStatementRequestDto(
            new BigDecimal("30000"),
            12,
            "Munir",
            "Sunchalyaev",
            "Raisovich",
            "msunchalyaev@gmail.com",
            null,
            "4618",
            "469798"
        );
    }

    public static Object getLoanStatementRequestDtoInvalidNullPassportSeries() {
        return new LoanStatementRequestDto(
                new BigDecimal("30000"),
                12,
                "Munir",
                "Sunchalyaev",
                "Raisovich",
                "msunchalyaev@gmail.com",
                LocalDate.of(1998, 5, 26),
                "",
                "469798"
        );
    }

    public static Object getLoanStatementRequestDtoInvalidNullPassportNumber() {
        return new LoanStatementRequestDto(
                new BigDecimal("30000"),
                12,
                "Munir",
                "Sunchalyaev",
                "Raisovich",
                "msunchalyaev@gmail.com",
                LocalDate.of(1998, 5, 26),
                "4618",
                null
        );
    }

    public static Object getLoanOfferNullUUID() {
        return new LoanOfferDto(
                null,
                new BigDecimal("10000"),
                new BigDecimal("11116.20"),
                12,
                new BigDecimal("926.35"),
                new BigDecimal("20"),
                false,
                false
        );
    }

    public static Object getLoanOfferNullRequestAmount() {
        return new LoanOfferDto(
                UUID.randomUUID(),
                null,
                new BigDecimal("11116.20"),
                12,
                new BigDecimal("926.35"),
                new BigDecimal("20"),
                false,
                false
        );
    }

    public static Object getLoanOfferNullTotalAmount() {
        return new LoanOfferDto(
                UUID.randomUUID(),
                new BigDecimal("10000"),
                null,
                12,
                new BigDecimal("926.35"),
                new BigDecimal("20"),
                false,
                false
        );
    }

    public static Object getLoanOfferNullTerm() {
        return new LoanOfferDto(
                UUID.randomUUID(),
                new BigDecimal("10000"),
                new BigDecimal("11116.20"),
                null,
                new BigDecimal("926.35"),
                new BigDecimal("20"),
                false,
                false
        );
    }

    public static Object getLoanOfferNullMonthlyPayment() {
        return new LoanOfferDto(
                UUID.randomUUID(),
                new BigDecimal("10000"),
                new BigDecimal("11116.20"),
                12,
                null,
                new BigDecimal("20"),
                false,
                false
        );
    }
}
