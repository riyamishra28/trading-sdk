package com.trading.sdk.service;

import com.trading.sdk.enums.OrderType;
import com.trading.sdk.model.Instrument;
import com.trading.sdk.model.Portfolio;
import com.trading.sdk.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final InstrumentService instrumentService;

    public List<Portfolio> getPortfolio() {
        log.info("Fetching portfolio holdings");
        List<Portfolio> holdings = portfolioRepository.findAll();

        holdings.forEach(holding -> {
            Instrument instrument = instrumentService.getInstrumentBySymbol(holding.getSymbol());
            BigDecimal currentValue = instrument.getLastTradedPrice()
                    .multiply(BigDecimal.valueOf(holding.getQuantity()));
            holding.setCurrentValue(currentValue);
        });

        return holdings;
    }

    @Transactional
    public void updatePortfolio(String symbol, OrderType orderType,
                                Integer quantity, BigDecimal executedPrice) {
        log.info("Updating portfolio for symbol: {}, type: {}, quantity: {}",
                symbol, orderType, quantity);

        Portfolio portfolio = portfolioRepository.findBySymbol(symbol)
                .orElse(new Portfolio(null, symbol, 0, BigDecimal.ZERO, BigDecimal.ZERO));

        if (orderType == OrderType.BUY) {
            int newQuantity = portfolio.getQuantity() + quantity;
            BigDecimal totalCost = portfolio.getAveragePrice()
                    .multiply(BigDecimal.valueOf(portfolio.getQuantity()))
                    .add(executedPrice.multiply(BigDecimal.valueOf(quantity)));
            BigDecimal newAvgPrice = totalCost.divide(
                    BigDecimal.valueOf(newQuantity), 2, RoundingMode.HALF_UP);

            portfolio.setQuantity(newQuantity);
            portfolio.setAveragePrice(newAvgPrice);
        } else {
            int newQuantity = portfolio.getQuantity() - quantity;
            if (newQuantity < 0) {
                throw new RuntimeException("Insufficient holdings for symbol: " + symbol);
            }
            portfolio.setQuantity(newQuantity);
        }

        portfolioRepository.save(portfolio);
        log.info("Portfolio updated successfully for symbol: {}", symbol);
    }
}
