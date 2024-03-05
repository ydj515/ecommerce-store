package org.example.userservice.model.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
public class OrderResponse {
    private Long productId;
    private long quantity;
    private Long unitPrice;
    private Long totalPrice;
    private LocalDateTime createdAt;

    private Long orderId;
}
