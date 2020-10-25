package com.kuznetsovka.trueshop.dto;

import com.kuznetsovka.trueshop.domain.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class SellIntegrationDto {
    Date date;
    Long orderId;
    String username;
    private String productTitle;
    private Long amount;
    private BigDecimal sum;
    private List<OrderDetailsDto> details;

    public SellIntegrationDto(Date date, Long orderId, String username, String productTitle, Long amount, BigDecimal sum) {
        this.date = date;
        this.orderId = orderId;
        this.username = username;
        this.productTitle = productTitle;
        this.amount = amount;
        this.sum = sum;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderDetailsDto {
        private String productTitle;
        Long orderId;
        String username;
        private Double amount;
        private Double sum;

        public OrderDetailsDto(OrderDetails details) {
            this.productTitle = details.getProduct().getTitle();
            this.amount = details.getAmount().doubleValue();
            this.sum = details.getPrice().multiply(details.getAmount()).doubleValue();
            this.orderId = details.getOrder ().getId ();
            this.username = details.getOrder ().getUser ().getName ();
        }
    }
}
