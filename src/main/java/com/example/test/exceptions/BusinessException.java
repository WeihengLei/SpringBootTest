package com.example.test.exceptions;

public class BusinessException extends Exception {

	private String errorCode;
	public BusinessException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public BusinessException(String errorCode) {
		super();
		this.errorCode = errorCode;
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	@Override
	public String toString() {
		return "[errorCode=" + errorCode + ", detailMessage=" + getMessage() + "]";
	}
}