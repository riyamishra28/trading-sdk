package com.trading.sdk.controller;

import com.trading.sdk.dto.ApiResponse;
import com.trading.sdk.model.Portfolio;
import com.trading.sdk.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Portfolio>>> getPortfolio() {
        List<Portfolio> portfolio = portfolioService.getPortfolio();
        return ResponseEntity.ok(
                ApiResponse.success("Portfolio retrieved successfully", portfolio)
        );
    }
}
