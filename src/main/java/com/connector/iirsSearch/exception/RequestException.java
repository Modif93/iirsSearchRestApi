package com.connector.iirsSearch.exception;

import lombok.Getter;

@Getter
public class RequestException extends RuntimeException {
    private String respErrCode;
    private String respErrMsg;
    private String detail;
    public RequestException(RequestExceptCode requestExceptCode, String detail) {
        this.respErrCode = requestExceptCode.getExceptCode();
        this.respErrMsg = requestExceptCode.getExceptMsg();
        this.detail = detail;
    }
}