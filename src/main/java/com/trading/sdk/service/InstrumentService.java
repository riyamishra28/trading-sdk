package com.trading.sdk.service;

import com.trading.sdk.model.Instrument;
import com.trading.sdk.repository.InstrumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InstrumentService {

    private final InstrumentRepository instrumentRepository;

    public List<Instrument> getAllInstruments() {
        log.info("Fetching all instruments");
        return instrumentRepository.findAll();
    }

    public Instrument getInstrumentBySymbol(String symbol) {
        log.info("Fetching instrument by symbol: {}", symbol);
        return instrumentRepository.findBySymbol(symbol)
                .orElseThrow(() -> new RuntimeException("Instrument not found: " + symbol));
    }
}
