package com.trading.sdk.dto;

import com.trading.sdk.enums.OrderStatus;
import com.trading.sdk.enums.OrderStyle;
import com.trading.sdk.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long orderId;
    private String symbol;
    private OrderType orderType;
    private OrderStyle orderStyle;
    private Integer quantity;
    private BigDecimal price;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime executedAt;
    private BigDecimal executedPrice;
}
