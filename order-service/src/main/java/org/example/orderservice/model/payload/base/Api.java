package org.example.orderservice.model.payload.base;

import lombok.*;
import org.example.orderservice.model.enums.ResultCode;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Api<T> {


    @NonNull
    private int code;
    @NonNull
    private String message;
    private final LocalDateTime timestamp = LocalDateTime.now();
    @NonNull
    private T data;

    public static class ApiBuilder<T> {
        public ApiBuilder<T> resultCode(ResultCode resultCode) {
            this.code = resultCode.code;
            this.message = resultCode.message;
            return this;
        }
    }


}
