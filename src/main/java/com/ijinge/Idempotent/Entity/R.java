package com.ijinge.Idempotent.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class R<T> {
    private int code;
    private String msg;
    private T data;

    public static <T> R<T> ok(T data) {
        return new R<>(200, "success", data);
    }
    public static R<Void> fail(int code, String msg) {
        return new R<>(code, msg, null);
    }
}