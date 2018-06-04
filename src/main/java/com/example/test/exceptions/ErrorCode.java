package com.example.test.exceptions;

public enum ErrorCode {
    Normal("200"), TechnicalIssue("RBB000-01"), TIME_OUT("RBB000-02"),
    RestClientIssue("RBB000-03"), UNKNOWN_PROTOCOL("RBB000-04"),
    UNKNOWN_HOST_EXCEPTION("RBB000-05"), NOT_FOUND("RBB000-06"),
    GENERATE_TOKEN_ERROR("RBB000-07"),TOKEN_EXPIRED_ERROR("RBB000-08"),TOKEN_VALIDATION_ERROR("RBB000-09"),AUTH_FAILED("RBB000-10");

    private String code;

    private ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}