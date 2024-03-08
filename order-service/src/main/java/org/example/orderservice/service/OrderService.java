package org.example.orderservice.service;

import org.example.orderservice.model.dto.OrderDto;
import org.example.orderservice.model.payload.response.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(OrderDto orderRequest);

    OrderResponse getOrderByOrderId(String orderId);

    Iterable<OrderResponse> getOrdersByUserId(String userId);
}