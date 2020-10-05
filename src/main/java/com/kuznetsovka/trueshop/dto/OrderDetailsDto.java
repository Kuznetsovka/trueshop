package com.kuznetsovka.trueshop.dto;

import com.kuznetsovka.trueshop.domain.Product;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
@Data
@NoArgsConstructor
public class OrderDetailsDto {
    private Product product;
    private BigDecimal amount;
    private BigDecimal price;
}
