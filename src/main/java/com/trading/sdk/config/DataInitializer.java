package com.trading.sdk.config;

import com.trading.sdk.enums.InstrumentType;
import com.trading.sdk.model.Instrument;
import com.trading.sdk.repository.InstrumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final InstrumentRepository instrumentRepository;

    @Override
    public void run(String... args) {
        log.info("Initializing sample instruments...");

        List<Instrument> instruments = Arrays.asList(
                new Instrument(null, "RELIANCE", "NSE", InstrumentType.EQUITY, new BigDecimal("2450.50")),
                new Instrument(null, "TCS", "NSE", InstrumentType.EQUITY, new BigDecimal("3650.75")),
                new Instrument(null, "INFY", "NSE", InstrumentType.EQUITY, new BigDecimal("1550.30")),
                new Instrument(null, "HDFCBANK", "NSE", InstrumentType.EQUITY, new BigDecimal("1680.90")),
                new Instrument(null, "ICICIBANK", "NSE", InstrumentType.EQUITY, new BigDecimal("1020.40")),
                new Instrument(null, "NIFTY24FEB", "NSE", InstrumentType.FUTURES, new BigDecimal("21500.00")),
                new Instrument(null, "BANKNIFTY24FEB", "NSE", InstrumentType.FUTURES, new BigDecimal("45200.00")),
                new Instrument(null, "GOLD", "MCX", InstrumentType.COMMODITY, new BigDecimal("62500.00"))
        );

        instrumentRepository.saveAll(instruments);
        log.info("Sample instruments initialized: {} records", instruments.size());
    }
}
