package com.example.test.model;


import com.example.test.exceptions.ErrorCode;

public class BaseResponse<T> {
    private String respCode;
    private String message;
    private T responseBody;

    public BaseResponse() {

    }

    public BaseResponse(String respCode) {
        this(respCode , null);
    }

    public BaseResponse(String respCode, String message) {
        this.respCode = respCode;
        this.message = message;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
        if (ErrorCode.Normal.getCode().equals(respCode)) {
            message = "success";
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(T responseBody) {
        this.responseBody = responseBody;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "respCode='" + respCode + '\'' +
                ", message='" + message + '\'' +
                ", responseBody=" + responseBody +
                '}';
    }
}
