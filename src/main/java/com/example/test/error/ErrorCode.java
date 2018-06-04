package com.example.test.error;


public class ErrorCode {
    private ErrorCode(){}

    private static final String ERROR_CODE_PREFIX = "test001-";
    public static final String SFTP_CONNECT_EXCEPTION = ERROR_CODE_PREFIX + "01";
    public static final String SFTP_DOWNLOAD_EXCEPTION = ERROR_CODE_PREFIX + "02";
    public static final String CLOSE_STREAM_ERROR = ERROR_CODE_PREFIX + "03";

}
