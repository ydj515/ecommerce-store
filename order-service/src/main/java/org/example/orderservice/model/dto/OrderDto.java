package org.example.orderservice.model.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrderDto implements Serializable {
    private String productId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;
}
