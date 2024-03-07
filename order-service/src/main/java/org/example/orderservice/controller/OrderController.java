package org.example.orderservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.model.dto.OrderDto;
import org.example.orderservice.model.mapper.ModelMapper;
import org.example.orderservice.model.payload.request.OrderRequest;
import org.example.orderservice.model.payload.response.OrderResponse;
import org.example.orderservice.service.OrderService;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    public ResponseEntity<?> createOrder(@PathVariable("userId") String userId,
                                         @RequestBody OrderRequest orderRequest) {
        OrderDto orderDto = ModelMapper.INSTANCE.toOrderDto(userId, orderRequest);
        /* jpa */
        OrderResponse createdOrder = orderService.createOrder(orderDto);


        /* kafka */
//        orderDto.setOrderId(UUID.randomUUID().toString());
//        orderDto.setTotalPrice(orderDetails.getQty() * orderDetails.getUnitPrice());

        /* send this order to the kafka */
//        kafkaProducer.send("example-catalog-topic", orderDto);
//        orderProducer.send("orders", orderDto);

//        ResponseOrder responseOrder = mapper.map(orderDto, ResponseOrder.class);

        log.info("After added orders data");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<?> getOrder(@PathVariable("userId") String userId) throws Exception {
        Iterable<OrderResponse> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

}
