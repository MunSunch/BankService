package com.munsun.calculator.controllers;

import com.munsun.calculator.dto.request.LoanStatementRequestDto;
import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.dto.request.enums.TypePayments;
import com.munsun.calculator.dto.response.CreditDto;
import com.munsun.calculator.dto.response.ErrorMessageDto;
import com.munsun.calculator.dto.response.LoanOfferDto;
import com.munsun.calculator.services.CalculatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/calculator")
@Tag(name="Calculator of loans controller", description = "displays possible offers and calculates the loan")
public class CalculatorController {
    private final CalculatorService calculatorService;

    @PostMapping("/offers")
    @Operation(summary = "calculation possible offers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calculated offers",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = LoanOfferDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid format",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessageDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Server's error",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessageDto.class)) })})
    public List<LoanOfferDto> calculatePossibleLoanTerms(@RequestBody @Valid LoanStatementRequestDto loanStatementRequestDto,
                                                         @RequestParam @NotBlank TypePayments typePayment)
    {
        return calculatorService.calculateLoan(typePayment, loanStatementRequestDto);
    }

    @PostMapping("/calc")
    @Operation(summary = "calculation credit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calculated credit",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CreditDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid format",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessageDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Server's error",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessageDto.class)) })})
    public CreditDto fullCalculateLoanParametersAndScoring(@RequestBody @Valid ScoringDataDto scoringDataDto,
                                                           @RequestParam @NotBlank TypePayments typePayment)
    {
        return calculatorService.calculateCredit(typePayment, scoringDataDto);
    }
}
