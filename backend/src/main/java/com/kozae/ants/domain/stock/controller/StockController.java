package com.kozae.ants.domain.stock.controller;

import com.kozae.ants.domain.stock.dto.StockCreateRequest;
import com.kozae.ants.domain.stock.dto.StockResponse;
import com.kozae.ants.domain.stock.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 종목 Controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stocks")
public class StockController {

    private final StockService stockService;


    /**
     * 종목 생성
     */
    @PostMapping
    public ResponseEntity<StockResponse> createStock(@Valid @RequestBody StockCreateRequest request) {
        StockResponse response = stockService.createStock(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 종목 조회 (코드로)
     */
    @GetMapping("/code/{code}")
    public ResponseEntity<StockResponse> getStockByCode(@PathVariable String code) {
        StockResponse response = stockService.getStockByCode(code);
        return ResponseEntity.ok(response);
    }

    /**
     * 종목 검색 (이름으로)
     */
    @GetMapping("/search")
    public ResponseEntity<List<StockResponse>> searchStocks(@RequestParam String keyword) {
        List<StockResponse> response = stockService.searchStocks(keyword);
        return ResponseEntity.ok(response);
    }

    /**
     * 모든 종목 조회
     */
    @GetMapping
    public ResponseEntity<List<StockResponse>> getAllStocks() {
        List<StockResponse> response = stockService.getAllStocks();
        return ResponseEntity.ok(response);
    }
}
