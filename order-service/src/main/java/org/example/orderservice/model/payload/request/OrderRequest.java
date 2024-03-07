package org.example.orderservice.model.payload.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderRequest {
    private String productId;
    private Integer quantity;
    private Integer unitPrice;
}
