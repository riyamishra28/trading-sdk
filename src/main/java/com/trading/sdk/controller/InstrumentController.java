package com.trading.sdk.controller;

import com.trading.sdk.dto.ApiResponse;
import com.trading.sdk.model.Instrument;
import com.trading.sdk.service.InstrumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/instruments")
@RequiredArgsConstructor
public class InstrumentController {

    private final InstrumentService instrumentService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Instrument>>> getAllInstruments() {
        List<Instrument> instruments = instrumentService.getAllInstruments();
        return ResponseEntity.ok(
                ApiResponse.success("Instruments retrieved successfully", instruments)
        );
    }
}
