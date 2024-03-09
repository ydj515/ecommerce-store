package org.example.orderservice.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS(0, "success"),

    FAIL(99, "99 fail"),
    ;

    public final int code;
    public final String message;
}
