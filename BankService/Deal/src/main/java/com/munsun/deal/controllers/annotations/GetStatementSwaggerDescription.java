package com.munsun.deal.controllers.annotations;

import com.munsun.deal.dto.response.ErrorMessageDto;
import com.munsun.deal.models.Statement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "get statement")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Return statement",
                content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Statement.class)) }),
        @ApiResponse(responseCode = "404", description = "Statement not found",
                content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessageDto.class)) }),
        @ApiResponse(responseCode = "500", description = "Server's error",
                content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessageDto.class)) })})
public @interface GetStatementSwaggerDescription {}