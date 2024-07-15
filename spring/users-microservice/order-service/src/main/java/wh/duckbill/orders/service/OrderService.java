package wh.duckbill.orders.service;

import wh.duckbill.orders.dto.OrderDto;
import wh.duckbill.orders.jpa.OrderEntity;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);

    OrderDto getOrderByOrderId(String orderId);

    Iterable<OrderEntity> getOrdersByUserId(String userId);
}
