package com.kuznetsovka.trueshop.service.order;

import com.kuznetsovka.trueshop.domain.Order;
import com.kuznetsovka.trueshop.dto.OrderDto;

import java.util.List;

public interface OrderService {
    Order findById(Long id);

    Order getOrder(Long id);

    List<OrderDto> findAll();
    OrderDto save(OrderDto dto);
    void saveOrder(Order order);
}
