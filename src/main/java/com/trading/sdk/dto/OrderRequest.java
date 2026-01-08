package com.trading.sdk.dto;

import com.trading.sdk.enums.OrderStyle;
import com.trading.sdk.enums.OrderType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotBlank(message = "Symbol is required")
    private String symbol;

    @NotNull(message = "Order type is required")
    private OrderType orderType;

    @NotNull(message = "Order style is required")
    private OrderStyle orderStyle;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be greater than 0")
    private Integer quantity;

    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;
}
