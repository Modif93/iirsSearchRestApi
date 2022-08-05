package com.connector.iirsSearch.exception;

import com.connector.iirsSearch.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class RequestExceptionHandler {

    @ExceptionHandler(RequestException.class)
    @ResponseBody
    public ErrorResponse RequestExceptionResponse (
            HttpServletRequest request,
            RequestException e
    ) {
        log.error("url: {}, errorCode: {}, message: {}",
                request.getRequestURI(), e.getRespErrCode(), e.getRespErrMsg());

        return ErrorResponse.builder()
                .errorCode(e.getRespErrCode())
                .errorMsg(e.getRespErrMsg())
                .errorParam(e.getDetail())
                .build();
    }
}