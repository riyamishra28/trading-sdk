package com.trading.sdk.controller;

import com.trading.sdk.dto.ApiResponse;
import com.trading.sdk.dto.OrderRequest;
import com.trading.sdk.dto.OrderResponse;
import com.trading.sdk.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> placeOrder(
            @Valid @RequestBody OrderRequest request) {
        OrderResponse order = orderService.placeOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Order placed successfully", order));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderStatus(
            @PathVariable Long orderId) {
        OrderResponse order = orderService.getOrderStatus(orderId);
        return ResponseEntity.ok(
                ApiResponse.success("Order retrieved successfully", order)
        );
    }
}
