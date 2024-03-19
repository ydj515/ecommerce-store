package org.example.orderservice.model.dto.kafka;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class Payload {
    private String order_id;
    private String user_id;
    private String product_id;
    private Integer quantity;
    private Integer unit_price;
    private Integer total_price;
    private LocalDateTime created_at;
}
