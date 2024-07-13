package com.munsun.gateway.exceptions;

public class StatementClientException extends Exception {
    private int statusCode;
    private String responseBody;

    public StatementClientException(int statusCode, String responseBody) {
        super("statementClientException");
        this.statusCode=statusCode;
        this.responseBody=responseBody;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    @Override
    public String toString() {
        return "StatementClientException{" +
                "statusCode=" + statusCode +
                ", responseBody='" + responseBody + '\'' +
                '}';
    }
}
