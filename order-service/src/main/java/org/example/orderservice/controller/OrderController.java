package org.example.orderservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.model.dto.OrderDto;
import org.example.orderservice.model.enums.ResultCode;
import org.example.orderservice.model.mapper.ModelMapper;
import org.example.orderservice.model.payload.base.Api;
import org.example.orderservice.model.payload.request.OrderRequest;
import org.example.orderservice.model.payload.response.OrderResponse;
import org.example.orderservice.service.OrderService;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order-service")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final Environment env;
    private final OrderService orderService;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Order Service on PORT %s",
                env.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<Api<OrderResponse>> createOrder(@PathVariable("userId") String userId,
                                                          @RequestBody OrderRequest orderRequest) {
        OrderDto orderDto = ModelMapper.INSTANCE.toOrderDto(userId, orderRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Api.<OrderResponse>builder()
                        .resultCode(ResultCode.SUCCESS)
                        .data(orderService.createOrder(orderDto))
                        .build()
                );
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<Api<Iterable<OrderResponse>>> getOrder(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(
                Api.<Iterable<OrderResponse>>builder()
                        .resultCode(ResultCode.SUCCESS)
                        .data(orderService.getOrdersByUserId(userId))
                        .build()
        );
    }

}
