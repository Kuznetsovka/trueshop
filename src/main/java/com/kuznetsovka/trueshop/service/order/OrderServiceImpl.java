package com.kuznetsovka.trueshop.service.order;
import com.kuznetsovka.trueshop.config.OrderIntegrationConfig;
import com.kuznetsovka.trueshop.dao.OrderRepository;
import com.kuznetsovka.trueshop.domain.Order;
import com.kuznetsovka.trueshop.dto.OrderDto;
import com.kuznetsovka.trueshop.dto.OrderIntegrationDto;
import com.kuznetsovka.trueshop.mapper.OrderMapper;
import com.kuznetsovka.trueshop.service.measure.MeasureMethod;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderMapper mapper = OrderMapper.MAPPER;
    private final OrderIntegrationConfig integrationConfig;
    private final OrderRepository dao;

    public OrderServiceImpl(OrderIntegrationConfig integrationConfig, OrderRepository dao) {
        this.integrationConfig = integrationConfig;
        this.dao = dao;
    }

    @Override
    public Order findById(Long id) {
        return dao.findById(id).orElse(null);
    }

    @Override
    public Order getOrder(Long id) {
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

    @Override
    @Transactional
    public void saveOrder(Order order) {
        Order savedOrder = dao.save(order);
        sendIntegrationNotify(savedOrder);
    }

    private void sendIntegrationNotify(Order order){
        OrderIntegrationDto dto = new OrderIntegrationDto();
        dto.setUsername(order.getUser().getName());
        dto.setAddress(order.getAddress());
        dto.setOrderId(order.getId());
        List<OrderIntegrationDto.OrderDetailsDto> details = order.getDetails().stream()
                .map(OrderIntegrationDto.OrderDetailsDto::new).collect(Collectors.toList());
        dto.setDetails(details);

        Message<OrderIntegrationDto> message = MessageBuilder.withPayload(dto)
                .setHeader("Content-type", "application/json")
                .build();

        integrationConfig.getOrdersChannel().send(message);
    }
}
