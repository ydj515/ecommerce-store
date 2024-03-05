package org.example.orderservice.model.mapper;

import org.example.orderservice.model.entity.Order;
import org.example.orderservice.model.payload.request.OrderRequest;
import org.example.orderservice.model.payload.response.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ModelMapper {
    ModelMapper INSTANCE = Mappers.getMapper(ModelMapper.class);

    OrderResponse toOrderResponse(Order order);

    Order toOrder(OrderRequest orderRequest);

}
