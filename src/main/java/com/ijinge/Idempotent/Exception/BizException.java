package com.ijinge.Idempotent.Exception;

public class BizException extends RuntimeException {
    private final int code;
    public BizException(int code, String msg) {
        super(msg);
        this.code = code;
    }
    public int getCode() { return code; }
}
