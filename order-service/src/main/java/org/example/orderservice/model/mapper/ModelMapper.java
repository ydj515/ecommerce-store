package org.example.orderservice.model.mapper;

import org.example.orderservice.model.dto.OrderDto;
import org.example.orderservice.model.entity.Order;
import org.example.orderservice.model.payload.request.OrderRequest;
import org.example.orderservice.model.payload.response.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper
public interface ModelMapper {
    ModelMapper INSTANCE = Mappers.getMapper(ModelMapper.class);

    OrderResponse toOrderResponse(Order order);


    Order toOrder(OrderDto orderDto);


    @Mapping(target = "totalPrice", expression = "java(calculateTotalPrice(orderRequest))")
    @Mapping(target = "orderId", expression = "java(generateOrderId())")
    @Mapping(target = "userId", source = "userId")
    OrderDto toOrderDto(String userId, OrderRequest orderRequest);

    default Integer calculateTotalPrice(OrderRequest orderRequest) {
        if (orderRequest == null || orderRequest.getQuantity() == null || orderRequest.getUnitPrice() == null) {
            return null;
        }
        return orderRequest.getQuantity() * orderRequest.getUnitPrice();
    }

    default String generateOrderId() {
        return UUID.randomUUID().toString();
    }
}
