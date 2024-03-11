package org.example.orderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.config.messageQueue.KafkaProducer;
import org.example.orderservice.model.dto.OrderDto;
import org.example.orderservice.model.entity.Order;
import org.example.orderservice.model.mapper.ModelMapper;
import org.example.orderservice.model.payload.response.OrderResponse;
import org.example.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final KafkaProducer kafkaProducer;
    private final OrderRepository orderRepository;

    @Override
    public OrderResponse createOrder(OrderDto orderDto) {
        Order order = ModelMapper.INSTANCE.toOrder(orderDto);
        Order savedOrder = orderRepository.save(order);

        kafkaProducer.send("example-catalog-topic", orderDto);
        return ModelMapper.INSTANCE.toOrderResponse(savedOrder);
    }

    @Override
    public OrderResponse getOrderByOrderId(String orderId) {
        Order order = orderRepository.findByOrderId(orderId);
        return ModelMapper.INSTANCE.toOrderResponse(order);
    }

    @Override
    public Iterable<OrderResponse> getOrdersByUserId(String userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(ModelMapper.INSTANCE::toOrderResponse)
                .collect(Collectors.toList());
    }
}
