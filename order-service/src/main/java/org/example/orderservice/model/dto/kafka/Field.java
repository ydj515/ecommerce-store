package org.example.orderservice.model.dto.kafka;

import lombok.*;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Field {
    private String type;
    private boolean optional;
    private String field;
}
