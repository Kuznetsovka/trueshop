package com.kuznetsovka.trueshop.service.order;
import com.kuznetsovka.trueshop.dao.OrderRepository;
import com.kuznetsovka.trueshop.domain.Order;
import com.kuznetsovka.trueshop.dto.OrderDto;
import com.kuznetsovka.trueshop.mapper.OrderMapper;
import com.kuznetsovka.trueshop.service.measure.MeasureMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderMapper mapper = OrderMapper.MAPPER;
    private final OrderRepository dao;

    public OrderServiceImpl(OrderRepository dao) {
        this.dao = dao;
    }

    @Override
    public Order findById(Long id) {
        return dao.findById(id).orElse(null);
    }

    @MeasureMethod
    @Override
    public List<OrderDto> findAll() {
        return mapper.fromOrderList(dao.findAll());
    }

    @MeasureMethod
    @Override
    public OrderDto save(OrderDto dto) {
        Order entity = mapper.toOrder(dto);
        Order savedEntity = dao.save(entity);
        return mapper.fromOrder(savedEntity);
    }
}
