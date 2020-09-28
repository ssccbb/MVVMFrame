package com.sung.mvvmframe.config;

/**
 * Create by sung at 2020/9/28
 *
 * @desc: 错误码定义
 * @notice:
 */
public enum  Error {
    ParserException(300,"json parser exception"),
    ResponseException(400,"service not response"),
    ServiceException(404,"service error"),
    UnknownException(999,"unknown exception");

    private int code;
    private String msg;

    Error(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return msg;
    }
}
