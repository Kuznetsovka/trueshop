package com.kuznetsovka.trueshop.service.order;
import com.kuznetsovka.trueshop.config.OrderIntegrationConfig;
import com.kuznetsovka.trueshop.config.SellsIntegrationConfig;
import com.kuznetsovka.trueshop.dao.OrderRepository;
import com.kuznetsovka.trueshop.domain.*;
import com.kuznetsovka.trueshop.dto.OrderDto;
import com.kuznetsovka.trueshop.dto.OrderIntegrationDto;
import com.kuznetsovka.trueshop.dto.SellIntegrationDto;
import com.kuznetsovka.trueshop.mapper.OrderMapper;
import com.kuznetsovka.trueshop.service.measure.MeasureMethod;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderMapper mapper = OrderMapper.MAPPER;
    private final OrderIntegrationConfig orderIntegrationConfig;
    private final OrderRepository dao;
    private final SellsIntegrationConfig sellsIntegrationConfig;

    public OrderServiceImpl(OrderIntegrationConfig orderIntegrationConfig, OrderRepository dao, SellsIntegrationConfig sellsIntegrationConfig) {
        this.orderIntegrationConfig = orderIntegrationConfig;
        this.dao = dao;
        this.sellsIntegrationConfig = sellsIntegrationConfig;
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
        sendIntegrationSell (savedOrder);
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

        orderIntegrationConfig.getOrdersChannel().send(message);
    }

    private void sendIntegrationSell(Order order){
        List<SellIntegrationDto> dto = new ArrayList<> ();
        User user = order.getUser ();
        if(user == null){
            throw new RuntimeException("User is not found");
        }
        Bucket bucket = user.getBucket();
        if(bucket == null || bucket.getProducts().isEmpty()){
            return;
        }
        Map<Product, Long> productWithAmount = bucket.getProducts().stream()
                .collect(Collectors.groupingBy(product -> product, Collectors.counting()));
        List<OrderDetails> orderDetails = productWithAmount.entrySet().stream()
                .map(pair -> new OrderDetails(order, pair.getKey(), pair.getValue()))
                .collect(Collectors.toList());
        List<SellIntegrationDto.OrderDetailsDto> details = order.getDetails().stream()
                .map(SellIntegrationDto.OrderDetailsDto::new).collect(Collectors.toList());
        for (Map.Entry<Product, Long> entry : productWithAmount.entrySet()) {
            dto.add(new SellIntegrationDto (
                    new Date(),
                    order.getId (),
                    user.getName (),
                    entry.getKey ().getTitle (),
                    entry.getValue (),
                    new BigDecimal (entry.getValue () * entry.getKey ().getPrice ())
            ));
        }
        for (SellIntegrationDto value : dto) {
            Message<SellIntegrationDto> message = MessageBuilder.withPayload (value)
                    .setHeader ("Content-type", "application/json")
                    .build ();
            sellsIntegrationConfig.getSellsChannel ().send (message);
        }
    }
}
