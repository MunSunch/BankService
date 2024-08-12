package com.munsun.statement.exceptions;

import java.util.UUID;

public class StatementNotFoundException extends RuntimeException {
    public StatementNotFoundException(UUID uuid) {
        super(uuid.toString());
    }
}
