package org.example.userservice.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS("0", "success"),
    JOIN_SUCCESS("1", "join success"),
    LOGIN_SUCCESS("2", "login success"),
    REFRESH_SUCCESS("3", "refresh token success"),
    FAIL("99", "99 fail"),
    ;

    public final String code;
    public final String message;
}
