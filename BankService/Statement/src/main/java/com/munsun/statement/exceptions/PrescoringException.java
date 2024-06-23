package com.munsun.statement.exceptions;

import com.munsun.statement.dto.LoanStatementRequestDto;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class PrescoringException extends RuntimeException {
    private Set<ConstraintViolation<LoanStatementRequestDto>> resultPrescoring;
    public PrescoringException(String s) {
        super(s);
    }

    public PrescoringException(Set<ConstraintViolation<LoanStatementRequestDto>> resultPrescoring) {
        this.resultPrescoring = resultPrescoring;
    }

    public Set<ConstraintViolation<LoanStatementRequestDto>> getResultPrescoring() {
        return resultPrescoring;
    }
}
