package com.kuznetsovka.trueshop.dto;

import com.kuznetsovka.trueshop.domain.OrderDetails;
import com.kuznetsovka.trueshop.domain.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;
@Data
@NoArgsConstructor
public class OrderDto {
    private List<ProductDto> orderedProducts;
    private BigDecimal sum;
    private String address;
    private List<OrderDetails> details;
    private OrderStatus status;
}
