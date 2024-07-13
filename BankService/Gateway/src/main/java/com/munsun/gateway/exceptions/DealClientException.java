package com.munsun.gateway.exceptions;

public class DealClientException extends Exception {
    private int statusCode;
    private String responseBody;

    public DealClientException(int statusCode, String responseBody) {
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
        return "DealClientException{" +
                "statusCode=" + statusCode +
                ", responseBody='" + responseBody + '\'' +
                '}';
    }
}
