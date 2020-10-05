package com.kuznetsovka.trueshop.mapper;

import com.kuznetsovka.trueshop.domain.Order;
import com.kuznetsovka.trueshop.domain.OrderDetails;
import com.kuznetsovka.trueshop.domain.OrderStatus;
import com.kuznetsovka.trueshop.dto.OrderDto;
import com.kuznetsovka.trueshop.dto.ProductDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;

@Mapper(uses = {ProductMapper.class,UserMapper.class})
public interface OrderMapper {
    OrderMapper MAPPER = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "orderedProducts", target = "products")
    Order toOrder(OrderDto dto);
    List<Order> toOrderList(List<OrderDto> orders);
    @InheritInverseConfiguration
    OrderDto fromOrder(Order order);
    List<OrderDto> fromOrderList(List<Order> orders);
}
