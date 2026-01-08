package com.trading.sdk.service;

import com.trading.sdk.enums.OrderType;
import com.trading.sdk.model.Trade;
import com.trading.sdk.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradeService {

    private final TradeRepository tradeRepository;

    public List<Trade> getAllTrades() {
        log.info("Fetching all trades");
        return tradeRepository.findAll();
    }

    public Trade createTrade(Long orderId, String symbol, OrderType orderType,
                             Integer quantity, BigDecimal price) {
        log.info("Creating trade for order: {}", orderId);

        Trade trade = new Trade();
        trade.setOrderId(orderId);
        trade.setSymbol(symbol);
        trade.setTradeType(orderType);
        trade.setQuantity(quantity);
        trade.setPrice(price);
        trade.setTotalValue(price.multiply(BigDecimal.valueOf(quantity)));
        trade.setExecutedAt(LocalDateTime.now());

        Trade savedTrade = tradeRepository.save(trade);
        log.info("Trade created successfully: {}", savedTrade.getTradeId());
        return savedTrade;
    }
}
