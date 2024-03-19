package org.example.orderservice.model.dto.kafka;

import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class KafkaOrderDto implements Serializable {
    private Schema schema;
    private Payload payload;
}
