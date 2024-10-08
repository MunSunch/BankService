package com.munsun.deal.exceptions;

public class StatementNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Statement not found";

    public StatementNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public StatementNotFoundException(String message) {
        super(message.concat(message));
    }
}
