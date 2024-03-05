package org.example.userservice.model.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberResponse {
    private String userId;
    private String email;
    private String name;

    private List<OrderResponse> orders;
}
