package com.trading.sdk.controller;

import com.trading.sdk.dto.ApiResponse;
import com.trading.sdk.model.Trade;
import com.trading.sdk.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trades")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Trade>>> getAllTrades() {
        List<Trade> trades = tradeService.getAllTrades();
        return ResponseEntity.ok(
                ApiResponse.success("Trades retrieved successfully", trades)
        );
    }
}
