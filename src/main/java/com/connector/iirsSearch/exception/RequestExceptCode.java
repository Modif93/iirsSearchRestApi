package com.connector.iirsSearch.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestExceptCode {
    NO_DATA("OK-210", "데이터가 없습니다."),
    MANDATORY_PARAM_ERROR("ERROR-300", "요청 메시지 중 필수값이 누락되었습니다."),
    INVALID_PARAM_ERROR("ERROR-400", "요청 메시지 중 값이 유효하지 않습니다."),
    INTERNAL_SERVER_ERROR("ERROR-500","서버에 오류가 발생했습니다.");

    private final String exceptCode;
    private final String exceptMsg;
}