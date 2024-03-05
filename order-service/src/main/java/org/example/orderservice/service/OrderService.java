package org.example.orderservice.service;

import org.example.orderservice.model.entity.Order;
import org.example.orderservice.model.payload.request.OrderRequest;
import org.example.orderservice.model.payload.response.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);

    OrderResponse getOrderByOrderId(String orderId);

    Iterable<OrderResponse> getOrdersByUserId(String userId);
}