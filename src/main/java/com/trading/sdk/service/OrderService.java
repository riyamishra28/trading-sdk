package com.trading.sdk.service;

import com.trading.sdk.dto.OrderRequest;
import com.trading.sdk.dto.OrderResponse;
import com.trading.sdk.enums.OrderStatus;
import com.trading.sdk.enums.OrderStyle;
import com.trading.sdk.exception.InvalidOrderException;
import com.trading.sdk.exception.ResourceNotFoundException;
import com.trading.sdk.model.Instrument;
import com.trading.sdk.model.Order;
import com.trading.sdk.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InstrumentService instrumentService;
    private final TradeService tradeService;
    private final PortfolioService portfolioService;

    @Transactional
    public OrderResponse placeOrder(OrderRequest request) {
        log.info("Placing order: {}", request);

        validateOrder(request);

        Instrument instrument = instrumentService.getInstrumentBySymbol(request.getSymbol());

        Order order = new Order();
        order.setSymbol(request.getSymbol());
        order.setOrderType(request.getOrderType());
        order.setOrderStyle(request.getOrderStyle());
        order.setQuantity(request.getQuantity());
        order.setPrice(request.getPrice());
        order.setStatus(OrderStatus.PLACED);
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);
        log.info("Order placed successfully: {}", savedOrder.getOrderId());

        if (request.getOrderStyle() == OrderStyle.MARKET) {
            executeOrder(savedOrder, instrument.getLastTradedPrice());
        }

        return mapToOrderResponse(savedOrder);
    }

    private void validateOrder(OrderRequest request) {
        if (!instrumentService.getInstrumentBySymbol(request.getSymbol()).getSymbol()
                .equals(request.getSymbol())) {
            throw new InvalidOrderException("Invalid instrument symbol: " + request.getSymbol());
        }

        if (request.getOrderStyle() == OrderStyle.LIMIT && request.getPrice() == null) {
            throw new InvalidOrderException("Price is required for LIMIT orders");
        }

        if (request.getQuantity() <= 0) {
            throw new InvalidOrderException("Quantity must be greater than 0");
        }
    }

    @Transactional
    public void executeOrder(Order order, BigDecimal executionPrice) {
        log.info("Executing order: {}", order.getOrderId());

        order.setStatus(OrderStatus.EXECUTED);
        order.setExecutedPrice(executionPrice);
        order.setExecutedAt(LocalDateTime.now());
        orderRepository.save(order);

        tradeService.createTrade(order.getOrderId(), order.getSymbol(),
                order.getOrderType(), order.getQuantity(), executionPrice);

        portfolioService.updatePortfolio(order.getSymbol(), order.getOrderType(),
                order.getQuantity(), executionPrice);

        log.info("Order executed successfully: {}", order.getOrderId());
    }

    public OrderResponse getOrderStatus(Long orderId) {
        log.info("Fetching order status for orderId: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + orderId));
        return mapToOrderResponse(order);
    }

    private OrderResponse mapToOrderResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getOrderId());
        response.setSymbol(order.getSymbol());
        response.setOrderType(order.getOrderType());
        response.setOrderStyle(order.getOrderStyle());
        response.setQuantity(order.getQuantity());
        response.setPrice(order.getPrice());
        response.setStatus(order.getStatus());
        response.setCreatedAt(order.getCreatedAt());
        response.setExecutedAt(order.getExecutedAt());
        response.setExecutedPrice(order.getExecutedPrice());
        return response;
    }
}
